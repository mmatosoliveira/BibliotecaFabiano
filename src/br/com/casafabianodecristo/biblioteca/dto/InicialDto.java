package br.com.casafabianodecristo.biblioteca.dto;

import java.util.*;

import br.com.casafabianodecristo.biblioteca.model.Emprestimo;

public class InicialDto {
	private UsuarioDto usuario;
	private List<Emprestimo> emprestimo = new ArrayList<Emprestimo>();
	
	public InicialDto(UsuarioDto usuario, List<Emprestimo> emprestimo) {
		super();
		this.usuario = usuario;
		this.emprestimo = emprestimo;
	}
	
	public InicialDto(){}
	
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public List<Emprestimo> getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(List<Emprestimo> emprestimo) {
		this.emprestimo = emprestimo;
	}
}