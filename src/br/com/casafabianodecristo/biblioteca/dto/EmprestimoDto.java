package br.com.casafabianodecristo.biblioteca.dto;

import java.text.SimpleDateFormat;
import java.util.*;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.model.Usuario;

public class EmprestimoDto {
	
	private int id;
	
	private Date dataEmprestimo;
	
	private Date dataDevolucaoPrevista;
	
	private Date dataDevolucaoEfetiva;
	
	private LivroDto livroDto;
	
	private UsuarioDto usuarioDto;
	
	ModelMapper mapper = new ModelMapper();

	public EmprestimoDto(int id, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoEfetiva, LivroDto livroDto,
			UsuarioDto usuarioDto) {
		super();
		this.id = id;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
		this.livroDto = livroDto;
		this.usuarioDto = usuarioDto;
	}
	
	public EmprestimoDto(int id, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoEfetiva, Livro livro,
			Usuario usuario) {
		this.id = id;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
		this.livroDto = mapper.map(livro, LivroDto.class);
		this.usuarioDto = mapper.map(usuario, UsuarioDto.class);
	}

	public EmprestimoDto(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}
	
	public String getDataEmprestimoString(){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String str = fmt.format(dataEmprestimo);
		return str;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public Date getDataDevolucaoEfetiva() {
		return dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(Date dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	}

	public LivroDto getLivro() {
		return livroDto;
	}

	public void setLivro(LivroDto livroDto) {
		this.livroDto = livroDto;
	}

	public UsuarioDto getUsuario() {
		return usuarioDto;
	}

	public void setUsuario(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}
	
	public String getAtrasado(){
		if (dataDevolucaoPrevista.compareTo(new Date()) < 0)
			return "Sim";
		else
			return "Não";
	}
	
	public String getDevolucaoPrevista(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(dataDevolucaoPrevista);
	}
	
	public String getDevolucaoEfetiva(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return (dataDevolucaoEfetiva == null) ? "" : sdf.format(dataDevolucaoEfetiva);
	}
}