package br.com.casafabianodecristo.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public int getUltimoTombo(){
		int result = 0;
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<Livro> query = em.createQuery("select o from Livro o order by o.tomboPatrimonial DESC", Livro.class);
				query.setFirstResult(0);
				query.setMaxResults(1);
				try{
					result = query.getSingleResult().getTomboPatrimonial();
				}
				catch(Exception e){	}
			closeEntityManager();
		closeEntityManagerFactory();
		return result;
	}
	
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
	
	public List<Livro> pesquisaRapidaLivro(String texto, boolean titulo, boolean autor, boolean tombo, boolean soDisponiveis){
		List<Livro> livros = new ArrayList<>();
		TypedQuery<Livro> query;
		
		createEntityManagerFactory();
		createEntityManager();
		if (titulo){
			query = em.createQuery("select o from Livro o where o.titulo like :texto", Livro.class);
			query.setParameter("texto", "%" + texto + "%");
		}
		else if (autor){
			query = em.createQuery("select o from Livro o where o.nomeAutor like :texto", Livro.class);
			query.setParameter("texto", "%" + texto + "%");
		}
		else if (tombo){
			query = em.createQuery("select o from Livro o where o.tomboPatrimonial = :tombo", Livro.class);
			int tomboP = 0;
			try {tomboP = Integer.parseInt(texto);}
			catch(NumberFormatException e){return new ArrayList<Livro>();}
			query.setParameter("tombo", tomboP);
		}
		else if(soDisponiveis){
			query = em.createQuery("select o from Livro o where o.flEmprestado = 0 and o.titulo like :texto", Livro.class);
			query.setParameter("texto", "%" + texto + "%");
		}
		else {
			query = em.createQuery("select o from Livro o where o.titulo like :texto", Livro.class);
			query.setParameter("texto", "%" + texto + "%");
		}
		
		try{
			livros = query.getResultList();
		}
		catch(NoResultException ex){
			System.out.println("Não foi possível encontrar o livro especificado.");
		}
		closeEntityManager();
		closeEntityManagerFactory();
		return livros;
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
	
	public void excluir(LivroDto dto){
		createEntityManagerFactory();
			createEntityManager();
				Livro l = getLivroPorTombo(dto.getTomboPatrimonial());
				em.getTransaction().begin();
					em.remove(l);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public int cadastrarLivro(LivroDto dto, int qtd) {	
		int retorno = 0;
		createEntityManagerFactory();
			createEntityManager();
				Livro livroCadastrado = getLivroPorTombo(dto.getTomboPatrimonial());
				if (livroCadastrado == null){
					em.getTransaction().begin();
						for (int i = 0; i < qtd; i++){
							Livro livro = LivroFactory.create(dto);
							em.merge(livro);
							dto.setTomboPatrimonial(dto.getTomboPatrimonial() + 1);
						}						
					em.getTransaction().commit();
				}
				else 
					retorno = 1;
				
			closeEntityManager();
		closeEntityManagerFactory();
		return retorno;
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
