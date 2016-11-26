package br.com.casafabianodecristo.biblioteca.controller;

import javafx.collections.FXCollections;
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
	private ComboBox<String> classificacao = new ComboBox<>();
	
	@FXML
	private TextField quantidadeExemplares;

	public CadastroLivroController(){}
	
	@FXML
	public void initialize(){
		//classificacao.setItems(FXCollections.observableList(ClassificacaoLivroEnum.getDescricoes()));
		botaoCancelar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoCancelar.getScene().getWindow();
	            stage.close();
            }            
        });
	}
}
