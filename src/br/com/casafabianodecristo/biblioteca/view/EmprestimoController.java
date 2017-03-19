package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;
import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
	
	private Alertas alerta = new Alertas();
	
	@SuppressWarnings("rawtypes")
	private Task aplicarFiltro;
	
	@SuppressWarnings("rawtypes")
	public Task taskAplicarFiltro() {
        return new Task() {
            @Override
            protected List<EmprestimoDto> call() throws Exception {
            	String nome = nomeUsuario.getText();
            	if(nome.equals(null) || nome.equals("")){
            		alerta.notificacaoErro("Consultar empréstimos", "É obrigatório informar pelo menos o nome do usuário no filtro.");
            		showMaskerPane(false);
            		super.failed();
            		return null;
            	}
            	else {
            		return servico.getEmprestimosPorNome(nome, checkAtrasados.isSelected());
            	}
            }
            
            @SuppressWarnings("unchecked")
			@Override
    		protected void succeeded() {
            	List<EmprestimoDto> result = (List<EmprestimoDto>) getValue();
            	
            	if(result.equals(null) || result.isEmpty()){
            		alerta.alertaAviso("Consultar empréstimos", "O usuário informado não possui empréstimos pendentes.");
            		showMaskerPane(false);
            	}
            	else{
            		showMaskerPane(false);
            		atualizarGrid(result);
            	}
            		
    		}
        };
    }
	
	private void atualizarGrid(List<EmprestimoDto> result){
		ObservableList<EmprestimoDto> itens = FXCollections.observableList(result);
		emprestimos.setItems(itens);
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
	
	@FXML private void aplicarFiltro(){
		showMaskerPane(true);
		aplicarFiltro = taskAplicarFiltro();
		Thread t = new Thread(aplicarFiltro);
		t.setDaemon(true);
		t.start();	
	}
	
	private void showMaskerPane(boolean visibility){
		avisoCarregando.setText("Consultando... Aguarde!");
		paneCarregando.setVisible(visibility);
		avisoCarregando.setVisible(visibility);
	}
	
	@FXML
	private void initialize(){
		botaoAplicarFiltro.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoAdicionar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		renovar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		imprimirRecibo.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		paneFiltro.setExpanded(true);
	}
}
