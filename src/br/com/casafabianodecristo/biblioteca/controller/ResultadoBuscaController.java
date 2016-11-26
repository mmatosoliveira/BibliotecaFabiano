package br.com.casafabianodecristo.biblioteca.controller;

import br.com.casafabianodecristo.biblioteca.Principal.Principal;
import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.service.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ResultadoBuscaController {
	@FXML
	private TableView<Livro> tabelaLivros;
	
	@FXML
	private TableColumn<Livro, String> colunaTitulo;
	
	@FXML
	private TableColumn<Livro, String> colunaSubtitulo;

	@FXML
	private TableColumn<Livro, String> colunaAutor;
	
	@FXML
	private TableColumn<Livro, String> colunaEmprestado;
	
	@FXML
	private Button botaoVerDetalhes;
	
	@FXML
	private Button botaoEmprestar;
	
	@FXML
	private Button botaoFechar;
	
	@SuppressWarnings("unused")
	private Principal principal;
	
	Alertas alerta = new Alertas();
	
	public ResultadoBuscaController(){}
	
	@FXML
	private void initialize(){
		colunaTitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getTitulo()));
		colunaSubtitulo.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getSubtitulo()));
		colunaAutor.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeAutor()));
		colunaEmprestado.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlEmprestadoString()));		
		
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
	            stage.close();
            }            
        });
		
		botaoVerDetalhes.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
            	
            	if (livroSelecionado != null){
            		alerta.alertaAviso("Clique", "Vc clicou no ver detalhes com o livro selecionado de tombo: " + livroSelecionado.getTomboPatrimonial());
            	}
            	else
            		alerta.alertaErro("Erro!", "Você deve selecionar um registro na tabela para ver os detalhes.");
            }            
        });
	}

	public void setPrincipal (Principal principal){
		this.principal = principal;
	}


}
