package br.com.casafabianodecristo.biblioteca.model;

import javax.persistence.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="Lembrete")
public class Lembrete {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;
	
	@Column(name="TextoLembrete", length=200)
	private String lembrete;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="IdUsuario")
	private Usuario usuario;

	public Lembrete(String lembrete, Usuario u) {
		this.lembrete = lembrete;
		this.usuario = u;
	}
	
	public Lembrete(){}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getLembrete() {
		return lembrete;
	}

	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
		result = prime * result + ((lembrete == null) ? 0 : lembrete.hashCode());
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
		Lembrete other = (Lembrete) obj;
		if (Id != other.Id)
			return false;
		if (lembrete == null) {
			if (other.lembrete != null)
				return false;
		} else if (!lembrete.equals(other.lembrete))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lembrete [Id=" + Id + ", lembrete=" + lembrete + "]";
	}
}