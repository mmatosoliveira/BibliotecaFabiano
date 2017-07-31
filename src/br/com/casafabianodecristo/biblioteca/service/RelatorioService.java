package br.com.casafabianodecristo.biblioteca.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;

import br.com.casafabianodecristo.biblioteca.dto.ImpressaoRelatorioDto;
import br.com.casafabianodecristo.biblioteca.dto.RelatorioDto;
import br.com.casafabianodecristo.biblioteca.model.Relatorio;
import br.com.casafabianodecristo.biblioteca.utils.GeradorDeRelatorios;

public class RelatorioService {
	private EntityManagerFactory emf;
	private EntityManager        em;
	private GeradorDeRelatorios gerador;
	
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
	
	public RelatorioService(){}
	
	public Object gerarRelatorio(ImpressaoRelatorioDto dto){
		String user = System.getProperty("user.home");
		boolean result = new File(user+"\\Documents\\Livres\\Relatórios").mkdirs();
		System.out.println("resultado: " + result);
		System.out.println("resultado1: " + user+"\\Documents\\Livres\\Relatórios");
		System.out.println(dto.getIdRelatorio());
		try {
			OutputStream saida;
			switch(dto.getIdClassificacao()){
				case(-1):
					 gerador = new GeradorDeRelatorios("TodosOsLivros.jrxml");
					 saida = new FileOutputStream(user+"\\Documents\\Livres\\Relatórios\\TodosLivros.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-2):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-3):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-4):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-5):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-6):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-7):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-8):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-9):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
				case (-10):
					gerador = new GeradorDeRelatorios("EmprestimosPorUsuario.jrxml");
					saida = new FileOutputStream("EmprestimosPorUsuario.pdf");
					return gerador.gerarPdf(new HashMap<String,Object>(), saida);
			}
		}catch (FileNotFoundException e) {
			System.out.println("Erro na geração do relatório");
		}
		return null;
	}
	
	public List<RelatorioDto> getModelosRelatorios(){
		List<RelatorioDto> relatorios = new ArrayList<RelatorioDto>();
		ModelMapper mapper = new ModelMapper();
		List<Relatorio> tmp = null;
		createEntityManagerFactory();
			createEntityManager();
				TypedQuery<Relatorio> query;
				query = em.createQuery("select r from Relatorio r", Relatorio.class);
				try{
					tmp = query.getResultList();
				}
				catch(NoResultException e){}
			closeEntityManager();
		closeEntityManagerFactory();
		for(Relatorio item : tmp){
			relatorios.add(mapper.map(item, RelatorioDto.class));
		}
		return relatorios;
	}
}
