package br.com.casafabianodecristo.biblioteca.dto;

public class MensagemDto {
	private int id;
	
	private String tituloMensagem;
	
	private String textoMensagem;
	
	private int flLida;
	
	private UsuarioDto destinatario;
	
	private UsuarioDto remetente;

	public MensagemDto(int id, String tituloMensagem, String textoMensagem, int flLida, UsuarioDto destinatario,
			UsuarioDto remetente) {
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

	public UsuarioDto getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(UsuarioDto destinatario) {
		this.destinatario = destinatario;
	}

	public UsuarioDto getRemetente() {
		return remetente;
	}

	public void setRemetente(UsuarioDto remetente) {
		this.remetente = remetente;
	}
}