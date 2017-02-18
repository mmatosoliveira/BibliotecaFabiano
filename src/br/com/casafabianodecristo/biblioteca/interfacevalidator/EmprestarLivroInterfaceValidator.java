package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import java.util.List;

import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;

public class EmprestarLivroInterfaceValidator {
	private static Alertas alerta = new Alertas();
	
	public static boolean validarUsuario(UsuarioDto usuario){
		if (usuario != null)
			return true;
		else {
			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar um usuário para realizar um empréstimo.");
			return false;
		}
	}
	
	public static boolean validarLivrosSelecionados(List<LivroDto> livros){
		if(livros.isEmpty()){
			alerta.notificacaoAlerta("Emprestar Livro", "É obrigatório selecionar pelo menos um livro para realizar um empréstimo.");
			return false;
		}
		else if(livros.size() > 3){
			alerta.notificacaoAlerta("Emprestar Livro", "Não é permitido selecionar mais que 3 (três) livros para um empréstimo.");
			return false;
		}
		else 
			return true;
	}
}
