package br.com.casafabianodecristo.biblioteca.factory;

import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;

public class ClassificacaoFactory {
	public static Classificacao create(ClassificacaoDto dto){		
		Classificacao classificacao = new Classificacao();
		
		classificacao.setDescricao(dto.getDescricao());
		classificacao.setCor(dto.getCor());
		
		return classificacao;
	}
}
