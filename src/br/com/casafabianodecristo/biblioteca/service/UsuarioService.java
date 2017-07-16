package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dbmanager.DbManager;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
import br.com.casafabianodecristo.biblioteca.factory.*;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.updater.*;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;

public class UsuarioService {
	private EntityManagerFactory emf;
	private EntityManager        em;
	private Alertas alerta = new Alertas();
	
	private void createEntityManagerFactory() {
		try{
			emf = Persistence.createEntityManagerFactory("BibliotecaFabiano2");
		}
		catch(Exception e){
			//throw new ApplicationException("Erro na criação do entity manager.", "Login", "Erro na criação do contexto de entidades. \nContate o administrador do sistema!");
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
		em  = emf.createEntityManager();
	}

	private void criarEntityManagerFactory() {
		try{
			emf = Persistence.createEntityManagerFactory("BibliotecaFabiano2");
		}
		catch(RuntimeException e){
			System.out.println("Caiu no erro factory");
			throw new ApplicationException("Erro na criação do entity manager.", "Login", "Erro na criação do contexto de entidades.");
		}
	}
	
	private void criarEntityManager() throws Exception{
		try{
			em  = emf.createEntityManager();
		}
		catch(RuntimeException  e){
			throw new Exception("Erro na criação do entity manager.");
			//alerta.alertaErro("Login", "Erro na criação do contexto de entidades.");
			//System.out.println("Caiu no erro 1");


			//System.out.println("execução dps do alerta");

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
		/*try{
			DbManager.createEntityManagerFactory();
			DbManager.createEntityManager();
		}
		catch (Exception e) {alerta.notificacaoAlerta("erro", "erro");}
		
		TypedQuery<Usuario> query = em.createQuery("select o from Usuario o where o.nomeUsuarioAcessoSistema = :nomeUsuario", Usuario.class);	
		query.setParameter("nomeUsuario", nomeUsuario);
	
		user = query.getSingleResult();
		try{
			user = query.getSingleResult();
		}
		catch(Exception ex){System.out.println("Erro na consulta...");}
		
		DbManager.closeEntityManagerFactory();
		DbManager.closeEntityManager();*/
		
		try {
			criarEntityManagerFactory();
			criarEntityManager();
		} catch (Exception e) {alerta.notificacaoAlerta("erro", "erro");}
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
	
	public UsuarioDto logar(String nomeUsuario, String senha){
		Usuario usuarioLogado = null;
		ModelMapper mapper = new ModelMapper();
		UsuarioDto dto;
		
		try {
			criarEntityManagerFactory();
			criarEntityManager();
		} catch (Exception e) {}
			TypedQuery<Usuario> query = em.createQuery("select o from Usuario o "+
					 "where o.nomeUsuarioAcessoSistema = :nomeUsuario " + 
					 "and o.senha = :senha" , Usuario.class);
					query.setParameter("nomeUsuario", nomeUsuario);
					query.setParameter("senha", senha);
					query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
					
					try {
						usuarioLogado = query.getSingleResult();
					}
					catch(NoResultException e){
						dto = null;
					}

			closeEntityManager();
		closeEntityManagerFactory();
		
		dto = (usuarioLogado != null) ? mapper.map(usuarioLogado, UsuarioDto.class) : null;
		return dto;
	}
	
	public String getDicaSenha(String nomeUsuario){
		String dicaSenha = null;
		createEntityManagerFactory();
		createEntityManager();
			TypedQuery<String> query = em.createQuery("select o.dicaSenha from Usuario o where o.nomeUsuarioAcessoSistema = :nomeUsuario", String.class);	
			query.setParameter("nomeUsuario", nomeUsuario);
			
			try{
				dicaSenha = query.getSingleResult();
			}
			catch(NoResultException ex){dicaSenha = null;}
			
		closeEntityManager();
		closeEntityManagerFactory();
		return dicaSenha;
	}
	
	public void atualizarUsuario(UsuarioDto dto) throws ApplicationException{
		//dto.setSobrenome(null);
		createEntityManagerFactory();
			createEntityManager();
				try {
					em.getTransaction().begin();
						Usuario user = em.find(Usuario.class, dto.getId());
						user = UsuarioUpdater.update(dto, user);
						em.merge(user);
					em.getTransaction().commit();
				}
				catch (Exception e){
					throw new ApplicationException("Erro ao editar usuário. Não foi possível realizar a operação de atualização.", "Editar dados do usuário", "Erro ao editar usuário. Não foi possível realizar a operação de atualização", dto.isExibeMsg());
				}
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
