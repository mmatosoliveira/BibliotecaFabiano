package br.com.casafabianodecristo.biblioteca.updater;

import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;

public class ClassificacaoUpdater {
	public static Classificacao update(ClassificacaoDto dto){		
		Classificacao classificacao = new Classificacao();
		
		classificacao.setId(dto.getId());
		classificacao.setDescricao(dto.getDescricao());
		classificacao.setCor(dto.getCor());
		
		return classificacao;
	}
}
