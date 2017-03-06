package br.com.casafabianodecristo.biblioteca.utils;

public enum ModelosRelatorio {
	TODOS_LIVROS(-1), EMPRESTIMOS_POR_USUARIO(-2), EMPRESTIMOS_POR_TEMPO(-3), ETIQUETAS(-4), 
	LIVROS_ATRASADOS(-5), LIVROS_REMOVIDO(-6), LIVROS_DOADOS(-7), TODAS_CLASSIFICACOES(-8), TODOS_USUARIOS(-9),
	TODOS_USUARIOS_INATIVOS(-10);
	
	private final int valorOpcao;
	
	ModelosRelatorio(int valor){
		valorOpcao = valor;
	}
	
	public int getValorOpcao(){
		return valorOpcao;
	}
}