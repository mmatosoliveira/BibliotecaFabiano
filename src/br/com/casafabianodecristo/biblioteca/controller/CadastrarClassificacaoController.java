package br.com.casafabianodecristo.biblioteca.controller;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.utils.RGBConverter;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CadastrarClassificacaoController {
	@FXML
	private Button botaoSalvar;
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private TextField descricao;
	
	@FXML
	private ColorPicker escolherCor;
	
	private BibliotecaAppService appService = new BibliotecaAppService();
	
	Alertas alerta = new Alertas();
	
	public CadastrarClassificacaoController (){}
	
	@FXML
	private void initialize(){
		

		botaoSalvar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if(validarCampos()){
		    		ClassificacaoDto dto = new ClassificacaoDto(0, descricao.getText(), RGBConverter.toRGBCode(escolherCor.getValue()));
		    		appService.cadastrarClassificacao(dto);
		    	}
		    }
		});
		
		
		
		botaoCancelar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if (alerta.alertaConfirmacaoSair().get() == ButtonType.OK){
		    		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
		    		stage.close();	    	
		    	}		    	
		    }
		});
	}
	
	private boolean validarCampos(){
		 boolean result;
		 System.out.println(escolherCor.getValue());
		 
		 if (descricao.getText() == null || descricao.getText().equals(""))
		 {
			 result = false;
			 descricao.setStyle("-fx-background-color: #ff7c7c;");
		 }
		 else{
			 result = true;
			 descricao.setStyle("-fx-background-color: white;");
			 escolherCor.setStyle("-fx-background-color: white;");
		 }
			 
		 return result;
	 }
}
