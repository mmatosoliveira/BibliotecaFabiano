package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.dto.LivroDto;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.factory.ClassificacaoFactory;
import br.com.casafabianodecristo.biblioteca.factory.EmprestimoFactory;
import br.com.casafabianodecristo.biblioteca.factory.LivroFactory;
import br.com.casafabianodecristo.biblioteca.factory.UsuarioFactory;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import br.com.casafabianodecristo.biblioteca.model.Emprestimo;
import br.com.casafabianodecristo.biblioteca.model.Livro;
import br.com.casafabianodecristo.biblioteca.model.Usuario;
import br.com.casafabianodecristo.biblioteca.updater.ClassificacaoUpdater;
import br.com.casafabianodecristo.biblioteca.updater.LivroUpdater;
import br.com.casafabianodecristo.biblioteca.updater.UsuarioUpdater;

public class BibliotecaAppService {
	private EntityManagerFactory emf;
	private EntityManager        em;

	public BibliotecaAppService() {	}

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

	public Classificacao getClassificacaoById(int id){
		createEntityManagerFactory();
			createEntityManager();
				Classificacao c = em.find(Classificacao.class, id);
			closeEntityManager();
		closeEntityManagerFactory();
		return c;
	}
	
	public Usuario getUsuarioById(int id){
		Usuario u = em.find(Usuario.class, id);
		
		return u;
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
	
	public void atualizarUsuario(UsuarioDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Usuario user = getUsuarioById(dto.getId());
					System.out.println(user);
					user = UsuarioUpdater.update(dto);
					System.out.println(user);
					em.merge(user);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public void cadastrarUsuario(UsuarioDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Usuario usuario = UsuarioFactory.create(dto);
					em.persist(usuario);
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
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
	
	public void removerUsuario(int idUsuario){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Usuario usuario = em.find(Usuario.class, idUsuario);
					em.remove(usuario);
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
	
	public void inativarUsuario(int id){
		createEntityManagerFactory();
		createEntityManager();
			em.getTransaction().begin();
				Usuario usuario = em.find(Usuario.class, id);
				usuario.setFlInativo(1);
				em.merge(usuario);
			em.getTransaction().commit();
		closeEntityManager();
	closeEntityManagerFactory();
	}
}
