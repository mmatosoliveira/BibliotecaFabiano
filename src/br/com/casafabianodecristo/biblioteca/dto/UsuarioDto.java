package br.com.casafabianodecristo.biblioteca.dto;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import br.com.casafabianodecristo.biblioteca.utils.ConvertToMD5;

public class UsuarioDto {
	
	private int id;	
	
	private String nomeUsuario;	
	
	private String sobrenome;	
	
	private String nomeUsuarioAcessoSistema;
	
	private String senha;	
	
	private String senhaAntesEdicao;
	
	private int ddd;	
	
	private int telefone;	
	
	private int flAdministrador;	
	
	private String dicaSenha;
	
	private boolean exibeMsg = true;

	@SuppressWarnings("unused")
	private List<EmprestimoDto> emprestimoDtos = new ArrayList<EmprestimoDto>();
	
	private int flInativo;
	
	private String nomeCompleto;
	
	public UsuarioDto(){}

	public UsuarioDto(int id, String nomeUsuario, String sobrenome, int ddd, int telefone, int flAdministrador) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = flAdministrador;
	}

	public UsuarioDto(int id, String nomeUsuario, String sobrenome, String nomeUsuarioAcessoSistema, String senha, int ddd,
			int telefone, boolean flAdministrador, String dicaSenha, int flInativo) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.nomeUsuarioAcessoSistema = nomeUsuarioAcessoSistema;
		this.senha = (senha == "" || senha == null) ? null : setSenhaCriptografada(senha);
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = (flAdministrador == true) ? 1 : 0;
		this.dicaSenha = dicaSenha;
		this.flInativo = flInativo;
		this.nomeCompleto = nomeUsuario + " " + sobrenome;;
	}
	
	public UsuarioDto(int id, String nomeUsuario, String sobrenome, String nomeUsuarioAcessoSistema, String senha, int ddd,
			int telefone, boolean flAdministrador, String dicaSenha, int flInativo, String nomeCompleto) {
		this.id = id;
		this.nomeUsuario = nomeUsuario;
		this.sobrenome = sobrenome;
		this.nomeUsuarioAcessoSistema = nomeUsuarioAcessoSistema;
		this.senha = (senha == "" || senha == null) ? null : setSenhaCriptografada(senha);
		this.ddd = ddd;
		this.telefone = telefone;
		this.flAdministrador = (flAdministrador == true) ? 1 : 0;
		this.dicaSenha = dicaSenha;
		this.flInativo = flInativo;
		this.nomeCompleto = nomeCompleto;
	}
	
	public String getSenhaAntesEdicao() {
		return senhaAntesEdicao;
	}

	public void setSenhaAntesEdicao(String senhaAntesEdicao) {
		this.senhaAntesEdicao = senhaAntesEdicao;
	}

	public boolean isExibeMsg() {
		return exibeMsg;
	}

	public void setExibeMsg(boolean exibeMsg) {
		this.exibeMsg = exibeMsg;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	
	public String getNomeCompleto(){
		return this.nomeCompleto;
	}
	
	@Override
	public String toString() {
		return nomeCompleto;
	}
		
	private String setSenhaCriptografada(String senha){
		try {
			return this.senha = ConvertToMD5.convertPasswordToMD5(senha);
		} catch (NoSuchAlgorithmException e) {
			return senha;
		}
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