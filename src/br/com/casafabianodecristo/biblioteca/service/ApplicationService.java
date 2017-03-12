package br.com.casafabianodecristo.biblioteca.service;

import javax.persistence.*;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.ParametrizacaoSistemaDto;
import br.com.casafabianodecristo.biblioteca.model.ParametrizacaoSistema;

public class ApplicationService {
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

	public ApplicationService() {}
	
	public int salvarParametrizacao(String nomeImpressora){
		createEntityManagerFactory();
			createEntityManager();
				em.getTransaction().begin();
					ParametrizacaoSistema obj = new ParametrizacaoSistema(nomeImpressora);
					try{
						em.persist(obj);					
					}
					catch(Exception e){
						return 0;
					}
				em.getTransaction().commit();
			closeEntityManager();
		closeEntityManagerFactory();
		return 1;
	}
	
	public ParametrizacaoSistemaDto getParametrizacaoSistemaVigente() throws Exception{
		ParametrizacaoSistemaDto  dto = null;
		ModelMapper mapper = new ModelMapper();
		
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<ParametrizacaoSistema> query = em.createQuery("select o from ParametrizacaoSistema o order by o.id desc", ParametrizacaoSistema.class);
				query.setFirstResult(0);
				query.setMaxResults(1);
				ParametrizacaoSistema param = query.getSingleResult();
				if(param == null)
					throw new Exception("Não foi encontrada nenhuma parametrização cadastrada.");
				dto = mapper.map(param, ParametrizacaoSistemaDto.class);
			closeEntityManager();
		closeEntityManagerFactory();
		
		return dto;
	}
}
