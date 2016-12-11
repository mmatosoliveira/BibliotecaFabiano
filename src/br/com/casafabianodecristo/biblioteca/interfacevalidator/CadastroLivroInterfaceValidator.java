package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import java.util.*;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.scene.control.*;

public class CadastroLivroInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarCamposObrigatorios(List<TextField> camposTexto, ComboBox<Classificacao> classificacao)
	{
		boolean[] validado = new boolean [7];
		for (TextField item : camposTexto){
			if (item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() == null){
				item.setStyle("-fx-background-color: #ff7c7c;");
				classificacao.setStyle("-fx-background-color: #ff7c7c;");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (!item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() == null){
				item.setStyle("-fx-background-color: white;");
				classificacao.setStyle("-fx-background-color: #ff7c7c;");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() != null){
				item.setStyle("-fx-background-color: #ff7c7c;");
				classificacao.setStyle("-fx-background-color: white;");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (!item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() != null){
				item.setStyle("-fx-background-color: white;");
				classificacao.setStyle("-fx-background-color: white;");
				validado[camposTexto.indexOf(item)] = true;
			}
		}
		
		for (int i = 0; i < validado.length; i++){
			if (validado[i] == false){
				alerta.notificacaoAlerta("Cadastrar livro", "Verifique os campos obrigatórios e tente novamente.");
				return false;
			}
		}
		
		return true;
	}
}
