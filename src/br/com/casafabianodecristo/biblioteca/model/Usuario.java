package br.com.casafabianodecristo.biblioteca.model;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.persistence.*;

import br.com.casafabianodecristo.biblioteca.utils.ConvertToMD5;

@Entity
@Table(schema="BibliotecaFabiano2", name="Usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	@Column(name="Nome", nullable=false, length=50)
	private String nomeUsuario;
	
	@Column(name = "Sobrenome", nullable = false, length=50)
	private String sobrenome;
	
	@Column(name="NomeUsuarioAcesso", length=10)
	private String nomeUsuarioAcessoSistema;
	
	@Column(name="Senha")
	private String senha;
	
	@Column(name="DDD", nullable=false)
	private int ddd;
	
	@Column(name="Telefone", nullable=false)
	private int telefone;
	
	@Column(name="FlAdministrador", nullable=false)
	private int flAdministrador;
	
	@Column(name="DicaSenha", length= 100)
	private String dicaSenha;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="usuario")
	@JoinColumn(name="Id")
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	
	@Column(name="FlInativo", nullable=false)
	private int flInativo;
	
	@Column(name="FlPossuiAtraso", nullable=false)
	private int flPossuiAtraso;
	
	public Usuario(){}

	public Usuario(String nomeUsuario, String sobrenome, String nomeUsuarioAcessoSistema, String senha, int ddd,
			int telefone, int flAdministrador, String dicaSenha, int flInativo) {
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.nomeUsuarioAcessoSistema = nomeUsuarioAcessoSistema;
		this.senha = senha;
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = flAdministrador;
		this.dicaSenha = dicaSenha;
		this.flInativo = flInativo;
		this.flPossuiAtraso = 0;
	}
	
	public Usuario(int id, String nomeUsuario, String sobrenome, int ddd, int telefone, int flAdministrador,
			int flInativo) {
		super();
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = flAdministrador;
		this.flInativo = flInativo;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ddd;
		result = prime * result + ((dicaSenha == null) ? 0 : dicaSenha.hashCode());
		result = prime * result + flAdministrador;
		result = prime * result + flInativo;
		result = prime * result + id;
		result = prime * result + ((nomeUsuario == null) ? 0 : nomeUsuario.hashCode());
		result = prime * result + ((nomeUsuarioAcessoSistema == null) ? 0 : nomeUsuarioAcessoSistema.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
		result = prime * result + telefone;
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
		Usuario other = (Usuario) obj;
		if (ddd != other.ddd)
			return false;
		if (dicaSenha == null) {
			if (other.dicaSenha != null)
				return false;
		} else if (!dicaSenha.equals(other.dicaSenha))
			return false;
		if (emprestimos == null) {
			if (other.emprestimos != null)
				return false;
		} else if (!emprestimos.equals(other.emprestimos))
			return false;
		if (flAdministrador != other.flAdministrador)
			return false;
		if (flInativo != other.flInativo)
			return false;
		if (id != other.id)
			return false;
		if (nomeUsuario == null) {
			if (other.nomeUsuario != null)
				return false;
		} else if (!nomeUsuario.equals(other.nomeUsuario))
			return false;
		if (nomeUsuarioAcessoSistema == null) {
			if (other.nomeUsuarioAcessoSistema != null)
				return false;
		} else if (!nomeUsuarioAcessoSistema.equals(other.nomeUsuarioAcessoSistema))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		if (telefone != other.telefone)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nomeUsuario=" + nomeUsuario + ", sobrenome=" + sobrenome
				+ ", nomeUsuarioAcessoSistema=" + nomeUsuarioAcessoSistema + ", senha=" + senha + ", ddd=" + ddd
				+ ", telefone=" + telefone + ", flAdministrador=" + flAdministrador + ", dicaSenha=" + dicaSenha
				+ ", emprestimos=" + emprestimos + ", flInativo=" + flInativo + "]";
	}

	public int getFlInativo() {
		return flInativo;
	}

	public void setFlInativo(int flInativo) {
		this.flInativo = flInativo;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public String getIdUsuarioString(){
		return this.id + "";
	}

	public int getIdUsuario(){
		return this.id;
	}
	
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getNomeUsuarioAcessoSistema() {
		return nomeUsuarioAcessoSistema;
	}

	public void setNomeUsuarioAcessoSistema(String nomeUsuarioAcessoSistema) {
		this.nomeUsuarioAcessoSistema = nomeUsuarioAcessoSistema;
	}

	public String getDddString(){
		return this.ddd + "";
	}
	
	public int getDdd() {
		return ddd;
	}

	public void setDdd(int ddd) {
		this.ddd = ddd;
	}

	public String getTelefoneString(){
		return telefone + "";
	}
	
	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	public int getFlAdministrador() {
		return flAdministrador;
	}

	public String getFlAdministradorString(){
		if (flAdministrador == 1)
			return "Sim";
		else
			return "N�o";
	}
	
	public void setFlAdministrador(int flAdministrador) {
		this.flAdministrador = flAdministrador;
	}
	
	public String getDicaSenha() {
		return dicaSenha;
	}

	public void setDicaSenha(String dicaSenha) {
		this.dicaSenha = dicaSenha;
	}

	public String getSenha(){
		return this.senha;
	}
	
	public void setSenhaCriptografada(String senha) throws NoSuchAlgorithmException{
		this.senha = ConvertToMD5.convertPasswordToMD5(senha);
	}

	public int getFlPossuiAtraso() {
		return flPossuiAtraso;
	}

	public void setFlPossuiAtraso(int flPossuiAtraso) {
		this.flPossuiAtraso = flPossuiAtraso;
	}
	
	
}