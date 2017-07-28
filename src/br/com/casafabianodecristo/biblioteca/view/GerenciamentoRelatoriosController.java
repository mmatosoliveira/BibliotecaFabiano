package br.com.casafabianodecristo.biblioteca.view;

import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.textfield.TextFields;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.*;
import br.com.casafabianodecristo.biblioteca.service.RelatorioService;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.collections.*;
import javafx.concurrent.Task;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
	
	private RelatorioService service = new RelatorioService();
	
	private BibliotecaAppService appService = new BibliotecaAppService();
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando = new BorderPane();
	
	@FXML
	private ComboBox<ClassificacaoDto> comboClassificacao;
	
	private ObservableList<UsuarioDto> listaUsuarios = null;
	
	private Alertas alerta = new Alertas();
	
	private ObservableList<ClassificacaoDto> listaClassificacoes = null;
	
	@SuppressWarnings("rawtypes")
	private Task gerarRelatorio;
	
	@FXML
	private void initialize(){
		ObservableList<RelatorioDto> itens = FXCollections.observableArrayList(service.getModelosRelatorios());
		comboModeloRelatorio.setItems(itens);
		
		comboUsuario.setEditable(true);
		
		comboModeloRelatorio.setOnAction((event) -> {
			RelatorioDto itemSelecionado = comboModeloRelatorio.getSelectionModel().getSelectedItem();
		    descricao.setText(itemSelecionado.getDescricao());
		    comportamentoComponentes(itemSelecionado);
		});
		
		botaoGerar.setOnAction((event) -> {
			avisoCarregando.setText("Gerando relatório... Aguarde!");
			alterarSituacaoRelatorio(true);
			
			gerarRelatorio = taskGerarRelatorio();
			Thread t = new Thread(gerarRelatorio);
			t.setDaemon(true);
			t.start();
		});
	}	
	
	private void alterarSituacaoRelatorio(boolean state){
		paneCarregando.setVisible(state);
		avisoCarregando.setVisible(state);
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskGerarRelatorio() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
            	System.out.println("Caiu no call");
            	ImpressaoRelatorioDto dto = new ImpressaoRelatorioDto
    					(comboModeloRelatorio.getSelectionModel().getSelectedItem().getId(),
    					dataInicio.getValue(),
    					dataFim.getValue(),
    					comboUsuario.getSelectionModel().getSelectedItem().getId(),
    					comboClassificacao.getSelectionModel().getSelectedItem().getId()
    					);
    			return service.gerarRelatorio(dto);
            }
            
			@Override
    		protected void succeeded() {
            	
            	Object result = (Object) getValue();
            	if (result != null){
            		alerta.notificacaoSucesso("Geração de relatório", "Sucesso ao gerar relatório!");
            		alterarSituacaoRelatorio(false);                   
            	}
            	else{
            		alterarSituacaoRelatorio(false);  
            		alerta.notificacaoErro("Geração de relatórios", "Ocorreu um erro na geração do relatório.\nPor favor tente novamente mais tarde.");
            	}
            	
    		}
        };
    }
	
	private void getUsuarios(){
		listaUsuarios = FXCollections.observableArrayList(appService.getUsuarios(""));
		TextFields.bindAutoCompletion(comboUsuario.getEditor(), listaUsuarios);
		comboUsuario.setItems(listaUsuarios);
	}
	
	private void getClassificacoes(){
		listaClassificacoes = FXCollections.observableArrayList(appService.getClassificacoes());
		comboClassificacao.setItems(listaClassificacoes);
	}
	
	private void comportamentoComponentes(RelatorioDto itemSelecionado){
		if(itemSelecionado.getId() == -1){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboUsuario.setDisable(true);
	    	comboClassificacao.setDisable(false);
	    	if(listaClassificacoes == null)
	    		getClassificacoes();
	    }
		else if(itemSelecionado.getFlObrigaDatas() == 0 && itemSelecionado.getFlObrigaUsuario() == 0 && itemSelecionado.getFlObrigaClassificacaoLivro() == 0){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboUsuario.setDisable(true);
	    	comboClassificacao.setDisable(true);
	    }
	    else if(itemSelecionado.getFlObrigaDatas() == 1){
	    	dataInicio.setDisable(false);
	    	dataFim.setDisable(false);
	    	comboClassificacao.setDisable(true);
	    	comboUsuario.setDisable(true);
	    }
	    else if(itemSelecionado.getFlObrigaUsuario() == 1){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboClassificacao.setDisable(true);
	    	comboUsuario.setDisable(false);
	    	if(listaUsuarios == null)
	    		getUsuarios();
	    }
	    else if(itemSelecionado.getFlObrigaClassificacaoLivro() == 1){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboUsuario.setDisable(true);
	    	comboClassificacao.setDisable(false);
	    	if(listaClassificacoes == null)
	    		getClassificacoes();
	    }
	    else{
	    	dataInicio.setDisable(false);
	    	dataFim.setDisable(false);
	    	comboUsuario.setDisable(false);
	    	comboClassificacao.setDisable(false);
	    	if(listaUsuarios == null)
	    		getUsuarios();
	    	if(listaClassificacoes == null)
	    		getClassificacoes();
	    }
	}
}