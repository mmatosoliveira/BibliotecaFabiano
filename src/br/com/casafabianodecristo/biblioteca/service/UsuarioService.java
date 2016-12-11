package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;

public class UsuarioService {
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
	
	public UsuarioService(){}
	
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
	
	public Usuario getUsuarioPorNomeUsuario(String nomeUsuario){
		Usuario user = null;
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o where o.nomeUsuarioAcessoSistema = :nomeUsuario", Usuario.class);	
			query.setParameter("nomeUsuario", nomeUsuario);
			
			try{
				user = query.getSingleResult();
			}
			catch(NoResultException ex){}
		closeEntityManager();
		closeEntityManagerFactory();
		return user;
	}
	
	public Usuario logar(String nomeUsuario, String senha){
		Usuario usuarioLogado = null;
		createEntityManagerFactory();
			createEntityManager();
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o "+
					 "where o.nomeUsuarioAcessoSistema = :nomeUsuario " + 
					 "and o.senha = :senha" , Usuario.class);
					query.setParameter("nomeUsuario", nomeUsuario);
					query.setParameter("senha", senha);
					
					try {
						usuarioLogado = query.getSingleResult();
					}
					catch(NoResultException ex){}
			closeEntityManager();
		closeEntityManagerFactory();
		return usuarioLogado;
	}
	
	public String getDicaSenha(String nomeUsuario){
		Usuario user = null;
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o where o.nomeUsuarioAcessoSistema = :nomeUsuario", Usuario.class);	
			query.setParameter("nomeUsuario", nomeUsuario);
			
			try{
				user = query.getSingleResult();
			}
			catch(NoResultException ex){ex.printStackTrace();}
			
		closeEntityManager();
		closeEntityManagerFactory();
		return user.getDicaSenha();
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
