package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import java.util.List;

import br.com.casafabianodecristo.biblioteca.components.Numberfield;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.scene.control.TextField;

public class CadastroUsuarioInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarTamanhosObrigatorios(boolean adm, TextField nome, TextField sobrenome, Numberfield ddd, Numberfield telefone, TextField senha, TextField confirmacao, TextField dicaSenha, String operacao){
		if(nome.getText().length() > 50 || nome.getText().length() < 1)
			return setarFoco(nome, "nome", 50, 1, operacao);
		
		if(sobrenome.getText().length() > 50 || sobrenome.getText().length() < 1)
			return setarFoco(sobrenome, "sobrenome", 50, 1, operacao);
		
		if(ddd.getText().length() > 3 || ddd.getText().length() < 2)
			return setarFoco(ddd, "DDD",3, 2, operacao);
		
		if(telefone.getText().length() > 9 || telefone.getText().length() < 8)
			return setarFoco(telefone, "telefone", 9, 8, operacao);
		
		if(adm){
			if(senha.getText().length() > 9 || senha.getText().length() < 4)
				return setarFoco(senha, "senha", 8, 4, operacao);
			
			if(confirmacao.getText().length() > 8 || confirmacao.getText().length() < 4)
				return setarFoco(confirmacao, "confirmação de senha", 8, 4, operacao);
			
			if(dicaSenha.getText().length() > 100 || dicaSenha.getText().length() < 10)
				return setarFoco(dicaSenha, "dica de senha", 100, 10, operacao);
		}
		return true;
	}
	
	public static boolean validarSenha(TextField senha, TextField confirmacao, TextField dica, String operacao){
		String txtSenha = senha.getText();
		String txtConfirmacao = confirmacao.getText();
		if(senha.getText().length() > 9 || senha.getText().length() < 4)
			return setarFoco(senha, "senha", 8, 4, operacao);
		if(confirmacao.getText().length() > 8 || confirmacao.getText().length() < 4)
			return setarFoco(confirmacao, "confirmação de senha", 8, 4, operacao);
		else if(!txtSenha.equals(txtConfirmacao)){
			senha.requestFocus();
			alerta.notificacaoErro(operacao, "A senha e a confirmação da senha não são iguais.");
			return false;
		}
		else if(senha.getText().equalsIgnoreCase(dica.getText())){
			dica.requestFocus();
			alerta.notificacaoErro(operacao, "A senha e a dica de senha não podem ser iguais.");
			return false;
		}
		return true;
	}
	
	private static boolean setarFoco(TextField campo, String nomeCampo, int tamanhoMaximoCampo, int tamanhoMinimo, String operacao){
		campo.requestFocus();
		alerta.notificacaoErro(operacao, "O tamanho máximo para o campo " + nomeCampo + " é de "+ tamanhoMaximoCampo + " caracteres e o tamanho mínimo é de " + tamanhoMinimo + " caracteres.");
		return false;
	}
	
	public static boolean validarCamposObrigatorios (boolean adm, List<TextField> campos){
		boolean[] validado;
		if(!adm){
			validado  = new boolean [4];
			for(int i = 0; i < 4; i++){
				TextField item = campos.get(i);
				if (item.getText().equals("")){
					item.setStyle("-fx-background-color: #ff7c7c;");
					validado[campos.indexOf(item)] = false;
				}
				else if (!item.getText().equals("")){
					item.setStyle("");
					validado[campos.indexOf(item)] = true;
				}
			}
		}
		else{
			validado = new boolean [8];
			for(int i = 0; i < 8; i++){
				TextField item = campos.get(i);
				if (item.getText().equals("")){
					item.setStyle("-fx-background-color: #ff7c7c;");
					validado[campos.indexOf(item)] = false;
				}
				else if (!item.getText().equals("")){
					item.setStyle("");
					validado[campos.indexOf(item)] = true;
				}
			}
		}
		
		for (int i = 0; i < validado.length; i++){
			if (validado[i] == false){
				alerta.notificacaoAlerta("Cadastrar usuário", "Verifique os campos obrigatórios e tente novamente.");
				campos.get(i).requestFocus();
				return false;
			}
		}
		return true;
	}
}