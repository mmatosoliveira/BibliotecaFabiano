package br.com.casafabianodecristo.biblioteca.appservice;

import java.util.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.service.*;

public class BibliotecaAppService {
	private LivroService livroService = new LivroService();
	private ClassificacaoService classService = new ClassificacaoService();
	private UsuarioService usuarioService = new UsuarioService();
	private EmprestimoService empService = new EmprestimoService();

	public BibliotecaAppService() {	}
	
	/**
	 * LIVRO 
	 **/
	
	public Livro getLivroPorTombo(int tombo){
		return livroService.getLivroPorTombo(tombo);
	}
	
	public void atualizarLivro(LivroDto dto){
		livroService.atualizarLivro(dto);
	}
	
	public void cadastrarLivro(LivroDto dto){
		livroService.cadastrarLivro(dto);
	}
	
	public void removerLivroAcervo(int tomboPatrimonial){
		livroService.removerLivroAcervo(tomboPatrimonial);
	}
	
	/**
	 * CLASSIFICAÇÃO
	 **/
	
	public Classificacao getClassificacaoById(int id){
		return classService.getClassificacaoById(id);
	}
	
	public void cadastrarClassificacao(ClassificacaoDto dto){
		classService.cadastrarClassificacao(dto);
	}
	
	public void atualizarClassificacao(ClassificacaoDto dto){
		classService.atualizarClassificacao(dto);
	}
	
	/**
	 * USUÁRIO
	 **/
	
	public Usuario getUsuarioById(int id){
		return usuarioService.getUsuarioById(id);
	}
	
	public List<UsuarioDto> getUsuarios(String nomeUsuario){
		return usuarioService.getUsuarios(nomeUsuario);
	}
	
	public void atualizarUsuario(UsuarioDto dto){
		usuarioService.atualizarUsuario(dto);
	}
	
	public void cadastrarUsuario(UsuarioDto dto){
		usuarioService.cadastrarUsuario(dto);
	}	
	
	public void inativarUsuario(int id){
		usuarioService.inativarUsuario(id);
	}
	
	/**
	 * EMPRÉSTIMO
	 **/
	
	public void realizarEmprestimo(EmprestimoDto dto){
		empService.realizarEmprestimo(dto);
	}
}
