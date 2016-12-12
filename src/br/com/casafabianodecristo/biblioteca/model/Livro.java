package br.com.casafabianodecristo.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="Livro")
public class Livro {
	@Id
	@Column(name="TomboPatrimonial", nullable=false)
	private int tomboPatrimonial;
	
	@Column(name="Titulo", nullable=false, length=300)
	private String titulo;
	
	@Column(name = "Subtitulo", length=300)
	private String subtitulo;
	
	@Column(name="NomeAutor", nullable=false, length=300)
	private String nomeAutor;
	
	@Column(name="Editora", nullable=false, length=50)
	private String editora;
	
	@Column(name="Edicao", nullable=false)
	private int edicao;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="IdClassificacaoLivro")
	private Classificacao classificacaoLivro;
	
	@Column(name="FlEmprestado", nullable=false)
	private int flEmprestado;
	
	@Column(name="FlDoado", nullable=false)
	private int flDoado;
	
	@Column(name="FlRemovido", nullable=false)
	private int flRemovido;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="livro", orphanRemoval=true)
	@JoinColumn(name="Id")
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	
	public Livro(int tomboPatrimonial, String titulo, String subtitulo, String nomeAutor, String editora, int edicao,
			Classificacao classificacaoLivro, int flEmprestado, int flDoado, int flRemovido) {
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

	public Livro(){}
	
	public void addEmprestimo(Emprestimo emprestimo){
		emprestimos.add(emprestimo);
	}
	
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
	
	public Classificacao getClassificacaoLivro() {
		return classificacaoLivro;
	}

	public void setClassificacaoLivro(Classificacao classificacaoLivro) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificacaoLivro == null) ? 0 : classificacaoLivro.hashCode());
		result = prime * result + edicao;
		result = prime * result + ((editora == null) ? 0 : editora.hashCode());
		result = prime * result + ((emprestimos == null) ? 0 : emprestimos.hashCode());
		result = prime * result + flDoado;
		result = prime * result + flEmprestado;
		result = prime * result + flRemovido;
		result = prime * result + ((nomeAutor == null) ? 0 : nomeAutor.hashCode());
		result = prime * result + ((subtitulo == null) ? 0 : subtitulo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + tomboPatrimonial;
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
		Livro other = (Livro) obj;
		if (classificacaoLivro == null) {
			if (other.classificacaoLivro != null)
				return false;
		} else if (!classificacaoLivro.equals(other.classificacaoLivro))
			return false;
		if (edicao != other.edicao)
			return false;
		if (editora == null) {
			if (other.editora != null)
				return false;
		} else if (!editora.equals(other.editora))
			return false;
		if (emprestimos == null) {
			if (other.emprestimos != null)
				return false;
		} else if (!emprestimos.equals(other.emprestimos))
			return false;
		if (flDoado != other.flDoado)
			return false;
		if (flEmprestado != other.flEmprestado)
			return false;
		if (flRemovido != other.flRemovido)
			return false;
		if (nomeAutor == null) {
			if (other.nomeAutor != null)
				return false;
		} else if (!nomeAutor.equals(other.nomeAutor))
			return false;
		if (subtitulo == null) {
			if (other.subtitulo != null)
				return false;
		} else if (!subtitulo.equals(other.subtitulo))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (tomboPatrimonial != other.tomboPatrimonial)
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "Livro [tomboPatrimonial=" + tomboPatrimonial + ", titulo=" + titulo + ", subtitulo=" + subtitulo
				+ ", nomeAutor=" + nomeAutor + ", editora=" + editora + ", edicao=" + edicao + ", classificacaoLivro="
				+ classificacaoLivro + ", flEmprestado=" + flEmprestado + ", flDoado=" + flDoado + ", flRemovido="
				+ flRemovido + ", emprestimos=" + emprestimos + "]";
	}*/

	@Override
	public String toString() {
		String emprestado = new String();
		String retorno = new String();

		if (flEmprestado == 1)
			 emprestado = "Sim";
		else
			emprestado = "Não";
		if (subtitulo != null)
			retorno = titulo +": " + subtitulo + " - " +
					"Autor: " + nomeAutor + " - " +"Emprestado: " + emprestado;
		else
			retorno = titulo + " - " + "Autor: " + nomeAutor + " - " + "Emprestado: " + emprestado;
		
		return retorno;
	}
	
	
}