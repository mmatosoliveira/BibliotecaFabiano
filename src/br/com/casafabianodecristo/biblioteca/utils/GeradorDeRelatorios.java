package br.com.casafabianodecristo.biblioteca.utils;

import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *Classe responsável por exportar um relatório jasper em pdf
 *@author Matheus de Matos Oliveira
 */
public class GeradorDeRelatorios 
{
	private String path;
	
	private String pathToReportPackage;
	
	/**
	 * Nome do arquivo .jrxml que deve ser compilado
	 */
	private String nomeArquivoJasper;
	
	/**
	 * Nome e caminho do arquivo pdf a ser exportado 
	 */
	private String nomeCaminhoFinalRelatorio;
	
	public GeradorDeRelatorios(String nomeArquivoJasper, String nomeCaminhoFinalRelatorio) {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "br/com/casafabianodecristo/biblioteca/reports/";
		this.nomeArquivoJasper = nomeArquivoJasper;
		this.nomeCaminhoFinalRelatorio = nomeCaminhoFinalRelatorio;
	}
	
	/**
	 * Método que gera o relatório em pdf.
	 * @param <T>
	 * @param lista
	 * defaults to null 
	 */	
	public <T> void gerar(List<T> lista) throws Exception	
	{
		System.out.println(path);
		System.out.println(this.getPathToReportPackage() + nomeArquivoJasper);
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + nomeArquivoJasper);
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));
 
		JasperExportManager.exportReportToPdfFile(print, nomeCaminhoFinalRelatorio);		
	}
 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}
	
	public String getPath() {
		return this.path;
	}


	public String getNomeArquivoJasper() {
		return nomeArquivoJasper;
	}


	public void setNomeArquivoJasper(String nomeArquivoJasper) {
		this.nomeArquivoJasper = nomeArquivoJasper;
	}


	public String getNomeCaminhoFinalRelatorio() {
		return nomeCaminhoFinalRelatorio;
	}


	public void setNomeCaminhoFinalRelatorio(String nomeCaminhoFinalRelatorio) {
		this.nomeCaminhoFinalRelatorio = nomeCaminhoFinalRelatorio;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public void setPathToReportPackage(String pathToReportPackage) {
		this.pathToReportPackage = pathToReportPackage;
	}
	
	
}