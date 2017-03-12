package br.com.casafabianodecristo.biblioteca.view;

import javafx.fxml.*;
import javafx.scene.control.*;

public class RealizarBackupController {
	@FXML	private RadioButton radioEncerrarLivres;
	
	@FXML	private RadioButton radioEncerrarLivresESistema;
	
	@FXML	private Button botaoCancelar;
	
	@FXML	private Button iniciarBackup;
	
	@FXML	private Button restaurarBackup;
	
	@FXML	private TextField caminhoArquivoBackup;
	
	@FXML	private Button selecionarArquivo;
	
	@FXML	private ProgressBar progresso;
	
	@FXML
	private void initialize(){
		
	}
}