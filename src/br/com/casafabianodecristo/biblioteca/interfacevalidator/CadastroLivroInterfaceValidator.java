package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import java.util.*;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.scene.control.*;

public class CadastroLivroInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarTamanhosMaximos(TextField titulo, TextField subtitulo, TextField nomeAutor, TextField editora, TextField tomboPatrimonial){
		String textoTitulo = titulo.getText();
		String textoSubtitulo = subtitulo.getText();
		String textoNomeAutor = nomeAutor.getText();
		String textoEditora = editora.getText();
		String tombo = tomboPatrimonial.getText();
		
		if (textoTitulo.length() > 300){
			titulo.requestFocus();
			alerta.notificacaoAlerta("Cadastrar livro", "O tamanho máximo para o título é de 300 caracteres.");
			return false;
		}
		else if (textoSubtitulo.length() > 300){
			subtitulo.requestFocus();
			alerta.notificacaoAlerta("Cadastrar livro", "O tamanho máximo para o subtítulo é de 300 caracteres.");
			return false;
		}	
		else if (textoNomeAutor.length() > 300){
			nomeAutor.requestFocus();
			alerta.notificacaoAlerta("Cadastrar livro", "O tamanho máximo para o nome do autor é de 300 caracteres.");
			return false;
		}
		else if (textoEditora.length() > 50){
			editora.requestFocus();
			alerta.notificacaoAlerta("Cadastrar livro", "O tamanho máximo para o nome da editora é de 50 caracteres.");
			return false;
		}
		else if (tombo.length() > 6){
			tomboPatrimonial.requestFocus();
			alerta.notificacaoAlerta("Cadastrar livro", "O tamanho máximo para o tombo patrimonial é de 6 caracteres.");
			return false;
		}
		
		return true;
	}
	
	public static boolean validarCamposObrigatorios(List<TextField> camposTexto, ComboBox<Classificacao> classificacao)
	{
		boolean[] validado = new boolean [6];
		for (TextField item : camposTexto){
			if (item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() == null){
				item.setStyle("-fx-background-color: #ff7c7c;");
				classificacao.setStyle("-fx-background-color: #ff7c7c;");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (!item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() == null){
				item.setStyle("");
				classificacao.setStyle("-fx-background-color: #ff7c7c;");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() != null){
				item.setStyle("-fx-background-color: #ff7c7c;");
				classificacao.setStyle("");
				validado[camposTexto.indexOf(item)] = false;
			}
			else if (!item.getText().equals("") && classificacao.getSelectionModel().getSelectedItem() != null){
				item.setStyle("");
				classificacao.setStyle("");
				validado[camposTexto.indexOf(item)] = true;
			}
		}
		
		for (int i = 0; i < validado.length; i++){
			if (validado[i] == false){
				alerta.notificacaoAlerta("Cadastrar livro", "Verifique os campos obrigatórios e tente novamente.");
				camposTexto.get(i).requestFocus();
				return false;
			}
		}
		
		return true;
	}
}
