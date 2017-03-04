package br.com.casafabianodecristo.biblioteca.view;

import br.com.casafabianodecristo.biblioteca.dto.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class GerenciamentoRelatoriosController {
	@FXML
	private ComboBox<RelatorioDto> comboModeloRelatorio;
	
	@FXML
	private TextArea descricao;
	
	@FXML
	private DatePicker dataInicio;
	
	@FXML
	private DatePicker dataFim;
	
	@FXML
	private ComboBox<UsuarioDto> comboUsuario;
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private Button botaoGerar;
	
	@FXML
	private void initialize(){
		
	}	
}