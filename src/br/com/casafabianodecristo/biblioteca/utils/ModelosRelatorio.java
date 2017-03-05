package br.com.casafabianodecristo.biblioteca.utils;

public enum ModelosRelatorio {
	TODOSLIVROS(-1);
	
	private final int valorOpcao;
	
	ModelosRelatorio(int valor){
		valorOpcao = valor;
	}
	
	public int getValorOpcao(){
		return valorOpcao;
	}
}