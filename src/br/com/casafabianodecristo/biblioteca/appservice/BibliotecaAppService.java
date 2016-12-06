package br.com.casafabianodecristo.biblioteca.appservice;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.service.*;
import br.com.casafabianodecristo.biblioteca.utils.ConvertToMD5;

public class BibliotecaAppService {
	private LivroService livroService = new LivroService();
	private ClassificacaoService classService = new ClassificacaoService();
	private UsuarioService usuarioService = new UsuarioService();
	private EmprestimoService empService = new EmprestimoService();
	private LembreteService lemService = new LembreteService();
	
	public BibliotecaAppService() {	}
	
	/**
	 * LIVRO 
	 **/
	
	public Livro getLivroPorTombo(int tombo){
		return livroService.getLivroPorTombo(tombo);
	}
	
	public Livro getLivroPorTomboPatrimonial(int tombo){
		return livroService.getLivroPorTomboPatrimonial(tombo);
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
	
	public void excluirLivro(LivroDto dto){
		livroService.excluir(dto);
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
	
	public String getDicaSenha(String nomeUsuario){
		return usuarioService.getDicaSenha(nomeUsuario);
	}
	
	/**
	 * EMPRÉSTIMO
	 **/
	
	public void realizarEmprestimo(EmprestimoDto dto){
		empService.realizarEmprestimo(dto);
	}
	
	public List<Emprestimo> getDevolucoesPrevistas(){
		return empService.getDevolucoesPrevistas();
	}
	
	public void devolverLivro(EmprestimoDto dto){
		empService.devolverLivro(dto);
	}

	/**
	 * LEMBRETE
	 **/
	
	public String getLembrete(int idUsuario){
		return lemService.getLembretePorUsuario(idUsuario);
	}
	
	public void cadastrarLembrete(Lembrete l){
		lemService.cadastrarLembrete(l);
	}
	
	public void editarLembrete(Lembrete l){
		lemService.editarLembrete(l);
	}
	
	/**
	 * MENSAGEM
	 **/
	
	
	/**
	 * COMUM
	 **/
	
	
	
	public Usuario logar(String nomeUsuario, String senha){
		Usuario usuarioLogado = null;
		Usuario admin = null;
		String password = new String();
		try {
			password = ConvertToMD5.convertPasswordToMD5("Admin");
		} catch (NoSuchAlgorithmException e) {}
			
		admin = usuarioService.getUsuarioPorNomeUsuario("Admin");
		if (admin == null){
			UsuarioDto adminDto = new UsuarioDto(0, "Administrador", "Administrador", "Admin", password, 00, 000000000, 1, "Administrador", 0);
			usuarioService.cadastrarUsuario(adminDto);
		}
		
		usuarioLogado = usuarioService.logar(nomeUsuario, senha);
		
		return usuarioLogado;
	}	
	
	
}