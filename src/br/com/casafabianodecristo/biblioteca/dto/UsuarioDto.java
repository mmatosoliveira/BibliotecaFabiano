package br.com.casafabianodecristo.biblioteca.dto;

import java.util.*;

public class UsuarioDto {
	
	private int id;	
	
	private String nomeUsuario;	
	
	private String sobrenome;	
	
	private String nomeUsuarioAcessoSistema;
	
	private String senha;	
	
	private int ddd;	
	
	private int telefone;	
	
	private int flAdministrador;	
	
	private String dicaSenha;

	@SuppressWarnings("unused")
	private List<EmprestimoDto> emprestimoDtos = new ArrayList<EmprestimoDto>();
	
	@SuppressWarnings("unused")
	private List<MensagemDto> mensagensEnviadas = new ArrayList<MensagemDto>();
	
	@SuppressWarnings("unused")
	private List<MensagemDto> mensagensRecebidas = new ArrayList<MensagemDto>();
	
	private int flInativo;
	
	public UsuarioDto(){}

	public UsuarioDto(int id, String nomeUsuario, String sobrenome, String nomeUsuarioAcessoSistema, String senha, int ddd,
			int telefone, int flAdministrador, String dicaSenha, int flInativo) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.nomeUsuarioAcessoSistema = nomeUsuarioAcessoSistema;
		this.senha = senha;
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = flAdministrador;
		this.dicaSenha = dicaSenha;
		this.flInativo = flInativo;
	}
		@Override
	public String toString() {
		return "Usuario [idUsuario=" + id + ", nomeUsuario=" + nomeUsuario + ", sobrenome=" + sobrenome
				+ ", nomeUsuarioAcessoSistema=" + nomeUsuarioAcessoSistema + ", senha=" + senha + ", ddd=" + ddd
				+ ", telefone=" + telefone + ", flAdministrador=" + flAdministrador + ", dicaSenha=" + dicaSenha + "]";
	}
		
	public int getId() {
			return id;
		}
	
		public int getFlInativo() {
		return flInativo;
	}

	public void setFlInativo(int flInativo) {
		this.flInativo = flInativo;
	}

		public void setId(int id) {
			this.id = id;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

	public String getIdString(){
		return this.id + "";
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
			return "Não";
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
	
	public String getFlInativoString(){
		return (this.flInativo == 1) ? "Inativo" : "Ativo"; 
	}
}