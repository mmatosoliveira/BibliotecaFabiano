package br.com.casafabianodecristo.biblioteca.utils;

public enum DuvidasFrequentes {
	DADOS_NAO_INFORMADOS_LOGIN     ("Login",  "MA",   ""),
    TENTATIVAS_LOGIN_EXCEDIDAS     ("Login",  "O que siginifica número de tentativas excedido no Login?", "Isso significa que você tentou acessar mais de 3 vezes o sistema com um nome de usuário que não possui algumas informações para acessar o sistema.");

    private final String secao;
    private final String duvida;
    private final String resposta;
	
    private DuvidasFrequentes(String secao, String duvida, String resposta) {
		this.secao = secao;
		this.duvida = duvida;
		this.resposta = resposta;
	}

    
}