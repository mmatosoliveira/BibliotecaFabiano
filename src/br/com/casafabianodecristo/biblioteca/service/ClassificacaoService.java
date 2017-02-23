package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;
import java.util.*;

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
	
	public int cadastrarClassificacao(ClassificacaoDto dto){
		List<Classificacao> c = new ArrayList<Classificacao>();
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Classificacao classificacao = ClassificacaoFactory.create(dto);
					em.persist(classificacao);
				em.getTransaction().commit();
				TypedQuery<Classificacao> query = em.createQuery("select o from Classificacao o where o.cor = :hexa", Classificacao.class);
				query.setParameter("hexa", dto.getCor());
				
				try{
					c = query.getResultList();
				}
				catch(Exception e){
					em.getTransaction().rollback();
					e.printStackTrace();
				}
			closeEntityManager();
		closeEntityManagerFactory();
		return (c == null) ? 1 : 0;
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
	
	public List<Classificacao >getClassificacaoPorCor(String hexa){
		List<Classificacao> c = new ArrayList<Classificacao>();
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Classificacao> query = em.createQuery("select o from Classificacao o where o.cor = :hexa", Classificacao.class);
			query.setParameter("hexa", hexa);
			
			try{
				c = query.getResultList();
			}
			catch(Exception e){System.out.println("deu exceção");}
			
		closeEntityManager();
		closeEntityManagerFactory();
		return c;
	}
	
	public List<Classificacao> getClassificacaoPorNome(String nome){
		List<Classificacao> c = new ArrayList<Classificacao>();
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Classificacao> query = em.createQuery("select o from Classificacao o where o.descricao = :nome", Classificacao.class);
			query.setParameter("nome", nome);
			
			try{
				c = query.getResultList();
			}
			catch(Exception e){System.out.println("deu exceção");}
			
		closeEntityManager();
		closeEntityManagerFactory();
		return c;
	}


	public List<Classificacao> getClassificacoes() {
		List<Classificacao> c = new ArrayList<Classificacao>();
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Classificacao> query = em.createQuery("select o from Classificacao o", Classificacao.class);
			
			try{
				c = query.getResultList();
			}
			catch(NoResultException e){}
			
		closeEntityManager();
		closeEntityManagerFactory();
		return c;
	}	
}