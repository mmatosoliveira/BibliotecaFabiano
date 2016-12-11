package br.com.casafabianodecristo.biblioteca.dto;

import java.util.*;

import br.com.casafabianodecristo.biblioteca.model.Emprestimo;

public class InicialDto {
	private UsuarioDto usuario;
	private String textoLembrete;
	private boolean possuiMensagens;
	private List<Emprestimo> emprestimo = new ArrayList<Emprestimo>();
	
	public InicialDto(UsuarioDto usuario, String textoLembrete, boolean possuiMensagens,
			List<Emprestimo> emprestimo) {
		super();
		this.usuario = usuario;
		this.textoLembrete = textoLembrete;
		this.possuiMensagens = possuiMensagens;
		this.emprestimo = emprestimo;
	}
	
	public InicialDto(){}
	
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
	public String getTextoLembrete() {
		return textoLembrete;
	}
	public void setTextoLembrete(String textoLembrete) {
		this.textoLembrete = textoLembrete;
	}
	public boolean isPossuiMensagens() {
		return possuiMensagens;
	}
	public void setPossuiMensagens(boolean possuiMensagens) {
		this.possuiMensagens = possuiMensagens;
	}

	public List<Emprestimo> getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(List<Emprestimo> emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	
	
}