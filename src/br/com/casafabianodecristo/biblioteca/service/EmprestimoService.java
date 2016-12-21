package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;

public class EmprestimoService {
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
	
	public EmprestimoService(){}
	
	public List<Emprestimo> getEmprestimos(){
		List<Emprestimo> emprestimos = null;
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					TypedQuery<Emprestimo> query = em.createQuery("select o from Emprestimo o", Emprestimo.class);
					try{
						emprestimos = query.getResultList();
					}
					catch(NoResultException e){}
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		return emprestimos;
	}
	
	public Emprestimo realizarEmprestimo(EmprestimoDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Emprestimo emprestimo = EmprestimoFactory.create(dto);
					emprestimo.getLivro().setFlEmprestado(1);
					emprestimo.getLivro().addEmprestimo(emprestimo);
					Emprestimo e = em.merge(emprestimo);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		return e;
	}
	
	public void devolverLivro(EmprestimoDto dto){
		createEntityManagerFactory();
		createEntityManager();
			Emprestimo e = em.find(Emprestimo.class, dto.getId());
			System.out.println(e);
			e.setDataDevolucaoEfetiva(new Date());
			em.getTransaction().begin();
			em.merge(e);
			em.getTransaction().commit();
		closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public List<Emprestimo> getDevolucoesPrevistas(){
		List<Emprestimo> devolucoes = null;
		Date d = new Date();
		
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Emprestimo> query = em.createQuery("select o from" + 
											" Emprestimo o where" + 
											" o.dataDevolucaoPrevista = :dataDevolucao", Emprestimo.class);
			query.setParameter("dataDevolucao", d, TemporalType.DATE);
			
			try{
				devolucoes = query.getResultList();
			}
			catch(NoResultException ex){}
		closeEntityManager();
		closeEntityManagerFactory();
		
		return devolucoes;
	}
}
