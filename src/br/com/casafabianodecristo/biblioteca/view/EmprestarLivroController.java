package br.com.casafabianodecristo.biblioteca.view;

import java.util.*;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

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
	
	@SuppressWarnings("rawtypes")
	private Task gerarRecibo;
	
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
		getUsuarios();
		
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
					mudarEstadoCamposTela(true);
					atualizarUsuario();
					mudarEstadoCamposTela(false);
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
            	avisoCarregando.setText("Realizando empréstimo... Aguarde!");
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
	public Task taskGerarRecibo(){
		return new Task(){
			@Override
    		protected void succeeded() {
				int result = (int) getValue();
				
				if(result == 1){
					alerta.notificacaoSucesso("Emprestar livros", "Sucesso ao realizar empréstimo!");
					Stage stage = (Stage) cancelar.getScene().getWindow();
	            	stage.close();
				}
				else if(result == 2){
					alerta.alertaErro("Imprimir recibo", "Ocorreu um erro ao imprimir o recibo do empréstimo. \nTalvez a impressora informada não esteja ligada ou instalada corretamente.");
					Stage stage = (Stage) cancelar.getScene().getWindow();
	            	stage.close();
	            	this.done();
				}
				else if(result == 3){
					alerta.alertaErro("Imprimir recibo", "Não existe uma impressora configurada como padrão para imprimir os recibos de empréstimos.\nPara configurar o sistema vá em: Sistema > Selecionar impressora padrão para recibos.");
					Stage stage = (Stage) cancelar.getScene().getWindow();
	            	stage.close();
	            	this.done();
				}
				else {
					alerta.alertaErro("Gerar recibo", "Ocorreu um erro ao gerar o recibo do empréstimo. Acesse a aplicação \"Consultar empréstimos\" e tente imprimir o recibo novamente.");
					this.done();
				}
    		}

			@Override
			protected Object call() throws Exception {
				return gerarRecibo();
			}
		};
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
    					boolean result = realizarNovoEmprestimo(dtoEmprestimo); 
    	            	if(result)
    	            		return 1;
    	            	else return 0;
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
            		avisoCarregando.setText("Imprimindo recibo do empréstimo... Aguarde!");
            		gerarRecibo = taskGerarRecibo();
            		Thread t = new Thread(gerarRecibo);
        			t.setDaemon(true);
        			t.start();
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
	
	private int gerarRecibo (){
		dtoRecibo.setNomeUsuario(dtoEmprestimo.getUsuario().toString());
		informarLivrosRecibo();
		dtoRecibo.setDataDevolucao(dtoEmprestimo.getDevolucaoPrevista());
		
		List<ReciboEmprestimoDto> lista = new ArrayList<>();
		lista.add(dtoRecibo);
		GeradorDeRelatorios gerador = new GeradorDeRelatorios("ReciboEmprestimo.jrxml");
		String nomeImpressora = "";
		try {
			nomeImpressora = servico.getParametrizacaoSistemaVigente().getNomeImpressoraPadrao();
		} catch (Exception e1) {
			return 3;
		}
		
		try {
			JasperPrint jp = gerador.gerar(lista);
			try{
				gerador.imprimir(jp, nomeImpressora);
			}
			catch(JRException ex){
				return 2;
			}
            
		} catch (Exception e) {
			return 0;
		}
		return 1;
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
	}
	
	private boolean realizarNovoEmprestimo(EmprestimoDto dto){
		return servico.realizarEmprestimo(dto);
	}
	
	private void atualizarUsuario(){
		avisoCarregando.setText("Carregando usuários, aguarde!");
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
	
	private void getUsuarios(){
		List<UsuarioDto> listaUsuarios = (List<UsuarioDto>) FXCollections.observableArrayList(servico.getUsuarios(""));
		TextFields.bindAutoCompletion(nomeUsuario, listaUsuarios);
	}
}
