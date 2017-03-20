package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;

import org.modelmapper.ModelMapper;

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
	
	@SuppressWarnings("unchecked")
	public List<EmprestimoDto> getEmprestimosPorUsuario(String nomeUsuario, boolean apenasAtrasados){
		List<Emprestimo> emprestimos = new ArrayList<>();
		List<EmprestimoDto> dto = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();
		createEntityManagerFactory();
			createEntityManager();
					Query query; 
					if(!apenasAtrasados){
						query = em.createNativeQuery("select * from Emprestimo E"
								+ " join Usuario U ON U.Id = E.IdUsuario"
								+ " where U.NomeCompleto like '%"+nomeUsuario+"%'", Emprestimo.class);
					}
					else{
						query = em.createNativeQuery("select * from Emprestimo E"
								+ " join Usuario U ON U.Id = E.IdUsuario"
								+ " where U.NomeCompleto like '%"+nomeUsuario+"%'"
								+ " and E.DataDevolucaoPrevista < CURDATE()"
								+ " and E.DataDevolucaoEfetiva IS NULL", Emprestimo.class);
					}
					try{
						emprestimos = query.getResultList();
					}
					catch(NoResultException e){}
			closeEntityManager();
		closeEntityManagerFactory();
		
		for(Emprestimo item : emprestimos){
			dto.add(mapper.map(item, EmprestimoDto.class));
		}
		return dto;
	}
	
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
	
	public boolean realizarEmprestimo(EmprestimoDto dto){
		createEntityManagerFactory();
			createEntityManager();
			boolean result = true;
				em.getTransaction().begin();
					for(LivroDto livroDto : dto.getLivros()){
						Emprestimo emprestimo = EmprestimoFactory.create(dto, livroDto);
						emprestimo.getLivro().setFlEmprestado(1);
						emprestimo.getLivro().addEmprestimo(emprestimo);
						
						Emprestimo e = em.merge(emprestimo);
						if (e == null) result = false; 
						else result = true;
					}
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		System.out.println(result);
		return result;
	}
	
	public void devolverLivro(EmprestimoDto dto){
		createEntityManagerFactory();
		createEntityManager();
			Emprestimo e = em.find(Emprestimo.class, dto.getId());
			e.setDataDevolucaoEfetiva(new Date());
			em.getTransaction().begin();
			em.merge(e);
			em.getTransaction().commit();
		closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public boolean renovarEmprestimo(int id){
		boolean result = true;
		final int ONE_WEEK = 86400 * 7 * 1000;
		
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Emprestimo e = em.find(Emprestimo.class, id);
					e.setDataDevolucaoPrevista(new Date(System.currentTimeMillis() + ONE_WEEK));
					Emprestimo obj = em.merge(e);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		result = (obj == null) ? false : true;
		return result;
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
