package br.com.casafabianodecristo.biblioteca.view;

import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EmprestimoController {
	@FXML
	private TableView<EmprestimoDto> emprestimos;
	
	@FXML
	private TableColumn<EmprestimoDto, String> colunaUsuario;
	
	@FXML
	private TableColumn<EmprestimoDto, String> colunaDataPrevista;
	
	@FXML
	private TableColumn<EmprestimoDto, String> colunaDataEfetiva;

	@FXML
	private TableColumn<EmprestimoDto, String> colunaAtrasado;
	
	@FXML
	private void initialize(){
		colunaUsuario.setCellValueFactory(x -> new ReadOnlyStringWrapper(
												x.getValue()
												.getUsuario()
												.getNomeUsuario() + " " + 
												x.getValue()
												.getUsuario()
												.getSobrenome()));
		colunaDataPrevista.setCellValueFactory(x -> new ReadOnlyStringWrapper(
													x.getValue()
													.getDevolucaoPrevista()));
		colunaDataEfetiva.setCellValueFactory(x -> new ReadOnlyStringWrapper(
													x.getValue()
													.getDevolucaoEfetiva()));
		colunaAtrasado.setCellValueFactory(x -> new ReadOnlyStringWrapper(
												x.getValue()
												.getAtrasado()));
	}
}
