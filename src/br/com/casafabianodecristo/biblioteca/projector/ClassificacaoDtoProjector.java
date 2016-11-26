package br.com.casafabianodecristo.biblioteca.projector;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;

/*
 * Implementação básica de um ModelMapper (ModelMapper = DtoProjector - C#) 
 */

public class ClassificacaoDtoProjector {
	Classificacao c = new Classificacao();
	ModelMapper mapper = new ModelMapper();
	
	ClassificacaoDto dto = mapper.map(c, ClassificacaoDto.class);
}
