package br.com.casafabianodecristo.biblioteca.view;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.components.Numberfield;
import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class RemoverDoarLivrosController {
	@FXML
	private Button botaoRemoverLivroAcervo;
	
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
	
	@FXML private Numberfield tomboPatrimonial;
	
	@FXML private Button botaoConsultar;
	
	private BibliotecaAppService service = new BibliotecaAppService();
	
	private Alertas alerta = new Alertas();
	
	@FXML
	private void initialize(){
		botaoRemoverLivroAcervo.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoDoar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
	            stage.close();
            }            
        });
	}
	
	@FXML private void consultarLivro(Event event){
		String tomboPatrimonial = this.tomboPatrimonial.getText();
		
		if(tomboPatrimonial.isEmpty()){
			alerta.notificacaoAlerta("Remoção/Doação de livros", "É obrigatório digitar o tombo patrimonial do exemplar que será doado ou removido do acervo.");
			event.consume();
		}
		else {
			ObservableList<Livro> itens = FXCollections.observableList(service.pesquisaRapidaLivro(tomboPatrimonial, false, false, true, true));
			
			tabelaLivros.setItems(itens);
			
			colunaTitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x
					.getValue()
					.getTitulo()));
			
			colunaSubtitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getSubtitulo()));
			colunaAutor.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeAutor()));
		}
		
		
	}
}