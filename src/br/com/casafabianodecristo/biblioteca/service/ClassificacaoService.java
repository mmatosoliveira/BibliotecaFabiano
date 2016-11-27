package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;

public class ClassificacaoService {
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

	public ClassificacaoService(){}
	
	public Classificacao getClassificacaoById(int id){
		createEntityManagerFactory();
			createEntityManager();
				Classificacao c = em.find(Classificacao.class, id);
			closeEntityManager();
		closeEntityManagerFactory();
		return c;
	}
	
	public void cadastrarClassificacao(ClassificacaoDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Classificacao classificacao = ClassificacaoFactory.create(dto);
					em.persist(classificacao);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public void atualizarClassificacao(ClassificacaoDto dto){
		createEntityManagerFactory();
		createEntityManager();
			em.getTransaction().begin();
				Classificacao classificacao = em.find(Classificacao.class, dto.getId());
				classificacao = ClassificacaoUpdater.update(dto);
				em.merge(classificacao);
			em.getTransaction().commit();
		closeEntityManager();
	closeEntityManagerFactory();
	}
	
}
