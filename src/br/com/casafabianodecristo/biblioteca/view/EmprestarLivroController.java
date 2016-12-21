package br.com.casafabianodecristo.biblioteca.view;

import java.util.*;
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
	
	@FXML
	private TableView<LivroDto> livros;
	
	@FXML
	private TableColumn<LivroDto, String> colunaTitulo;
	
	@FXML
	private TableColumn<LivroDto, String> colunaSubtitulo;

	@FXML
	private TableColumn<LivroDto, String> colunaAutor;
	
	@FXML
	private TableColumn<LivroDto, String> colunaEmprestado;
	
	@FXML
	private ProgressIndicator indicador;
	
	private List<LivroDto> livrosDto = new ArrayList<LivroDto>();
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private Alertas alerta = new Alertas();
	
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
            	indicador.setVisible(true);
            	emprestar.setDisable(true);
            	cancelar.setDisable(true);
            	nomeUsuario.setDisable(true);
            	nomeLivro.setDisable(true);
            	usuarios.setDisable(true);
            	livros.setDisable(true);
            	pesquisarUsuario.setDisable(true);
            	pesquisarLivro.setDisable(true);
				UsuarioDto usuarioSelecionado = usuarios.getSelectionModel().getSelectedItem();
				LivroDto livroSelecionado = livros.getSelectionModel().getSelectedItem();
				
				if (EmprestarLivroInterfaceValidator.validarRegistrosSelecionados(usuarioSelecionado, livroSelecionado)){
					EmprestimoDto dto = new EmprestimoDto(0, new Date(), new Date(System.currentTimeMillis() + ONE_WEEK), null, livroSelecionado, usuarioSelecionado);
					realizarNovoEmprestimo(dto);
					emprestarLivro = taskEmprestarLivro(dto);
					Thread t = new Thread(emprestarLivro);
        			t.setDaemon(true);
        			t.start();
				}
				else{
	            	indicador.setVisible(false);
	            	emprestar.setDisable(false);
	            	cancelar.setDisable(false);
	            	nomeUsuario.setDisable(false);
	            	nomeLivro.setDisable(false);
	            	usuarios.setDisable(false);
	            	livros.setDisable(false);
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
            		indicador.setVisible(false);
	            	emprestar.setDisable(false);
	            	cancelar.setDisable(false);
	            	nomeUsuario.setDisable(false);
	            	nomeLivro.setDisable(false);
	            	usuarios.setDisable(false);
	            	livros.setDisable(false);
	            	pesquisarUsuario.setDisable(false);
	            	pesquisarLivro.setDisable(false);
            	}
            	else if (result){
            		Stage stage = (Stage) cancelar.getScene().getWindow();
    	            stage.close();
    	            alerta.notificacaoSucessoSalvarDados("Cadastrar livro");
            	}
    		}
        };
    }
	
	private boolean realizarNovoEmprestimo(EmprestimoDto dto){
		Emprestimo e = servico.realizarEmprestimo(dto);
		return (e == null) ? false : true;
	}
	
	private void atualizarUsuario(){
		List<UsuarioDto> u = servico.getUsuarios(nomeUsuario.getText());
		ObservableList<UsuarioDto> itens = FXCollections.observableList(u);
		
		usuarios.setItems(itens);
		
		colunaNome.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeUsuario() + " " + x.getValue().getSobrenome()));
		colunaAtivo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlInativoString()));		
	}
	
	private void atualizarLivros(){
		List<Livro> l = servico.pesquisaRapidaLivro(nomeLivro.getText(), false, false, false);
		
		if(l.size() != 0){
			ModelMapper mapper = new ModelMapper();
			for (Livro livro : l){
				livrosDto.add(mapper.map(livro, LivroDto.class));
			}
			ObservableList<LivroDto> itens = FXCollections.observableList (livrosDto);
			livros.setItems(itens);
		}

		colunaTitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getTitulo()));
		
		colunaSubtitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getSubtitulo()));
		colunaAutor.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeAutor()));
		colunaEmprestado.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlEmprestadoString()));
	}
}
