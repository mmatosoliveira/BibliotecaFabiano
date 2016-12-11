package br.com.casafabianodecristo.biblioteca.service;

import java.util.*;
import javax.persistence.*;
import br.com.casafabianodecristo.biblioteca.model.Mensagem;

public class MensagemService {
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
	
	public MensagemService(){}
	
	public boolean usuarioPossuiMensagensNaoLidas(int idUsuario){
		List<Mensagem> msg = new ArrayList<Mensagem>();
	
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<Mensagem> query = em.createQuery("select o from Mensagem o where o.destinatario.id = :idUsuario"
						+ " and o.flLida = 0", Mensagem.class);
				query.setParameter("idUsuario", idUsuario);
				
				try {
					msg = query.getResultList();
				}
				catch (NoResultException e){e.printStackTrace();}
				
			closeEntityManager();
		closeEntityManagerFactory();
		
		if (msg.size() != 0){
			return true;
		}
		else return false;
	}
}