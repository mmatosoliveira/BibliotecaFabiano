package br.com.casafabianodecristo.biblioteca.dto;

public class LembreteDto {
	private int id;
	private String textoLembrete;
	private UsuarioDto usuario;
	
	public LembreteDto(int id, String textoLembrete, UsuarioDto usuario) {
		super();
		this.id = id;
		this.textoLembrete = textoLembrete;
		this.usuario = usuario;
	}

	public LembreteDto(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTextoLembrete() {
		return textoLembrete;
	}
	public void setTextoLembrete(String textoLembrete) {
		this.textoLembrete = textoLembrete;
	}
	public UsuarioDto getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}
}
