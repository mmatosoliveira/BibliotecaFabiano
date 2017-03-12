package br.com.casafabianodecristo.biblioteca.model;

import javax.persistence.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="ParametrizacaoSistema")
public class ParametrizacaoSistema {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id", nullable = false)
	private int id;

	@Column(name="NomeImpressoraPadraoRecibos", nullable = false)
	private String nomeImpressoraPadraoRecibos;

	public ParametrizacaoSistema(String nomeImpressoraPadraoRecibos) {
		this.nomeImpressoraPadraoRecibos = nomeImpressoraPadraoRecibos;
	}

	public ParametrizacaoSistema() {}

	@Override
	public String toString() {
		return "ParametrizacaoSistema [id=" + id + ", nomeImpressoraPadraoRecibos=" + nomeImpressoraPadraoRecibos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nomeImpressoraPadraoRecibos == null) ? 0 : nomeImpressoraPadraoRecibos.hashCode());
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
		ParametrizacaoSistema other = (ParametrizacaoSistema) obj;
		if (id != other.id)
			return false;
		if (nomeImpressoraPadraoRecibos == null) {
			if (other.nomeImpressoraPadraoRecibos != null)
				return false;
		} else if (!nomeImpressoraPadraoRecibos.equals(other.nomeImpressoraPadraoRecibos))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeImpressoraPadraoRecibos() {
		return nomeImpressoraPadraoRecibos;
	}

	public void setNomeImpressoraPadraoRecibos(String nomeImpressoraPadraoRecibos) {
		this.nomeImpressoraPadraoRecibos = nomeImpressoraPadraoRecibos;
	}
}