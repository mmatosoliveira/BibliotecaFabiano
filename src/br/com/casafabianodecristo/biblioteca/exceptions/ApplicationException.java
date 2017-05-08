package br.com.casafabianodecristo.biblioteca.exceptions;

import br.com.casafabianodecristo.biblioteca.utils.Alertas;

@SuppressWarnings("serial")
public class ApplicationException extends RuntimeException {
	private Alertas alerta = new Alertas();

	public ApplicationException(String exception, String titulo, String texto) {
		super(exception);
		alerta.alertaErro(titulo, texto + getTextoGenerico());
	}
	
	public ApplicationException(String exception, String titulo, String texto, boolean exibeMsg) {
		super(exception);
		if(exibeMsg)
			alerta.alertaErro(titulo, texto + getTextoGenerico());
	}
	
	private String getTextoGenerico(){
		return "\nTente novamente mais tarde. Se o erro persistir, contate o Administrador do sistema.";
	}
}