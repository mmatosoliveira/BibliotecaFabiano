package br.com.casafabianodecristo.biblioteca.principal;

import java.util.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.service.BibliotecaAppService;

public class Principal {
	BibliotecaAppService service = new BibliotecaAppService();
	
	@SuppressWarnings("unused")
	public Principal(){
		UsuarioDto usuarioDto = new UsuarioDto(1, "Matheus", "Oliveira", null, null, 43, 998515183, 0, null, 0);
		ClassificacaoDto classificacaoDto = new ClassificacaoDto(1, "Livros mediúnicos", "#000001");		
		LivroDto livroDto = new LivroDto(000001, "O livro dos espíritos", null, "Allan Kardec", "FEB", 1, classificacaoDto, 0, 0, 0);
		EmprestimoDto emprestimoDto = new EmprestimoDto(0, new Date(), new Date(), null, livroDto, usuarioDto);

		/**
		 *  CLASSIFICAÇÃO 
		 **/

		//service.cadastrarClassificacao(classificacaoDto);
		//service.atualizarClassificacao(classificacaoDto);
		
		
		/**
		 *  LIVRO 
		 **/
		
		//service.cadastrarLivro(livroDto);
		//livroDto.setSobrenome("Matos");
		//service.atualizarLivro(livroDto);
		//service.removerLivroAcervo(000001);

		
		/**
		 *  USUÁRIO 
		 **/
		
		//service.cadastrarUsuario(usuarioDto);	
		//service.usuarioDto.setSobrenome("Matos");
		//service.atualizarUsuario(usuarioDto);
		//service.inativarUsuario(1);

		/**
		 *  EMPRÉSTIMO 
		 **/	
		
		//service.realizarEmprestimo(emprestimoDto);
	}
	
	public static void main(String[] args) {
			new Principal();
	}

}