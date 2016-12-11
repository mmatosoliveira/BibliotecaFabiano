package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.scene.control.*;

public class LoginInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarCamposObrigatorios(TextField nomeUsuario, PasswordField senha){
		if (nomeUsuario.getText().equals("") && senha.getText().equals("")){
			nomeUsuario.setStyle("-fx-background-color: #ff7c7c;");
    		senha.setStyle("-fx-background-color: #ff7c7c;");
    		alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		nomeUsuario.requestFocus();
    		return false;
    	}
    	else if (senha.getText().equals("") && !nomeUsuario.getText().equals("")){
    		senha.setStyle("-fx-background-color: #ff7c7c;");
    		nomeUsuario.setStyle("-fx-background-color: white;");
    		alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		senha.requestFocus();
    		return false;
		}
		else if (nomeUsuario.getText().equals("") && !senha.getText().equals("")){
			nomeUsuario.setStyle("-fx-background-color: #ff7c7c;");
			senha.setStyle("-fx-background-color: white;");
			alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		nomeUsuario.requestFocus();
    		return false;
		}	
		else {
			senha.setStyle("-fx-background-color: white;");
			nomeUsuario.setStyle("-fx-background-color: white;");
			return true;
		}
	}
}
