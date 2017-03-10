package br.com.casafabianodecristo.biblioteca.view;

import java.io.IOException;
import java.util.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ConfigurarImpressoraPadraoController {
	@FXML
	private TableView<String> gridImpressoras;
	
	@FXML
	private TableColumn<String, String> colunaImpressorasDisponiveis;
	
	@FXML
	private Button botaoImpressoraNaoListada;
	
	@FXML
	private Button selecionarPadrao;
	
	@FXML
	private Button cancelar;
	
	Alertas alerta = new Alertas();
	
	private List<String> impressoras = new ArrayList<String>();
	
	@FXML
	private void initialize(){
		botaoImpressoraNaoListada.setStyle("-fx-background-color: none");
		botaoImpressoraNaoListada.setOnAction(e -> abrirProgramaExterno());
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printer : printServices)
            impressoras.add(printer.getName());
        ObservableList<String> itens = FXCollections.observableList(impressoras);
        gridImpressoras.setItems(itens);
        colunaImpressorasDisponiveis.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().toString()));
	}
	
	private void abrirProgramaExterno(){
		try {
			Process process = new ProcessBuilder("cmd.exe", "/c", "rundll32 printui.dll,PrintUIEntry /il").start();
			if(process.getErrorStream() != null && !process.isAlive()){
				alerta.alertaErro("Abrir aplicação externa", "Ocorreu um erro ao tentar abrir a aplicação Adicionar uma impressora.\nVocê pode tentar adicionar a impressora manualmente indo em:\nPainel de controle > Dispositivos e impressoras > Adicionar uma impressora.");
			}
		} catch (IOException e) {
			alerta.alertaErro("Abrir aplicação externa", "Ocorreu um erro ao tentar abrir a aplicação Adicionar uma impressora.\nVocê pode tentar adicionar a impressora manualmente indo em:\nPainel de controle > Dispositivos e impressoras > Adicionar uma impressora.");
		}
	}
}
