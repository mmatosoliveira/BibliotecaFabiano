package br.com.casafabianodecristo.biblioteca.appservice;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.service.*;
import br.com.casafabianodecristo.biblioteca.utils.ConvertToMD5;
import br.com.casafabianodecristo.biblioteca.validator.ClassificacaoValidator;

public class BibliotecaAppService {
	private LivroService livroService = new LivroService();
	private ClassificacaoService classService = new ClassificacaoService();
	private UsuarioService usuarioService = new UsuarioService();
	private EmprestimoService empService = new EmprestimoService();
	private RelatorioService relatorioService = new RelatorioService();
	
	public BibliotecaAppService() {	}
	
	/**
	 * RELATÓRIO
	 * **/
	public List<RelatorioDto> getModelosRelatorios(){
		return relatorioService.getModelosRelatorios();
	}
	
	
	/**
	 * LIVRO 
	 **/
	
	public Livro getLivroPorTombo(int tombo){
		return livroService.getLivroPorTombo(tombo);
	}
	
	public List<Livro> pesquisaRapidaLivro(String texto, boolean titulo, boolean autor, boolean tombo, boolean soDisponiveis){
		return livroService.pesquisaRapidaLivro(texto, titulo, autor, tombo, soDisponiveis);
	}
	
	public void atualizarLivro(LivroDto dto){
		livroService.atualizarLivro(dto);
	}
	
	public int cadastrarLivro(LivroDto dto, int qtdExemplares){
		return livroService.cadastrarLivro(dto, qtdExemplares); 
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
	
	public int cadastrarClassificacao(ClassificacaoDto dto){
		return classService.cadastrarClassificacao(dto);
	}
	
	public int atualizarClassificacao(ClassificacaoDto dto){
		if(ClassificacaoValidator.validarValoresRepetidos(dto.getDescricao(), dto.getCor(), true)){
			classService.atualizarClassificacao(dto);
			return 0;
		}
		else return 2;
	}
	
	public List<ClassificacaoDto> getClassificacoes(){
		ModelMapper mapper = new ModelMapper();
		List<ClassificacaoDto> dto = new ArrayList<ClassificacaoDto>();
		
		for(Classificacao c : classService.getClassificacoes()){
			dto.add(mapper.map(c, ClassificacaoDto.class));
		}
		return dto;		
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
	
	public boolean realizarEmprestimo(EmprestimoDto dto){
		return empService.realizarEmprestimo(dto);
	}
	
	public List<Emprestimo> getDevolucoesPrevistas(){
		return empService.getDevolucoesPrevistas();
	}
	
	public void devolverLivro(EmprestimoDto dto){
		empService.devolverLivro(dto);
	}
	
	public List<EmprestimoDto> getEmprestimos(){
		List<Emprestimo> emprestimos = empService.getEmprestimos();
		List<EmprestimoDto> dto = new ArrayList<EmprestimoDto>(); 
		
		if (emprestimos.size() != 0){
			for (Emprestimo e : emprestimos){
				dto.add(new EmprestimoDto(e.getId(), e.getDataEmprestimo(), e.getDataDevolucaoPrevista(), e.getDataDevolucaoEfetiva(), e.getLivro(), e.getUsuario()));
			}
		}
		return dto;
	}
	
	/**
	 * COMUM
	 **/
	
	public InicialDto logar(String nomeUsuario, String senha){
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
		
		if (usuarioLogado != null){
			ModelMapper mapper = new ModelMapper();
			
			UsuarioDto uDto = mapper.map(usuarioLogado, UsuarioDto.class);
			uDto.setSenha(usuarioLogado.getSenha());

			List<Emprestimo> es = this.getDevolucoesPrevistas();
			
			InicialDto dto = new InicialDto(uDto, es);
			return dto;
		}
		else return null;
	}		
}