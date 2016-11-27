package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;

public class LivroService {
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

	public LivroService(){}
	
	public Livro getLivroPorTombo(int tombo){
		Livro livro = null;
		TypedQuery<Livro> query = em.createQuery("select o from Livro o " +
                "where o.tomboPatrimonial = :tombo",
                Livro.class);
		
		query.setParameter("tombo", tombo);
		
		try {
			livro = query.getSingleResult();
		}
		catch (NoResultException ex){}
		
		return livro;
	}
	
	public void atualizarLivro(LivroDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Livro livro = getLivroPorTombo(dto.getTomboPatrimonial());
					livro = LivroUpdater.update(dto);
					em.merge(livro);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public void cadastrarLivro(LivroDto dto){	
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Livro livro = LivroFactory.create(dto);
					em.merge(livro);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		
	}
	
	public void removerLivroAcervo(int tomboPatrimonial){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Livro livro = getLivroPorTombo(tomboPatrimonial);
					livro.setFlRemovido(1);
					em.merge(livro);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}

}
