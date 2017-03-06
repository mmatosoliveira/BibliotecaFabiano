package br.com.casafabianodecristo.biblioteca.view;

import java.util.*;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.dto.LivroDto;
import br.com.casafabianodecristo.biblioteca.dto.ReciboEmprestimoDto;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.EmprestarLivroInterfaceValidator;
import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.utils.GeradorDeRelatorios;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EmprestarLivroController {
	@FXML
	private AnchorPane pane;
	
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
	
	@FXML
	private BorderPane paneCarregando;
	
	@SuppressWarnings("rawtypes")
	private Task emprestarLivro;
	
	public static final int ONE_WEEK = 86400 * 7 * 1000;
	
	private int validacaoUsuario;
	
	private int validacaoLivro;
	
	private EmprestimoDto dtoEmprestimo;
	
	private ReciboEmprestimoDto dtoRecibo = new ReciboEmprestimoDto();
	
	@FXML
	private void initialize(){
		Label labelSelecionado = new Label("Disponível");
		labelSelecionado.setId("labelSelecionado");
		labelSelecionado.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		selectorLivros.setSourceHeader(labelSelecionado);
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
		
		pesquisarUsuario.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					atualizarUsuario();
				}				
			}
		}));
		
		pesquisarLivro.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					atualizarUsuario();
				}				
			}
		}));
		
		emprestar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
            	avisoCarregando.setText("Realizando empréstimo. Aguarde!");
            	mudarEstadoCamposTela(true);
				
				emprestarLivro = taskEmprestarLivro();
				Thread t = new Thread(emprestarLivro);
    			t.setDaemon(true);
    			t.start();			
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
	public Task taskEmprestarLivro() {
        return new Task() {
            @Override
            protected Integer call() throws Exception {
            	UsuarioDto usuarioSelecionado = usuarios.getSelectionModel().getSelectedItem();
				List<LivroDto> livrosSelecionados = selectorLivros.getTargetItems();
				validacaoUsuario = EmprestarLivroInterfaceValidator.validarUsuario(usuarioSelecionado);
				validacaoLivro = EmprestarLivroInterfaceValidator.validarLivrosSelecionados(livrosSelecionados); 
            	if (validacaoUsuario == 0 && validacaoLivro == 0){
    					dtoEmprestimo = new EmprestimoDto(0, new Date(), new Date(System.currentTimeMillis() + ONE_WEEK), null, livrosSelecionados, usuarioSelecionado);
    					super.succeeded();
    					return (realizarNovoEmprestimo(dtoEmprestimo) == true) ? 1 : 0;
    			}
    			super.failed();
    			return 2;
            }
            
            @Override
    		protected void succeeded() {
            	int result = (int) getValue();
            	
            	if (result == 0){
            		alerta.notificacaoErro("Realizar empréstimo", "Ocorreu um erro durante o empréstimo do livro. Por favor tente novamente mais tarde.");
            		mudarEstadoCamposTela(false);
            	}
            	else if (result == 1){
            		Stage stage = (Stage) cancelar.getScene().getWindow();
    	            stage.close();
    	            alerta.notificacaoSucessoSalvarDados("Emprestar livro");
    	            gerarRecibo();
            	}
            	else{
            		if(validacaoUsuario == 1){
            			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar um usuário para realizar um empréstimo.");
            			mudarEstadoCamposTela(false);
            		}	
    				else if(validacaoLivro == 1){
    					alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar pelo menos um livro para realizar um empréstimo.");
    					mudarEstadoCamposTela(false);
    				}
    				else if(validacaoLivro == 2){
    					alerta.notificacaoAlerta("Emprestar Livro", "Não é permitido selecionar mais que 3 (três) livros para um empréstimo.");
    					mudarEstadoCamposTela(false);
    				}
            	}
            	
    		}
        };
    }
	
	private void gerarRecibo (){
		dtoRecibo.setNomeUsuario(dtoEmprestimo.getUsuario().getNomeUsuario());
		informarLivrosRecibo();
		dtoRecibo.setDataDevolucao(dtoEmprestimo.getDevolucaoPrevista());
		
		List<ReciboEmprestimoDto> lista = new ArrayList<>();
		lista.add(dtoRecibo);
		GeradorDeRelatorios gerador = new GeradorDeRelatorios("ReciboEmprestimo.jrxml", "C:/Users/Recibo"+ new Random().nextInt() +".pdf");
		
		try {
			gerador.gerar(lista);
		} catch (Exception e) {
			System.out.println("Deu Ruim ------------------->");
			e.printStackTrace();
		}
	}
	
	private void informarLivrosRecibo(){
		int quantidadeLivros = dtoEmprestimo.getLivros().size();
		if(quantidadeLivros == 1){
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(null);
			dtoRecibo.setTerceiroLivro(null);
		}
		else if(quantidadeLivros == 2){
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(dtoEmprestimo.getLivros().get(1).getNomeConcatenadoLivro());
			dtoRecibo.setTerceiroLivro(null);
		}
		else{
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(dtoEmprestimo.getLivros().get(1).getNomeConcatenadoLivro());
			dtoRecibo.setTerceiroLivro(dtoEmprestimo.getLivros().get(2).getNomeConcatenadoLivro());
		}
	}
	
	private void mudarEstadoCamposTela(boolean estado){
		paneCarregando.setVisible(estado);
		avisoCarregando.setVisible(estado);
    	emprestar.setDisable(estado);
    	cancelar.setDisable(estado);
    	nomeUsuario.setDisable(estado);
    	nomeLivro.setDisable(estado);
    	usuarios.setDisable(estado);
    	selectorLivros.setDisable(estado);
    	pesquisarUsuario.setDisable(estado);
    	pesquisarLivro.setDisable(estado);
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
		selectorLivros.getSourceItems().addAll(livrosDto);		 
	}
}
