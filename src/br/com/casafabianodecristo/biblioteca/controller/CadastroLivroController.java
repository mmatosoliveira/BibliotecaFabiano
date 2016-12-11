package br.com.casafabianodecristo.biblioteca.controller;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CadastroLivroController {
	@FXML
	private Button botaoSalvar;
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private TextField tomboPatrimonial;
	
	@FXML
	private TextField titulo;
	
	@FXML
	private TextField subtitulo;
	
	@FXML
	private TextField nomeAutor;
	
	@FXML
	private TextField edicao;
	
	@FXML
	private TextField editora;
	
	@FXML
	private ComboBox<Classificacao> classificacao = new ComboBox<>();
	
	@FXML
	private TextField quantidadeExemplares;
	
	private BibliotecaAppService appService = new BibliotecaAppService();

	public CadastroLivroController(){}
	
	@FXML
	public void initialize(){
		ObservableList<Classificacao> itens = FXCollections.observableArrayList(appService.getClassificacoes());
		classificacao.setItems(itens);
		
		botaoCancelar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoCancelar.getScene().getWindow();
	            stage.close();
            }            
        });
	}
}
