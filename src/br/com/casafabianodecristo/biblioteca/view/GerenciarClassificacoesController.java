package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;

import org.controlsfx.control.MaskerPane;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GerenciarClassificacoesController {
	public GerenciarClassificacoesController(){}
	
	@FXML
	private TableView<ClassificacaoDto> classificacoes = new TableView<ClassificacaoDto>();
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaCor = new TableColumn<ClassificacaoDto, String>();
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaDescricao = new TableColumn<ClassificacaoDto, String>();
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaHexa = new TableColumn<ClassificacaoDto, String>();
	
	@FXML
	private Button botaoAdicionar = new Button();
	
	@FXML
	private Button botaoEditar = new Button();
	
	@FXML
	private Button botaoRemover = new Button();
	
	@FXML
	private Button botaoFechar = new Button();
	
	@FXML
	private Button botaoAtualizarGrid = new Button();
	
	@FXML
	private MaskerPane avisoCarregando;
	
	@FXML
	private BorderPane paneCarregando;
	
	private Principal principal = new Principal();
	
	private Alertas alerta = new Alertas();
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private List<ClassificacaoDto> lista = servico.getClassificacoes();;
	
	private ObservableList<ClassificacaoDto> itens = FXCollections.observableArrayList(lista);
	
	@SuppressWarnings("rawtypes")
	private Task removerClassificacao;
	
	@SuppressWarnings("rawtypes")
	public Task taskRemoverClassificacaoLivro() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
            	int result = 0;
        		try{ result = servico.removerClassificacao(classificacoes.getSelectionModel().getSelectedItem().getId());}
        		catch(MySQLIntegrityConstraintViolationException e){
        			result = 1;
        		}
        		return result;
            }
            
			@Override
    		protected void succeeded() {
				int result = (int) getValue();
				
				if(result == 0){                    
                    alerta.notificacaoSucessoSalvarDados("Remover classificação");
                    paneCarregando.setVisible(false);
        			avisoCarregando.setVisible(false);
        			classificacoes.setItems(FXCollections.observableArrayList(servico.getClassificacoes()));
				}
				else if(result == 1){
					paneCarregando.setVisible(false);
        			avisoCarregando.setVisible(false);
        			alerta.alertaErro("Remover classificação", "Não é permitido remover uma classificação de item que esteja sendo utilizada em um livro.");
				}
    		}
        };
    }
	
	@FXML
	public void initialize(){
		botaoAdicionar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoEditar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoRemover.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		realizarConsulta(null);
		botaoAtualizarGrid.setVisible(false);

		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if (alerta.alertaConfirmacaoSair().get() == ButtonType.OK){
            		Stage stage = (Stage) botaoFechar.getScene().getWindow();
    	            stage.close();
		    	}	
            	else {
            		event.consume();
            	}
            }            
        });
		
		botaoAdicionar.setOnMousePressed((e) -> principal.carregarTelaCadastro("CadastrarClassificacao", "Cadastrar classificação de livros", true, false, classificacoes));
		
		botaoEditar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(classificacoes.getSelectionModel().getSelectedItem() != null)
            		principal.carregarEditarClassificacao(classificacoes.getSelectionModel().getSelectedItem(), classificacoes);
            	else 
            		alerta.notificacaoAlerta("Editar classificação", "É obrigatório selecionar uma classificação para editar.");
            }            
        });
		
		botaoRemover.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(classificacoes.getSelectionModel().getSelectedItem() != null){
            		if (alerta.alertaConfirmacao("Deseja realmente excluir a classificação de livro selecionada?").get() == ButtonType.OK){
            			avisoCarregando.setText("Removendo classificação... Aguarde!");
            			paneCarregando.setVisible(true);
            			avisoCarregando.setVisible(true);
            			removerClassificacao = taskRemoverClassificacaoLivro();
            			Thread t = new Thread(removerClassificacao);
            			t.setDaemon(true);
            			t.start();
    		    	}	
                	else {
                		event.consume();
                	}
            	}
            	else 
            		alerta.notificacaoAlerta("Remover classificação", "É obrigatório selecionar uma classificação para remover.");
            }            
        });
	}
	
	public void realizarConsulta(){
		this.realizarConsulta(null);
	}
	
	private void realizarConsulta(List<ClassificacaoDto> dto){
		if(dto == null){
			itens = FXCollections.observableList(servico.getClassificacoes());
			classificacoes.setItems(itens);
			classificacoes.refresh();
		}
		else{ 
			ObservableList<ClassificacaoDto> itens = classificacoes.getItems();
			itens.removeAll(dto);
			itens = FXCollections.observableList(dto);
			classificacoes.setItems(itens);
		}
		atualizarGrid();
	}
	
	public List<ClassificacaoDto> getClassificacoes(){
		return this.lista;
	}
	
	public void setItens(List<ClassificacaoDto> dto){
		itens = FXCollections.observableArrayList(dto);
	}
	
	private void atualizarGrid(){
		colunaCor.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getCor()));
		
		colunaCor.setCellFactory(new Callback<TableColumn<ClassificacaoDto, String>, TableCell<ClassificacaoDto, String>>() {
            public TableCell<ClassificacaoDto, String> call(TableColumn<ClassificacaoDto, String> p) {
                TableCell<ClassificacaoDto, String> cell = new TableCell<ClassificacaoDto, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : getString());
                        setStyle("-fx-background-color:"+getString());
                    }

                    private String getString() {
                        return getItem() == null ? "" : getItem().toString();
                    }
                };
                return cell;
            }});
		
		colunaDescricao.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getDescricao()));
		
		colunaHexa.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getCor()));
	}
}