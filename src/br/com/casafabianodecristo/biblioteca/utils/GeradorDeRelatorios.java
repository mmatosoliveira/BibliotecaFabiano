package br.com.casafabianodecristo.biblioteca.utils;

import java.util.List;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 *Classe responsável por exportar um relatório jasper em pdf
 *@author Matheus de Matos Oliveira
 */
@SuppressWarnings("deprecation")
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
		this.inicializarVariaveis();
		this.nomeArquivoJasper = nomeArquivoJasper;
		this.nomeCaminhoFinalRelatorio = nomeCaminhoFinalRelatorio;
	}
	
	public GeradorDeRelatorios(String nomeArquivoJasper) {
		this.inicializarVariaveis();
		this.nomeArquivoJasper = nomeArquivoJasper;
	}
	
	private void inicializarVariaveis(){
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = this.path + "br/com/casafabianodecristo/biblioteca/reports/";
	}
	
	/**
	 * Método que gera o relatório.
	 * @param <T>
	 * @param lista
	 * defaults to null 
	 */	
	public <T> JasperPrint gerar(List<T> lista) throws Exception	
	{
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + nomeArquivoJasper);
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));
		
		return print;
	}
	
	/**
	 * Método que gera o relatório em pdf e salva em um caminho especificado.
	 * @param <T>
	 * @param lista
	 * defaults to null 
	 */	
	public <T> void gerarPdf(List<T> lista) throws Exception	
	{
		JasperReport report = JasperCompileManager.compileReport(this.getPathToReportPackage() + nomeArquivoJasper);
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(lista));
 
		JasperExportManager.exportReportToPdfFile(print, nomeCaminhoFinalRelatorio);		
	}
	
	@SuppressWarnings("deprecation")
	public void imprimir(JasperPrint jp, String nomeImpressora) throws JRException {
	    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
	    // printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
	    printRequestAttributeSet.add(new Copies(1));

	    PrinterName printerName = new PrinterName(nomeImpressora, null); //gets printer 

	    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
	    printServiceAttributeSet.add(printerName);

	    JRPrintServiceExporter exporter = new JRPrintServiceExporter();

	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
	    exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
		exporter.exportReport();
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