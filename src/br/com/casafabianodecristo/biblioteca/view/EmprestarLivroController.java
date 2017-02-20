package br.com.casafabianodecristo.biblioteca.view;

import java.util.*;

import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.dto.LivroDto;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.EmprestarLivroInterfaceValidator;
import br.com.casafabianodecristo.biblioteca.model.Emprestimo;
import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class EmprestarLivroController {
	@FXML
	private TextField nomeUsuario;
	
	@FXML
	private TextField nomeLivro;
	
	@FXML
	private Button emprestar;
	
	@FXML
	private Button cancelar;
	
	@FXML
	private Button pesquisarUsuario;
	
	@FXML
	private Button pesquisarLivro;
	
	@FXML
	private TableView <UsuarioDto> usuarios;
	
	@FXML
	private TableColumn<UsuarioDto, String> colunaNome;
	
	@FXML
	private TableColumn<UsuarioDto, String> colunaAtivo;
	
	private List<LivroDto> livrosDto = new ArrayList<LivroDto>();
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private Alertas alerta = new Alertas();
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	ListSelectionView<LivroDto> selectorLivros = new ListSelectionView<>();
	
	@SuppressWarnings("rawtypes")
	private Task emprestarLivro;
	
	public static final int ONE_WEEK = 86400 * 7 * 1000;
	
	@FXML
	private void initialize(){
		pesquisarLivro.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		pesquisarUsuario.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		
		nomeLivro.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					atualizarLivros();
				}				
			}
		}));
		
		pesquisarLivro.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				atualizarLivros();
			}
		});
		
		nomeUsuario.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					atualizarUsuario();
				}				
			}
		}));
		
		pesquisarUsuario.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				atualizarUsuario();
			}
		});
		
		emprestar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
            	avisoCarregando.setVisible(true);
            	avisoCarregando.setText("Realizando empréstimo. Aguarde!");
            	emprestar.setDisable(true);
            	cancelar.setDisable(true);
            	nomeUsuario.setDisable(true);
            	nomeLivro.setDisable(true);
            	usuarios.setDisable(true);
            	selectorLivros.setDisable(true);
            	pesquisarUsuario.setDisable(true);
            	pesquisarLivro.setDisable(true);
				UsuarioDto usuarioSelecionado = usuarios.getSelectionModel().getSelectedItem();
				List<LivroDto> livrosSelecionados = selectorLivros.getTargetItems();
				
				if (EmprestarLivroInterfaceValidator.validarUsuario(usuarioSelecionado) &&
					EmprestarLivroInterfaceValidator.validarLivrosSelecionados(livrosSelecionados)){
					
					EmprestimoDto dto = new EmprestimoDto(0, new Date(), new Date(System.currentTimeMillis() + ONE_WEEK), null, livrosSelecionados, usuarioSelecionado);
					realizarNovoEmprestimo(dto);
					emprestarLivro = taskEmprestarLivro(dto);
					Thread t = new Thread(emprestarLivro);
        			t.setDaemon(true);
        			t.start();
				}
				else{
					avisoCarregando.setVisible(false);
	            	emprestar.setDisable(false);
	            	cancelar.setDisable(false);
	            	nomeUsuario.setDisable(false);
	            	nomeLivro.setDisable(false);
	            	usuarios.setDisable(false);
	            	selectorLivros.setDisable(false);
	            	pesquisarUsuario.setDisable(false);
	            	pesquisarLivro.setDisable(false);
				}
			}
		});
		
		cancelar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				if(alerta.alertaConfirmacaoSair().get() == ButtonType.OK){
					Stage stage = (Stage) cancelar.getScene().getWindow();
	            	stage.close();	
				}			
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskEmprestarLivro(EmprestimoDto dto) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		return realizarNovoEmprestimo(dto);
            }
            
            @Override
    		protected void succeeded() {
            	boolean result = (boolean) getValue();
            	if (!result){
            		alerta.notificacaoErro("Realizar empréstimo", "Ocorreu um erro durante o empréstimo do livro. Por favor tente novamente mais tarde.");
	            	emprestar.setDisable(false);
	            	cancelar.setDisable(false);
	            	nomeUsuario.setDisable(false);
	            	nomeLivro.setDisable(false);
	            	usuarios.setDisable(false);
	            	selectorLivros.setDisable(false);
	            	pesquisarUsuario.setDisable(false);
	            	pesquisarLivro.setDisable(false);
            	}
            	else if (result){
            		Stage stage = (Stage) cancelar.getScene().getWindow();
    	            stage.close();
    	            alerta.notificacaoSucessoSalvarDados("Emprestar livro");
            	}
    		}
        };
    }
	
	private boolean realizarNovoEmprestimo(EmprestimoDto dto){
		return servico.realizarEmprestimo(dto);
	}
	
	private void atualizarUsuario(){
		List<UsuarioDto> u = servico.getUsuarios(nomeUsuario.getText());
		ObservableList<UsuarioDto> itens = FXCollections.observableList(u);
		
		usuarios.setItems(itens);
		
		colunaNome.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeUsuario() + " " + x.getValue().getSobrenome()));
		colunaAtivo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlInativoString()));
	}
	
	private void atualizarLivros(){
		selectorLivros.getSourceItems().removeAll(livrosDto);
		livrosDto.clear();
		List<Livro> l = servico.pesquisaRapidaLivro(nomeLivro.getText(), false, false, false, true);
		
		if(l.size() != 0){
			ModelMapper mapper = new ModelMapper();
			for (Livro livro : l){
				livrosDto.add(mapper.map(livro, LivroDto.class));
			}
		}
		System.out.println(livrosDto);
		selectorLivros.getSourceItems().addAll(livrosDto);		 
		}
}
