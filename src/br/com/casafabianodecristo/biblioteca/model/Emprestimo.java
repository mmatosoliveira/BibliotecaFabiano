package br.com.casafabianodecristo.biblioteca.model;

import javax.persistence.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(schema="BibliotecaFabiano2", name="emprestimo")
public class Emprestimo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="DataEmprestimo", nullable=false)
	private Date dataEmprestimo;
	
	@Column(name="DataDevolucaoPrevista", nullable=false)
	private Date dataDevolucaoPrevista;
	
	@Column(name="DataDevolucaoEfetiva", nullable=true)
	private Date dataDevolucaoEfetiva;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="TomboPatrimonial", referencedColumnName="TomboPatrimonial")
	private Livro livro;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="IdUsuario", referencedColumnName="Id")
	private Usuario usuario;

	public Emprestimo(int id, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoEfetiva, Livro livro,
			Usuario usuario) {
		super();
		this.id = id;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
		this.livro = livro;
		this.usuario = usuario;
	}

	public Emprestimo(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}
	
	public String getDataEmprestimoString(){
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String str = fmt.format(dataEmprestimo);
		return str;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}

	public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}

	public Date getDataDevolucaoEfetiva() {
		return dataDevolucaoEfetiva;
	}

	public void setDataDevolucaoEfetiva(Date dataDevolucaoEfetiva) {
		this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDevolucaoEfetiva == null) ? 0 : dataDevolucaoEfetiva.hashCode());
		result = prime * result + ((dataDevolucaoPrevista == null) ? 0 : dataDevolucaoPrevista.hashCode());
		result = prime * result + ((dataEmprestimo == null) ? 0 : dataEmprestimo.hashCode());
		result = prime * result + id;
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
		Emprestimo other = (Emprestimo) obj;
		if (dataDevolucaoEfetiva == null) {
			if (other.dataDevolucaoEfetiva != null)
				return false;
		} else if (!dataDevolucaoEfetiva.equals(other.dataDevolucaoEfetiva))
			return false;
		if (dataDevolucaoPrevista == null) {
			if (other.dataDevolucaoPrevista != null)
				return false;
		} else if (!dataDevolucaoPrevista.equals(other.dataDevolucaoPrevista))
			return false;
		if (dataEmprestimo == null) {
			if (other.dataEmprestimo != null)
				return false;
		} else if (!dataEmprestimo.equals(other.dataEmprestimo))
			return false;
		if (id != other.id)
			return false;
		if (livro == null) {
			if (other.livro != null)
				return false;
		} else if (!livro.equals(other.livro))
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
		return "Emprestimo [id=" + id + ", dataEmprestimo=" + dataEmprestimo + ", dataDevolucaoPrevista="
				+ dataDevolucaoPrevista + ", dataDevolucaoEfetiva=" + dataDevolucaoEfetiva + ", livro=" + livro
				+ ", usuario=" + usuario + "]";
	}	
}