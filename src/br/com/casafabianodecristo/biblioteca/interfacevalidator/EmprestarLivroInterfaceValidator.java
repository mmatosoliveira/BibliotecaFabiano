package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;

public class EmprestarLivroInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarRegistrosSelecionados(UsuarioDto usuario, LivroDto livro){
		if (usuario != null && livro != null)
			return true;
		else if (usuario != null && livro == null) {
			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar um livro para realizar um empréstimo.");
			return false;
		}
		else if (usuario == null && livro != null) {
			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar um usuário para realizar um empréstimo.");
			return false;
		}
		else {
			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar um usuário e um livro para\nrealizar um empréstimo.");
			return false;
		}			
	}
}
