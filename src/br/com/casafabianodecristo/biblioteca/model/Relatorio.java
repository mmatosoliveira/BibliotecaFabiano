package br.com.casafabianodecristo.biblioteca.model;

import javax.persistence.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="Relatorio")
public class Relatorio {
	@Id
	@Column(name="Id", nullable=false)
	private int id;
	
	@Column(name="Nome", nullable=false, length=120)
	private String nome;
	
	@Column(name="Descricao", nullable=false, length=500)
	private String descricao;
	
	@Column(name="FlObrigaDatas", nullable=false)
	private int flObrigaDatas;
	
	@Column(name="FlObrigaUsuario", nullable=false)
	private int flObrigaUsuario;
	
	@Column(name="FlObrigaClassificacao", nullable=false)
	private int flObrigaClassificacao;

	public Relatorio(){}
	
	public Relatorio(int id, String nome, String descricao, int flObrigaDatas, int flObrigaUsuario, int flObrigaClassificacao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.flObrigaDatas = flObrigaDatas;
		this.flObrigaUsuario = flObrigaUsuario;
		this.flObrigaClassificacao = flObrigaClassificacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + flObrigaClassificacao;
		result = prime * result + flObrigaDatas;
		result = prime * result + flObrigaUsuario;
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relatorio other = (Relatorio) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (flObrigaClassificacao != other.flObrigaClassificacao)
			return false;
		if (flObrigaDatas != other.flObrigaDatas)
			return false;
		if (flObrigaUsuario != other.flObrigaUsuario)
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	public int getFlObrigaClassificacao() {
		return flObrigaClassificacao;
	}

	public void setFlObrigaClassificacao(int flObrigaClassificacao) {
		this.flObrigaClassificacao = flObrigaClassificacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getFlObrigaDatas() {
		return flObrigaDatas;
	}

	public void setFlObrigaDatas(int flObrigaDatas) {
		this.flObrigaDatas = flObrigaDatas;
	}

	public int getFlObrigaUsuario() {
		return flObrigaUsuario;
	}

	public void setFlObrigaUsuario(int flObrigaUsuario) {
		this.flObrigaUsuario = flObrigaUsuario;
	}
}