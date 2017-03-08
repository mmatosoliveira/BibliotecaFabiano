package br.com.casafabianodecristo.biblioteca.view;

import java.util.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

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
	
	private List<String> impressoras = new ArrayList<String>();
	
	@FXML
	private void initialize(){
		botaoImpressoraNaoListada.setStyle("-fx-background-color: none");
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printer : printServices)
            impressoras.add(printer.getName());
        ObservableList<String> itens = FXCollections.observableList(impressoras);
        gridImpressoras.setItems(itens);
        colunaImpressorasDisponiveis.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().toString()));
	}
}
