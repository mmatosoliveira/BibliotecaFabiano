package br.com.casafabianodecristo.biblioteca.factory;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.LivroDto;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import br.com.casafabianodecristo.biblioteca.model.Livro;

public class LivroFactory {
	public static Livro create(LivroDto dto){
		Livro livro = new Livro();
		ModelMapper mapper = new ModelMapper();
		Classificacao c = mapper.map(dto.getClassificacaoLivro(), Classificacao.class);
		
		livro.setTomboPatrimonial(dto.getTomboPatrimonial());
		livro.setTitulo(dto.getTitulo());
		livro.setSubtitulo(dto.getSubtitulo());
		livro.setNomeAutor(dto.getNomeAutor());
		livro.setEditora(dto.getEditora());
		livro.setEdicao(dto.getEdicao());
		livro.setFlEmprestado(dto.getFlEmprestado());
		livro.setFlDoado(dto.getFlDoado());
		livro.setFlRemovido(dto.getFlRemovido());
		livro.setClassificacaoLivro(c);
		
		return livro;
	}
}
