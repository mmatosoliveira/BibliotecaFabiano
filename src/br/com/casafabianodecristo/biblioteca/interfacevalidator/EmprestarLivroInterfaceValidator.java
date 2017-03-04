package br.com.casafabianodecristo.biblioteca.interfacevalidator;

import java.util.List;
import br.com.casafabianodecristo.biblioteca.dto.*;

public class EmprestarLivroInterfaceValidator {
	public static int validarUsuario(UsuarioDto usuario){
		System.out.println(usuario);
		if (usuario != null)
			return 0;
		else {
			
			return 1;
		}
	}
	
	public static int validarLivrosSelecionados(List<LivroDto> livros){
		System.out.println(livros);
		if(livros.isEmpty()){
			return 1;
		}
		else if(livros.size() > 3){
			return 2;
		}
		else return 0;
	}
}
