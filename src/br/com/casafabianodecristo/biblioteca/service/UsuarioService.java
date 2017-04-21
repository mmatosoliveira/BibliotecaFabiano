package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.modelmapper.ModelMapper;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;

public class UsuarioService {
	private EntityManagerFactory emf;
	private EntityManager        em;
	
	private void createEntityManagerFactory() {
		try{
			emf = Persistence.createEntityManagerFactory("BibliotecaFabiano2");
		}
		catch(Exception e){
			System.out.println("Erro no database!");
		}
	}

	private void closeEntityManagerFactory() {
		try{
			emf.close();
		}
		catch(Exception e){
			System.out.println("Erro!");
		}
	}

	private void createEntityManager() {
		try{
			em  = emf.createEntityManager();
		}
		catch(Exception e){
			System.out.println("Erro!");
		}
	}

	private void closeEntityManager() {
		try{
			em.close();
		}
		catch(Exception e){
			System.out.println("Erro!");
		}
	}
	
	public UsuarioService(){}
	
	public Usuario getUsuarioById(int id){
		Usuario u = em.find(Usuario.class, id);
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public List<UsuarioDto> getUsuariosParaEmprestimo (String nomeUsuario){
		List<Usuario> usuarios = null;
		ModelMapper mapper = new ModelMapper();
		List<UsuarioDto> usuariosDto = new ArrayList<UsuarioDto>();
		
		createEntityManagerFactory();
		createEntityManager();
			Query q = em.createNativeQuery("select * from Usuario U "
					+ "where U.FlPossuiAtraso <> 0 and U.Nome like '%"+nomeUsuario+"%'", Usuario.class);
	
			try{
				usuarios = q.getResultList();
			}
			catch(NoResultException ex){ex.printStackTrace();}
		closeEntityManager();
		closeEntityManagerFactory();
		
		for (Usuario u : usuarios){
			usuariosDto.add(mapper.map(u, UsuarioDto.class));
		}
		System.out.println(usuariosDto);
		return usuariosDto;
	}
	
	@SuppressWarnings("unchecked")
	public int atualizarUsuariosComAtraso(){
		List<Integer> idsUsuariosComAtraso = new ArrayList<>();
		Usuario usuario = new Usuario();
		createEntityManagerFactory();
			createEntityManager();
			Query query = em.createNativeQuery("select * from Usuario U "
					+ "left join Emprestimo E ON U.Id = E.IdUsuario "
					+ "where E.DataDevolucaoEfetiva is null", Integer.class);
			try{
				idsUsuariosComAtraso = query.getResultList();
			}
			catch(NoResultException e){
				return 0;
			}
			for(Integer item: idsUsuariosComAtraso){
				usuario = this.getUsuarioById(item);
				usuario.setFlPossuiAtraso(1);
				em.merge(usuario);
				//em.
			}
			closeEntityManager();
		closeEntityManagerFactory();
		return 1;
	}
	
	public List<UsuarioDto> getUsuarios(String nomeUsuario){
		List<Usuario> usuarios = null;
		ModelMapper mapper = new ModelMapper();
		List<UsuarioDto> usuariosDto = new ArrayList<UsuarioDto>();
		
		createEntityManagerFactory();
		createEntityManager();
		
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o where o.nomeCompleto like :nomeUsuario order by o.nomeCompleto asc", Usuario.class);
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
			catch(Exception ex){System.out.println("Exce��o no getUsuarioPorNome...");}
		closeEntityManager();
		closeEntityManagerFactory();
		return user;
	}
	
	public Usuario logar(String nomeUsuario, String senha){
		Usuario usuarioLogado = null;
		//this.atualizarUsuariosComAtraso();
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
					catch(DatabaseException ex){
						System.out.println("Exce��o");
					}
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
	
	public void atualizarUsuario(UsuarioDto dto) throws ApplicationException{
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Usuario user = em.find(Usuario.class, dto.getId());
					user = UsuarioUpdater.update(dto, user);
					try{
						em.merge(user);
					}
					catch (Exception e){
						throw new ApplicationException("Erro ao editar usuário. Não foi possível realizar a operação de atualização.", "Editar dados do usuário", "Erro ao editar usuário. Não foi possível realizar a operação de atualização");
					}
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
	}
	
	public boolean cadastrarUsuario(UsuarioDto dto){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					Usuario usuario = UsuarioFactory.create(dto);
					try
					{
						em.persist(usuario);
					}
					catch(Exception e){
						em.getTransaction().rollback();
						return false;
					}
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		return true;
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
