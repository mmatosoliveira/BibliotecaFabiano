package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;

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
	
	public List<UsuarioDto> getUsuarios(String nomeUsuario){
		List<Usuario> usuarios = null;
		ModelMapper mapper = new ModelMapper();
		List<UsuarioDto> usuariosDto = new ArrayList<UsuarioDto>();
		
		createEntityManagerFactory();
		createEntityManager();
		
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o where o.nomeUsuario like :nomeUsuario", Usuario.class);
			query.setParameter("nomeUsuario", "%" + nomeUsuario + "%");
			try{
				usuarios = query.getResultList();
			}
			catch(NoResultException ex){ex.printStackTrace();}
		closeEntityManager();
		closeEntityManagerFactory();
		
		for (Usuario u : usuarios){
			usuariosDto.add(mapper.map(u, UsuarioDto.class));
		}
		
		return usuariosDto;
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
