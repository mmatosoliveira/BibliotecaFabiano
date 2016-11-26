package br.com.casafabianodecristo.biblioteca.model;

import javax.persistence.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="Mensagem")
public class Mensagem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;

	@Column(name="TituloMensagem", nullable=false, length=100)
	private String tituloMensagem;
	
	@Column(name="TextoMensagem", nullable=false, length=500)
	private String textoMensagem;
	
	@Column(name="FlLida", nullable=false)
	private int flLida;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdDestinatario", referencedColumnName="Id")
	private Usuario destinatario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="IdRemetente", referencedColumnName="Id")
	private Usuario remetente;

	public Mensagem(int id, String tituloMensagem, String textoMensagem, int flLida, Usuario destinatario,
			Usuario remetente) {
		super();
		this.id = id;
		this.tituloMensagem = tituloMensagem;
		this.textoMensagem = textoMensagem;
		this.flLida = flLida;
		this.destinatario = destinatario;
		this.remetente = remetente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTituloMensagem() {
		return tituloMensagem;
	}

	public void setTituloMensagem(String tituloMensagem) {
		this.tituloMensagem = tituloMensagem;
	}

	public String getTextoMensagem() {
		return textoMensagem;
	}

	public void setTextoMensagem(String textoMensagem) {
		this.textoMensagem = textoMensagem;
	}

	public int getFlLida() {
		return flLida;
	}

	public void setFlLida(int flLida) {
		this.flLida = flLida;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + flLida;
		result = prime * result + id;
		result = prime * result + ((remetente == null) ? 0 : remetente.hashCode());
		result = prime * result + ((textoMensagem == null) ? 0 : textoMensagem.hashCode());
		result = prime * result + ((tituloMensagem == null) ? 0 : tituloMensagem.hashCode());
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
		Mensagem other = (Mensagem) obj;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (flLida != other.flLida)
			return false;
		if (id != other.id)
			return false;
		if (remetente == null) {
			if (other.remetente != null)
				return false;
		} else if (!remetente.equals(other.remetente))
			return false;
		if (textoMensagem == null) {
			if (other.textoMensagem != null)
				return false;
		} else if (!textoMensagem.equals(other.textoMensagem))
			return false;
		if (tituloMensagem == null) {
			if (other.tituloMensagem != null)
				return false;
		} else if (!tituloMensagem.equals(other.tituloMensagem))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mensagem [id=" + id + ", tituloMensagem=" + tituloMensagem + ", textoMensagem=" + textoMensagem
				+ ", flLida=" + flLida + ", destinatario=" + destinatario + ", remetente=" + remetente + "]";
	}

}