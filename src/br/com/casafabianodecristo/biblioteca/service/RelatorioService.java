package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.RelatorioDto;
import br.com.casafabianodecristo.biblioteca.model.Relatorio;

public class RelatorioService {
	private EntityManagerFactory emf;
	private EntityManager        em;
	
	private void createEntityManagerFactory() {
		emf = Persistence.createEntityManagerFactory("BibliotecaFabiano2");
	}

	private void closeEntityManagerFactory() {
		emf.close();
	}

	private void createEntityManager() {
		em  = emf.createEntityManager();
	}

	private void closeEntityManager() {
		em.close();
	}
	
	public RelatorioService(){}
	
	public List<RelatorioDto> getModelosRelatorios(){
		List<RelatorioDto> relatorios = new ArrayList<RelatorioDto>();
		ModelMapper mapper = new ModelMapper();
		List<Relatorio> tmp = null;
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<Relatorio> query;
				query = em.createQuery("select r from Relatorio r", Relatorio.class);
				try{
					tmp = query.getResultList();
				}
				catch(NoResultException e){}
			closeEntityManager();
		closeEntityManagerFactory();
		for(Relatorio item : tmp){
			relatorios.add(mapper.map(item, RelatorioDto.class));
		}
		return relatorios;
	}
}
