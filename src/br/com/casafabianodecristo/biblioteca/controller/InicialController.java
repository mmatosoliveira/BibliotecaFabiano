package br.com.casafabianodecristo.biblioteca.controller;

import java.util.List;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.utils.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.control.*;

public class InicialController {
	@FXML
	private ImageView iconeSalvarLembrete;
	
	@FXML
	private ImageView iconePesquisar;
	
	@FXML
	private ImageView iconeAtualizarDevolucoes;
	
	@FXML
	private TextArea lembrete;
	
	@FXML
	private Label dataHora;
	
	@FXML
	private Label usuarioAcesso;
	
	@FXML
	private TableView<Emprestimo> tabelaEmprestimosPendentes;
	
	@FXML
	private TableColumn<Emprestimo, String> colunaNomeUsuario;
	
	@FXML
	private TableColumn<Emprestimo, String> colunaTitulo;
	
	@FXML
	private MenuItem itemSair = new MenuItem("Close");
	
	@FXML
	private MenuItem itemCadastrarLivros;
	
	@FXML
	private MenuItem itemCadastrarUsuario;
	
	@FXML
	private MenuItem itemEditarDadosUsuario;
	
	@FXML
	private MenuItem itemRemoverUsuario;
	
	@FXML
	private MenuItem itemCadastrarClassificacao;
	
	@FXML
	private MenuItem itemRemoverDoarLivro;
	
	@FXML
	private MenuItem itemConsultarEmprestimo;
	
	@FXML
	private TextField dadoLivroPesquisa;
	
	@FXML
	private CheckBox checkTitulo;
	
	@FXML
	private CheckBox checkAutor;
	
	@FXML
	private CheckBox checkTombo;
	
	@FXML
	private Button botaoCadastrarLivro;
	
	@FXML
	private Button botaoCadastrarUsuario;
	
	private List<Livro> livros;
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private Principal principal;
	
	private Alertas alerta = new Alertas();
	
	private Tooltip tpPesquisa = new Tooltip();
	
	private Tooltip tpLembrete = new Tooltip();
	
	public InicialController(){}
	
	private void atualizarGrid(){
		colunaNomeUsuario.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getUsuario()
				.getNomeUsuario()));
		colunaTitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
					.getValue()
					.getLivro()
					.getTitulo()));
	}
	
	@FXML
	private void initialize(){
		tpPesquisa.setText("Digite alguma informação e pesquise um livro!");
		dadoLivroPesquisa.setTooltip(tpPesquisa);
		tpLembrete.setText("Digite algum lembrete e aperte no disquete para salvar!");
		lembrete.setTooltip(tpLembrete);
		
		atualizarGrid();	
		
		iconeAtualizarDevolucoes.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            		ObservableList<Emprestimo> itens =FXCollections.observableList(servico.getDevolucoesPrevistas());
            		tabelaEmprestimosPendentes.setItems(itens);
            		atualizarGrid();
            }            
        });
		
		dadoLivroPesquisa.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					consultarLivro();
				}				
			}
		}));
		
		itemCadastrarLivros.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarCadastroLivros();			
			}
		});
		
		itemRemoverDoarLivro.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarRemoverDoarLivro();			
			}
		});
		
		itemCadastrarClassificacao.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarCadastrarClassificacao();			
			}
		});
		
		itemConsultarEmprestimo.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarConsultaEmprestimo();			
			}
		});
		
		itemCadastrarUsuario.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarCadastroUsuario();			
			}
		});
		
		itemEditarDadosUsuario.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				//principal.carregarBuscaUsuario();
			}
		});
		
		itemSair.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				if (alerta.alertaConfirmacaoSair().get() == ButtonType.OK)
					System.exit(0);				
			}
		});
		
		
		iconeSalvarLembrete.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	/*servico.createEntityManagerFactory();
	    			servico.salvarLembrete(lembrete.getText());
	    		servico.closeEntityManagerFactory();*/
            }            
        });
		
		iconePesquisar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	  consultarLivro();	
            }            
        });		
		
		botaoCadastrarLivro.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	principal.carregarCadastroLivros();	
            }            
        });
		
		botaoCadastrarUsuario.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	principal.carregarCadastroUsuario();	
            }            
        });
	}
	
	public void consultarLivro(){
		String textoPesquisa = dadoLivroPesquisa.getText();
    	boolean titulo = checkTitulo.isSelected();
    	boolean autor = checkAutor.isSelected();
    	boolean tombo = checkTombo.isSelected();
    	String textoAlerta = "Uma pesquisa vazia retorna todos os livros do sistema e isso pode demorar. Deseja continuar?";
    	
    	if (textoPesquisa.equals("")){
    		if(alerta.alertaConfirmacao(textoAlerta).get() == ButtonType.OK){	
    			livros = servico.pesquisaRapidaLivro(textoPesquisa, titulo, autor, tombo);
    			principal.carregarResultadoBusca(livros);
        	}     
    	}
    	else{
			livros = servico.pesquisaRapidaLivro(textoPesquisa, titulo, autor, tombo);
			principal.carregarResultadoBusca(livros);
    	}
    	checkTitulo.setSelected(false);
    	checkAutor.setSelected(false);
    	checkTombo.setSelected(false);
	}
	
	public void setPrincipal (Principal principal){
		this.principal = principal;
	}
}
