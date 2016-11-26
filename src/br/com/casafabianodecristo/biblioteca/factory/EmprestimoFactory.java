package br.com.casafabianodecristo.biblioteca.factory;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.model.*;

public class EmprestimoFactory {
	public static Emprestimo create(EmprestimoDto dto){		
		Emprestimo emprestimo = new Emprestimo();
		ModelMapper mapper = new ModelMapper();
		Livro livro = mapper.map(dto.getLivro(), Livro.class);
		Usuario usuario = mapper.map(dto.getUsuario(), Usuario.class);
		
		emprestimo.setDataEmprestimo(dto.getDataEmprestimo());
		emprestimo.setDataDevolucaoPrevista(dto.getDataDevolucaoPrevista());
		emprestimo.setDataDevolucaoEfetiva(dto.getDataDevolucaoEfetiva());
		emprestimo.setLivro(livro);
		emprestimo.setUsuario(usuario);
		
		return emprestimo;
	}
}
