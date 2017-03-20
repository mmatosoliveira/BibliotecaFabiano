package br.com.casafabianodecristo.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.dto.ReciboEmprestimoDto;
import br.com.casafabianodecristo.biblioteca.utils.GeradorDeRelatorios;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class GeradorReciboService {
	private ReciboEmprestimoDto dtoRecibo = new ReciboEmprestimoDto();
	private EmprestimoDto dtoEmprestimo;
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	public GeradorReciboService(EmprestimoDto dtoEmprestimo) {
		this.dtoEmprestimo = dtoEmprestimo;
	}

	public int gerarRecibo (){
		dtoRecibo.setNomeUsuario(dtoEmprestimo.getUsuario().toString());
		informarLivrosRecibo();
		dtoRecibo.setDataDevolucao(dtoEmprestimo.getDevolucaoPrevista());
		
		List<ReciboEmprestimoDto> lista = new ArrayList<>();
		lista.add(dtoRecibo);
		GeradorDeRelatorios gerador = new GeradorDeRelatorios("ReciboEmprestimo.jrxml");
		String nomeImpressora = "";
		try {
			nomeImpressora = servico.getParametrizacaoSistemaVigente().getNomeImpressoraPadrao();
		} catch (Exception e1) {
			return 3;
		}
		
		try {
			JasperPrint jp = gerador.gerar(lista);
			try{
				gerador.imprimir(jp, nomeImpressora);
			}
			catch(JRException ex){
				return 2;
			}
            
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
	
	private void informarLivrosRecibo(){
		int quantidadeLivros = dtoEmprestimo.getLivros().size();
		if(quantidadeLivros == 1){
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(null);
			dtoRecibo.setTerceiroLivro(null);
		}
		else if(quantidadeLivros == 2){
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(dtoEmprestimo.getLivros().get(1).getNomeConcatenadoLivro());
			dtoRecibo.setTerceiroLivro(null);
		}
		else{
			dtoRecibo.setPrimeiroLivro(dtoEmprestimo.getLivros().get(0).getNomeConcatenadoLivro());
			dtoRecibo.setSegundoLivro(dtoEmprestimo.getLivros().get(1).getNomeConcatenadoLivro());
			dtoRecibo.setTerceiroLivro(dtoEmprestimo.getLivros().get(2).getNomeConcatenadoLivro());
		}
	}

}
