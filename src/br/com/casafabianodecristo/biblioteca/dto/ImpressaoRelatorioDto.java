package br.com.casafabianodecristo.biblioteca.dto;

import java.time.LocalDate;

public class ImpressaoRelatorioDto {
	private int idRelatorio;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private Integer idUsuario;
	private Integer idClassificacao;
	
	public ImpressaoRelatorioDto(int idRelatorio, LocalDate dataInicial, LocalDate dataFinal, Integer idUsuario,
			Integer idClassificacao) {
		super();
		this.idRelatorio = idRelatorio;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.idUsuario = idUsuario;
		this.idClassificacao = idClassificacao;
	}

	public int getIdRelatorio() {
		return idRelatorio;
	}

	public void setIdRelatorio(int idRelatorio) {
		this.idRelatorio = idRelatorio;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdClassificacao() {
		return idClassificacao;
	}

	public void setIdClassificacao(Integer idClassificacao) {
		this.idClassificacao = idClassificacao;
	}
	
}