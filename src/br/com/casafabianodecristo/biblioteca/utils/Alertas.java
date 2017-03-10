package br.com.casafabianodecristo.biblioteca.utils;

import java.util.Optional;
import org.controlsfx.control.Notifications;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Alertas {
	Notifications notificacao;
	
	public Alertas(){}
	
	@SuppressWarnings("static-access")
	public void notificacaoSucessoSalvarDados(String titulo){
		notificacao.create()
        .title(titulo)
        .text("Sucesso ao salvar dados!")
        .showInformation(); 
	}
	
	@SuppressWarnings("static-access")
	public void notificacaoSucesso(String titulo, String texto){
		notificacao.darkStyle();
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showInformation();     		
	}

	@SuppressWarnings("static-access")
	public void notificacaoErro(String titulo, String texto){
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showError();     		
	}
	
	@SuppressWarnings("static-access")
	public void notificacaoAlerta(String titulo, String texto){
		notificacao.create()
        .title(titulo)
        .text(texto)
        .showWarning();     		
	}
	
	public void alertaErro(String titulo, String texto){
		Alert alertErro = new Alert(AlertType.ERROR);
		setIcon(alertErro);
        alertErro.setTitle(titulo);
        alertErro.setHeaderText(texto);
        alertErro.showAndWait();
	}
	
	public void alertaSucesso(String titulo, String texto){
		Alert alertInformacao = new Alert(AlertType.INFORMATION);
		setIcon(alertInformacao);
		alertInformacao.setTitle(titulo);
		alertInformacao.setHeaderText(texto);
		alertInformacao.showAndWait();
	}
	
	public void alertaSucessoSalvarDados(String titulo){
		Alert alertInformacao = new Alert(AlertType.INFORMATION);
		setIcon(alertInformacao);
		alertInformacao.setTitle(titulo);
		alertInformacao.setHeaderText("Sucesso ao salvar dados!");
		alertInformacao.showAndWait();
	}
	
	public void alertaAviso(String titulo, String texto){
		Alert alertAviso = new Alert(AlertType.WARNING);
		setIcon(alertAviso);
		alertAviso.setTitle(titulo);
		alertAviso.setHeaderText(texto);
		alertAviso.showAndWait();
	}
	
	public Optional<ButtonType> alertaConfirmacao(String texto){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText(texto);
		alertConfirmacao.setContentText(null);
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
 
	public Optional<ButtonType> alertaConfirmacaoSair(){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText("Deseja realmente sair?");
		alertConfirmacao.setContentText(null);	
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
	
	public Optional<ButtonType> alertaConfirmacaoSairTelaCadastro(){
		Alert alertConfirmacao = new Alert(AlertType.CONFIRMATION);
		setIcon(alertConfirmacao);
		alertConfirmacao.setTitle("Confirmação");
		alertConfirmacao.setHeaderText("Deseja realmente sair e perder os dados que não foram salvos?");
		alertConfirmacao.setContentText(null);	
		Optional<ButtonType> result = alertConfirmacao.showAndWait();
		return result;
	}
	
	private void setIcon(Alert alert){
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:resources/images/icon-principal.png"));
	}
}
