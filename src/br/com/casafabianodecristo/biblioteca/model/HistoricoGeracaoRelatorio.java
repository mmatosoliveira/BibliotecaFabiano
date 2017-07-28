package br.com.casafabianodecristo.biblioteca.model;

import java.util.Date;
import javax.persistence.*;

//@Entity
//@Table(schema="BibliotecaFabiano2", name="HistoricoGeracaoRelatorio")
public class HistoricoGeracaoRelatorio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id", nullable = false)
	private int id;

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="IdUsuario", referencedColumnName="Id")
	private Usuario usuario;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="IdRelatorio", referencedColumnName="Id")
	private Relatorio relatorio;
	
	@Column(name="DataHoraGravacao", nullable = false)
	private Date dataHoraGravacao;

	public HistoricoGeracaoRelatorio() {}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataHoraGravacao == null) ? 0 : dataHoraGravacao.hashCode());
		result = prime * result + id;
		result = prime * result + ((relatorio == null) ? 0 : relatorio.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		HistoricoGeracaoRelatorio other = (HistoricoGeracaoRelatorio) obj;
		if (dataHoraGravacao == null) {
			if (other.dataHoraGravacao != null)
				return false;
		} else if (!dataHoraGravacao.equals(other.dataHoraGravacao))
			return false;
		if (id != other.id)
			return false;
		if (relatorio == null) {
			if (other.relatorio != null)
				return false;
		} else if (!relatorio.equals(other.relatorio))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HistoricoGeracaoRelatorio [id=" + id + ", usuario=" + usuario + ", relatorio=" + relatorio
				+ ", dataHoraGravacao=" + dataHoraGravacao + "]";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public Date getDataHoraGravacao() {
		return dataHoraGravacao;
	}

	public void setDataHoraGravacao(Date dataHoraGravacao) {
		this.dataHoraGravacao = dataHoraGravacao;
	}
	
}