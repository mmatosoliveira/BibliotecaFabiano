package br.com.casafabianodecristo.biblioteca.view;

import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class EmprestimoController {
	@FXML private TableView<EmprestimoDto> emprestimos;
	
	@FXML private TableColumn<EmprestimoDto, String> colunaUsuario;
	
	@FXML private TableColumn<EmprestimoDto, String> colunaDataPrevista;
	
	@FXML private TableColumn<EmprestimoDto, String> colunaDataEfetiva;

	@FXML private TableColumn<EmprestimoDto, String> colunaAtrasado;
	
	@FXML private TitledPane paneFiltro;
	
	@FXML private TextField nomeUsuario;
	
	@FXML private CheckBox checkAtrasados;
	
	@FXML private Button botaoAplicarFiltro;
	
	@FXML private Button botaoAdicionar;
	
	@FXML private Button renovar;
	
	@FXML private Button imprimirRecibo;
	
	@FXML private Button botaoFechar;
	
	@FXML private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML private BorderPane paneCarregando;
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private void atualizarGrid(){
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
	
	@FXML
	private void initialize(){
		botaoAplicarFiltro.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoAdicionar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		renovar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		imprimirRecibo.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		paneFiltro.setExpanded(true);
		paneFiltro.setCollapsible(false);
		
	}
}
