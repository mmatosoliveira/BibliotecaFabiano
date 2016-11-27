package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.model.*;

public class LembreteService {
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
	
	public LembreteService(){}
	
	public String getLembretePorUsuario(int idUsuario){
		Lembrete lembrete = new Lembrete();
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<Lembrete> query = em.createQuery("select o from Lembrete o where o.idUsuario like :idUsuario", Lembrete.class);
				query.setParameter("idUsuario", idUsuario);
				lembrete = query.getSingleResult();
			closeEntityManager();
		closeEntityManagerFactory();
		return lembrete.getLembrete();
	}
}
