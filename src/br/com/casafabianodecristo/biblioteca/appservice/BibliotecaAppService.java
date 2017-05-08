package br.com.casafabianodecristo.biblioteca.appservice;

import java.util.*;
import org.modelmapper.ModelMapper;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.service.*;
import br.com.casafabianodecristo.biblioteca.validator.ClassificacaoValidator;

public class BibliotecaAppService {
	private LivroService livroService = new LivroService();
	private ClassificacaoService classService = new ClassificacaoService();
	private UsuarioService usuarioService = new UsuarioService();
	private EmprestimoService empService = new EmprestimoService();
	private RelatorioService relatorioService = new RelatorioService();
	private ApplicationService appService = new ApplicationService();
	
	public BibliotecaAppService() {	}
	
	/**
	 * RELATÓRIO
	 * **/
	public List<RelatorioDto> getModelosRelatorios(){
		return relatorioService.getModelosRelatorios();
	}
	
	/**
	 * SISTEMA
	 */
	public int salvarParametrizacao(String nomeImpressora){
		return appService.salvarParametrizacao(nomeImpressora);
	}
	
	public ParametrizacaoSistemaDto getParametrizacaoSistemaVigente() throws Exception{
		return appService.getParametrizacaoSistemaVigente();
	}
	
	/**
	 * LIVRO 
	 **/
	
	public int getUltimoTombo(){
		return livroService.getUltimoTombo();
	}
	
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
	
	public int removerClassificacao(int id) throws MySQLIntegrityConstraintViolationException{
		return classService.removerClassificacao(id);
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
	
	public List<UsuarioDto> getUsuariosParaEmprestimo(String nomeUsuario){
		return usuarioService.getUsuariosParaEmprestimo(nomeUsuario);
	}
	
	public void atualizarUsuario(UsuarioDto dto){
		try {
			usuarioService.atualizarUsuario(dto);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public boolean cadastrarUsuario(UsuarioDto dto){
		return usuarioService.cadastrarUsuario(dto);
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
	
	public boolean renovarEmprestimo(int id){
		return empService.renovarEmprestimo(id);
	}
	
	public List<Emprestimo> getDevolucoesPrevistas(){
		return empService.getDevolucoesPrevistas();
	}
	
	public boolean devolverLivro(EmprestimoDto dto){
		return empService.devolverLivro(dto);
	}
	
	public List<EmprestimoDto> getEmprestimosPorNome(String nomeUsuario, boolean apenasAtrasados){
		return empService.getEmprestimosPorUsuario(nomeUsuario, apenasAtrasados);
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
		UsuarioDto usuarioLogado = null;
		Usuario admin = null;			
		admin = usuarioService.getUsuarioPorNomeUsuario("Admin");
		if (admin == null){
			UsuarioDto adminDto = new UsuarioDto(0, "Administrador", "", "Admin", "Admin", 00, 000000000, true, "Administrador", 0);
			usuarioService.cadastrarUsuario(adminDto);
		}
		
		usuarioLogado = usuarioService.logar(nomeUsuario, senha);
		
		if (usuarioLogado != null){
			//ModelMapper mapper = new ModelMapper();
			
			//UsuarioDto uDto = mapper.map(usuarioLogado, UsuarioDto.class);
			usuarioLogado.setSenha(usuarioLogado.getSenha());

			List<Emprestimo> es = this.getDevolucoesPrevistas();
			usuarioLogado.setSenhaAntesEdicao(usuarioLogado.getSenha());
			InicialDto dto = new InicialDto(usuarioLogado, es);
			return dto;
		}
		else return null;
	}		
}