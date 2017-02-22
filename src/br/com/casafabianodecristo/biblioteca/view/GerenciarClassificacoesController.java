package br.com.casafabianodecristo.biblioteca.view;

import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GerenciarClassificacoesController {
	public GerenciarClassificacoesController(){}
	
	@FXML
	private TableView<ClassificacaoDto> classificacoes;
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaCor;
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaDescricao;
	
	@FXML
	private TableColumn<ClassificacaoDto, String> colunaHexa;
	
	@FXML
	private Button botaoAdicionar;
	
	@FXML
	private Button botaoEditar;
	
	@FXML
	private Button botaoRemover;
	
	@FXML
	private Button botaoFechar;
	
	private Principal principal = new Principal();
	
	@FXML
	private void initialize(){
		atualizarGrid();
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
	            stage.close();
            }            
        });
		
		botaoAdicionar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	principal.carregarCadastrarClassificacao();
            }            
        });
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