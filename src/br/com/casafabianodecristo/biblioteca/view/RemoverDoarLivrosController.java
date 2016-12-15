package br.com.casafabianodecristo.biblioteca.view;

import br.com.casafabianodecristo.biblioteca.model.Livro;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RemoverDoarLivrosController {
	@FXML
	private Button botaoRemover;
	
	@FXML
	private Button botaoDoar;
	
	@FXML
	private Button botaoFechar;
	
	@FXML
	private TableView<Livro> tabelaLivros;
	
	@FXML
	private TableColumn<Livro, String> colunaTitulo;
	
	@FXML
	private TableColumn<Livro, String> colunaSubtitulo;

	@FXML
	private TableColumn<Livro, String> colunaAutor;
	
	@FXML
	private void initialize(){
		colunaTitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
				.getValue()
				.getTitulo()));
		
		colunaSubtitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getSubtitulo()));
		colunaAutor.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeAutor()));
		
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
	            stage.close();
            }            
        });
	}

}
