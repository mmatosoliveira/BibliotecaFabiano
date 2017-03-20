package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;
import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("rawtypes")
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
	
	@FXML private Accordion accordion;
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private Alertas alerta = new Alertas();
	
	private Principal principal = new Principal();
	
	private Task renovarEmprestimo;
	
	private Task aplicarFiltro;
	
	public Task taskRenovarEmprestimo(){
        return new Task() {
            @Override
            protected Boolean call() throws Exception {
            	return servico.renovarEmprestimo(emprestimos.getSelectionModel().getSelectedItem().getId());
            }
            
			@Override
    		protected void succeeded() {
            	boolean result = (boolean) getValue();
            	if(result){
            		showMaskerPane(false, "Consultando... Aguarde!");
            		alerta.notificacaoSucesso("Renovar empréstimo", "Sucesso ao renovar empréstimo.");
            		atualizarGrid(null);
            	}
            	else{
            		showMaskerPane(false, "Consultando... Aguarde!");
            		alerta.alertaAviso("Renovar empréstimo", "Ocorreu um erro ao tentar renovar o empréstimo.\nTente novamente mais tarde");
            	}
    		}
        };
    }
	
	public Task taskAplicarFiltro() {
        return new Task() {
            @Override
            protected List<EmprestimoDto> call() throws Exception {
            	String nome = nomeUsuario.getText();
            	if(nome.equals(null) || nome.equals("")){
            		alerta.notificacaoErro("Consultar empréstimos", "É obrigatório informar pelo menos o nome do usuário no filtro.");
            		showMaskerPane(false, "Consultando... Aguarde!");
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
            		showMaskerPane(false, "Consultando... Aguarde!");
            		alerta.alertaAviso("Consultar empréstimos", "O usuário informado não possui registros de empréstimos.");
            		atualizarGrid(result);
            	}
            	else{
            		showMaskerPane(false, "Consultando... Aguarde!");
            		atualizarGrid(result);
            	}
    		}
        };
    }
	
	private void atualizarGrid(List<EmprestimoDto> result){
		if(result == null)
			result = servico.getEmprestimosPorNome(nomeUsuario.getText(), checkAtrasados.isSelected());
		
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
		showMaskerPane(true, "Consultando... Aguarde!");
		aplicarFiltro = taskAplicarFiltro();
		Thread t = new Thread(aplicarFiltro);
		t.setDaemon(true);
		t.start();	
	}
	
	@FXML private void renovarEmprestimo(){
		EmprestimoDto itemSelecionado = emprestimos.getSelectionModel().getSelectedItem();
		
		if(itemSelecionado == null)
			alerta.notificacaoAlerta("Renovar empréstimo", "É obrigatório selecionar um empréstimo para renovar.");
		else if (itemSelecionado.getDataDevolucaoEfetiva() != null)
			alerta.notificacaoAlerta("Renovar empréstimo", "É obrigatório selecionar um empréstimo que não tenha sido devolvido para renovar.");
		else if (itemSelecionado.getAtrasado().equals("Sim"))
			alerta.notificacaoAlerta("Renovar empréstimo", "Não é permitido renovar um empréstimo que esteja atrasado.");
		else{
			showMaskerPane(true, "Renovando empréstimo... Aguarde!");
			renovarEmprestimo = taskRenovarEmprestimo();
			Thread t = new Thread(renovarEmprestimo);
			t.setDaemon(true);
			t.start();	
		}
	}
	
	private void showMaskerPane(boolean visibility, String text){
		avisoCarregando.setText(text);
		paneCarregando.setVisible(visibility);
		avisoCarregando.setVisible(visibility);
	}
	
	@FXML
	private void initialize(){
		botaoAplicarFiltro.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoAdicionar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoAdicionar.setOnAction((e) -> principal.carregarEmprestimoLivro());
		renovar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		imprimirRecibo.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestimoController.class.getResource("style.css").toExternalForm());
		paneFiltro.setCollapsible(false);
		accordion.setExpandedPane(paneFiltro);
	}
}
