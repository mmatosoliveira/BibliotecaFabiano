package br.com.casafabianodecristo.biblioteca.dto;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmprestimoDto {
	
	private int id;
	
	private Date dataEmprestimo;
	
	private Date dataDevolucaoPrevista;
	
	private Date dataDevolucaoEfetiva;
	
	private LivroDto livroDto;
	
	private UsuarioDto usuarioDto;

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
}