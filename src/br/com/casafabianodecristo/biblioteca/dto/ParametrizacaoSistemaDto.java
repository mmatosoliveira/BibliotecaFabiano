package br.com.casafabianodecristo.biblioteca.dto;

public class ParametrizacaoSistemaDto {
	private int id;
	
	private String nomeImpressoraPadrao;

	public ParametrizacaoSistemaDto(int id, String nomeImpressoraPadrao) {
		this.id = id;
		this.nomeImpressoraPadrao = nomeImpressoraPadrao;
	}

	public ParametrizacaoSistemaDto() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeImpressoraPadrao() {
		return nomeImpressoraPadrao;
	}

	public void setNomeImpressoraPadrao(String nomeImpressoraPadrao) {
		this.nomeImpressoraPadrao = nomeImpressoraPadrao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nomeImpressoraPadrao == null) ? 0 : nomeImpressoraPadrao.hashCode());
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
		ParametrizacaoSistemaDto other = (ParametrizacaoSistemaDto) obj;
		if (id != other.id)
			return false;
		if (nomeImpressoraPadrao == null) {
			if (other.nomeImpressoraPadrao != null)
				return false;
		} else if (!nomeImpressoraPadrao.equals(other.nomeImpressoraPadrao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ParametrizacaoSistemaDto [id=" + id + ", nomeImpressoraPadrao=" + nomeImpressoraPadrao + "]";
	}
}