package br.com.casafabianodecristo.biblioteca.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.*;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.utils.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javafx.scene.control.*;

public class InicialController {
	@FXML
	private ImageView iconePesquisar;
	
	@FXML
	private ImageView iconeAtualizarDevolucoes;
	
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
	private MenuItem itemRealizarBackup;
	
	@FXML
	private MenuItem itemGerarEtiquetas;
	
	@FXML
	private MenuItem itemGerenciarClassificacoes;
	
	@FXML
	private MenuItem itemMeusDados;
	
	@FXML
	private MenuItem itemCadastrarLivros;
	
	@FXML
	private MenuItem itemCadastrarUsuario;
	
	@FXML
	private MenuItem itemEditarDadosUsuario;
	
	@FXML
	private MenuItem itemCadastrarClassificacao;
	
	@FXML
	private MenuItem itemRemoverDoarLivro;
	
	@FXML
	private MenuItem itemConsultarEmprestimo;
	
	@FXML
	private MenuItem itemRealizarEmprestimo;
	
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
	
	@FXML
	private Button botaoEmprestarLivro;
	
	@FXML
	private Button botaoRenovarEmprestimo;
	
	@FXML
	private MenuItem itemGerarRelatorio;
	
	@FXML
	private Label labelId;
	
	private List<Livro> livros;
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private Principal principal;
	
	private Alertas alerta = new Alertas();
	
	private Tooltip tpPesquisa = new Tooltip();
	
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
		botaoCadastrarLivro.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoCadastrarUsuario.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoEmprestarLivro.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoRenovarEmprestimo.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		tpPesquisa.setText("Digite alguma informação e pesquise um livro!");
		dadoLivroPesquisa.setTooltip(tpPesquisa);
		
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
		
		itemGerarRelatorio.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarGerenciamentoRelatorios();
			}
		});
		
		itemGerarEtiquetas.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarGerenciamentoRelatorios();
			}
		});
		
		itemCadastrarLivros.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarCadastroLivros();			
			}
		});
		
		itemGerenciarClassificacoes.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarGerenciamentoClassificacoes();			
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
				List<EmprestimoDto> emprestimos = servico.getEmprestimos();
				principal.carregarConsultaEmprestimo(emprestimos);			
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
		
		itemRealizarEmprestimo.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				principal.carregarEmprestimoLivro();			
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
		
		botaoEmprestarLivro.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	principal.carregarEmprestimoLivro();	
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
    			livros = servico.pesquisaRapidaLivro(textoPesquisa, titulo, autor, tombo, false);
    			principal.carregarResultadoBusca(livros);
        	}     
    	}
    	else{
			livros = servico.pesquisaRapidaLivro(textoPesquisa, titulo, autor, tombo, false);
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
