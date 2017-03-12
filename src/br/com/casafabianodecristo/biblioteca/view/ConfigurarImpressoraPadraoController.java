package br.com.casafabianodecristo.biblioteca.view;

import java.io.IOException;
import java.util.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
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
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando;
	
	@FXML
	private AnchorPane anchorPane = new AnchorPane();	
	
	Alertas alerta = new Alertas();
	
	private List<String> impressoras = new ArrayList<String>();
	
	BibliotecaAppService service = new BibliotecaAppService();
	
	private Task taskSelecionarImpressora;
	
	@FXML
	private void initialize(){
		botaoImpressoraNaoListada.setStyle("-fx-background-color: none");
		botaoImpressoraNaoListada.setOnAction(e -> abrirProgramaExterno(e));
		
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printer : printServices)
            impressoras.add(printer.getName());
        ObservableList<String> itens = FXCollections.observableList(impressoras);
        gridImpressoras.setItems(itens);
        
        colunaImpressorasDisponiveis.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().toString()));
        
        cancelar.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				if(alerta.alertaConfirmacaoSairTelaCadastro().get() == ButtonType.OK){
					Stage stage = (Stage) cancelar.getScene().getWindow();
	            	stage.close();	
				}
			}
		});
        
        selecionarPadrao.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            public void handle(ActionEvent event) {
				avisoCarregando.setText("Selecionando... Aguarde!");
				avisoCarregando.setVisible(true);
				paneCarregando.setVisible(true);
				
				taskSelecionarImpressora = taskSelecionarImpressora();
    			Thread t = new Thread(taskSelecionarImpressora);
    			t.setDaemon(true);
    			t.start();
			}
		});
	}
	
	public Task taskSelecionarImpressora() {
        return new Task() {
            @Override
            protected Integer call() throws Exception {
            	String nomeImpressora = gridImpressoras.getSelectionModel().getSelectedItem();
            	//System.out.println(nomeImpressora);
            	//System.out.println(cancelar.getScene().getUserData());
            	//int idUsuario = (int) cancelar.getScene().getUserData();
        		return service.salvarParametrizacao(nomeImpressora);
            }
            
            @Override
    		protected void succeeded() {
            	int result = (int) getValue();
            	if (result == 0){
            		alerta.notificacaoErro("Selecionar impressora de recibos padrão", "Ocorreu um erro durante a execução da ação solicitada, tente novamente mais tarde!\nSe o erro persistir, contate o administrador do sistema");
    				avisoCarregando.setVisible(false);
    				paneCarregando.setVisible(false);
            	}
            	else if (result == 1){
            		Stage stage = (Stage) cancelar.getScene().getWindow();
    	            stage.close();
    	            alerta.notificacaoSucessoSalvarDados("Selecionar impressora de recibos padrão");
            	}
            		
    		}
        };
    }
	
	private void abrirProgramaExterno(Event event){
		Alertas alerta = new Alertas();
		alerta.notificacaoSucesso("Adicionar impressora externa", "Após adicionar a impressora, clique no botão atualizar da grid para que ela seja exibida.\nQualquer dúvida, consulte o item Ajuda, no menu da aplicação.");
		
			try {
				Process process = new ProcessBuilder("cmd.exe", "/c", "rundll32 printui.dll,PrintUIEntry /il").start();
				if(process.getErrorStream() != null && !process.isAlive()){
					alerta.alertaErro("Abrir aplicação externa", "Ocorreu um erro ao tentar abrir a aplicação Adicionar uma impressora.\nVocê pode tentar adicionar a impressora manualmente indo em:\nPainel de controle > Dispositivos e impressoras > Adicionar uma impressora.");
				}
			} catch (IOException ex) {
				alerta.alertaErro("Abrir aplicação externa", "Ocorreu um erro ao tentar abrir a aplicação Adicionar uma impressora.\nVocê pode tentar adicionar a impressora manualmente indo em:\nPainel de controle > Dispositivos e impressoras > Adicionar uma impressora.");
			}
		}
	
}
