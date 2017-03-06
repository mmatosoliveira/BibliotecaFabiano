package br.com.casafabianodecristo.biblioteca.dto;

public class ReciboEmprestimoDto {
	private String nomeUsuario;
	
	private String primeiroLivro;
	
	private String segundoLivro;
	
	private String terceiroLivro;

	private String dataDevolucao;
	
	public ReciboEmprestimoDto(String nomeUsuario, String dataDevolucao, String primeiroLivro, String segundoLivro, String terceiroLivro) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.dataDevolucao = dataDevolucao;
		this.primeiroLivro = primeiroLivro;
		this.segundoLivro = segundoLivro;
		this.terceiroLivro = terceiroLivro;
	}
	
	public ReciboEmprestimoDto(){}


	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getPrimeiroLivro() {
		return primeiroLivro;
	}

	public void setPrimeiroLivro(String primeiroLivro) {
		this.primeiroLivro = primeiroLivro;
	}

	public String getSegundoLivro() {
		return segundoLivro;
	}

	public void setSegundoLivro(String segundoLivro) {
		this.segundoLivro = segundoLivro;
	}

	public String getTerceiroLivro() {
		return terceiroLivro;
	}

	public void setTerceiroLivro(String terceiroLivro) {
		this.terceiroLivro = terceiroLivro;
	}
}