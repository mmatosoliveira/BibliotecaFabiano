package br.com.casafabianodecristo.biblioteca.dto;

public class ClassificacaoDto {
	private int id;
	
	private String descricao;
	
	private String cor;

	public ClassificacaoDto(int id, String descricao, String cor) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.cor = cor;
	}
	
	public ClassificacaoDto(){}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}