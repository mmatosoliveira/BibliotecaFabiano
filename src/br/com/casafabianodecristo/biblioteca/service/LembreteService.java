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
	
	public void cadastrarLembrete(Lembrete l){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					em.persist(l);
					em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public void editarLembrete(Lembrete l){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					em.merge(l);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public void removerLembrete(Lembrete l){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					em.remove(l);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public String getLembretePorUsuario(int idUsuario){
		Lembrete lembrete = new Lembrete();
		createEntityManagerFactory();
			createEntityManager();
			
				TypedQuery<Lembrete> query = em.createQuery("select o from Lembrete o where o.usuario.id = :idUsuario", Lembrete.class);
				query.setParameter("idUsuario", idUsuario);
				try{
					lembrete = query.getSingleResult();
				}
				catch(NoResultException e){}
				
			closeEntityManager();
		closeEntityManagerFactory();
		
		return lembrete.getLembrete(); 
	}
}
