package br.com.casafabianodecristo.biblioteca.dbmanager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
//import br.com.casafabianodecristo.biblioteca.utils.Alertas;

public class DbManager {
	private static EntityManagerFactory emf;
	private static EntityManager        em;
	//private Alertas alerta = new Alertas();
	
	/*public <T> void getAll(T entity){
		createEntityManagerFactory();
			createEntityManager();
				
			closeEntityManager();
		closeEntityManagerFactory();
	}*/
	
	public static void createEntityManagerFactory() throws Exception {
		try{
			emf = Persistence.createEntityManagerFactory("BibliotecaFabiano2");
		}
		catch(Exception e){
			throw new Exception("Erro na criação do entity manager.");
		}
	}

	public static void closeEntityManagerFactory() {
		try{
			emf.close();
		}
		catch(Exception e){
			throw new RuntimeException("Erro na criação do entity manager.");
		}
	}
	
	public static void createEntityManager() throws Exception{
		try{
			em  = emf.createEntityManager();
		}
		catch(Exception  e){
			throw new Exception("Erro na criação do entity manager.");
		}
	}
	
	public static void closeEntityManager() {
		try{
			em.close();
		}
		catch(Exception e){
			throw new RuntimeException("Erro na criação do entity manager.");
		}
	}


}
