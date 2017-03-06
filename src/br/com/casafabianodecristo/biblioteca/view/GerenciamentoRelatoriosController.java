package br.com.casafabianodecristo.biblioteca.view;

import org.controlsfx.control.MaskerPane;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

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
	
	private BibliotecaAppService service = new BibliotecaAppService();
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando = new BorderPane();
	
	@FXML
	private ComboBox<ClassificacaoDto> comboClassificacao;
	
	private ObservableList<UsuarioDto> listaUsuarios = null;
	
	private ObservableList<ClassificacaoDto> listaClassificacoes = null;
	
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
			avisoCarregando.setText("Gerando relatório. Aguarde!");
			paneCarregando.setVisible(true);
			avisoCarregando.setVisible(true);
		});
	}	
	
	private void getUsuarios(){
		listaUsuarios = FXCollections.observableArrayList(service.getUsuarios(""));
		comboUsuario.setItems(listaUsuarios);
	}
	
	private void getClassificacoes(){
		listaClassificacoes = FXCollections.observableArrayList(service.getClassificacoes());
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