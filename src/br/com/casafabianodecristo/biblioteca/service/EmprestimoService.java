package br.com.casafabianodecristo.biblioteca.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import java.util.*;
import javax.persistence.*;
//import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
//import br.com.casafabianodecristo.biblioteca.updater.*;

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
	
	public void realizarEmprestimo(EmprestimoDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Emprestimo emprestimo = EmprestimoFactory.create(dto);
					emprestimo.getLivro().setFlEmprestado(1);
					emprestimo.getLivro().addEmprestimo(emprestimo);
					em.persist(emprestimo);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public List<Emprestimo> getDevolucoesPrevistas(){
		List<Emprestimo> devolucoes = null;
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFormatada = new Date();
		try{
			dataFormatada = sdf.parse(sdf.format(d));
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Emprestimo> query = em.createQuery("select o from" + 
											" Emprestimos o where" + 
											" o.dataDevolucaoPrevista = :dataDevolucao", Emprestimo.class);
			query.setParameter("dataDevolucao", dataFormatada);
			
			try{
				devolucoes = query.getResultList();
			}
			catch(NoResultException ex){}
		closeEntityManager();
		closeEntityManagerFactory();
		return devolucoes;
	}
}
