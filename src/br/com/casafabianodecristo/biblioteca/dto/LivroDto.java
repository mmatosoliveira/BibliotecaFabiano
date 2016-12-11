package br.com.casafabianodecristo.biblioteca.dto;

import java.util.List;

public class LivroDto {
	private int tomboPatrimonial;
	
	private String titulo;
	
	private String subtitulo;
	
	private String nomeAutor;
	
	private String editora;
	
	private int edicao;
	
	private ClassificacaoDto classificacaoLivro;
	
	private int flEmprestado;
	
	private int flDoado;
	
	private int flRemovido;

	@SuppressWarnings("unused")
	private List<EmprestimoDto> emprestimoDtos;
	
	public LivroDto(int tomboPatrimonial, String titulo, String subtitulo, String nomeAutor, String editora, int edicao,
			ClassificacaoDto classificacaoLivro, int flEmprestado, int flDoado, int flRemovido) {
		super();
		this.tomboPatrimonial = tomboPatrimonial;
		this.titulo = titulo;
		this.subtitulo = subtitulo;
		this.nomeAutor = nomeAutor;
		this.editora = editora;
		this.edicao = edicao;
		this.classificacaoLivro = classificacaoLivro;
		this.flEmprestado = flEmprestado;
		this.flDoado = flDoado;
		this.flRemovido = flRemovido;
	}

	public LivroDto(){}
	
	public int getTomboPatrimonial() {
		return tomboPatrimonial;
	}

	public void setTomboPatrimonial(int tomboPatrimonial) {
		this.tomboPatrimonial = tomboPatrimonial;
	}

	public int getFlRemovido() {
		return flRemovido;
	}

	public void setFlRemovido(int flRemovido) {
		this.flRemovido = flRemovido;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}
	
	public ClassificacaoDto getClassificacaoLivro() {
		return classificacaoLivro;
	}

	public void setClassificacaoLivro(ClassificacaoDto classificacaoLivro) {
		this.classificacaoLivro = classificacaoLivro;
	}

	public int getFlEmprestado() {
		return flEmprestado;
	}

	public String getFlEmprestadoString() {
		if (this.flEmprestado == 1)
			return "Sim";
		else 
			return "Não";
	}

	public void setFlEmprestado(int flEmprestado) {
		this.flEmprestado = flEmprestado;
	}

	public int getFlDoado() {
		return flDoado;
	}

	public void setFlDoado(int flDoado) {
		this.flDoado = flDoado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSubtitulo() {
		return subtitulo;
	}

	public void setSubtitulo(String subtitulo) {
		this.subtitulo = subtitulo;
	}

	@Override
	public String toString() {
		return "LivroDto [tomboPatrimonial=" + tomboPatrimonial + ", titulo=" + titulo + ", subtitulo=" + subtitulo
				+ ", nomeAutor=" + nomeAutor + ", editora=" + editora + ", edicao=" + edicao + ", classificacaoLivro="
				+ classificacaoLivro + ", flEmprestado=" + flEmprestado + ", flDoado=" + flDoado + ", flRemovido="
				+ flRemovido + ", emprestimoDtos=" + emprestimoDtos + "]";
	}
	
	
}