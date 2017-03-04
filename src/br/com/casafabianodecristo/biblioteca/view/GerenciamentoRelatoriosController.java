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
	private void initialize(){
		ObservableList<RelatorioDto> itens = FXCollections.observableArrayList(service.getModelosRelatorios());
		comboModeloRelatorio.setItems(itens);
		
		
		comboModeloRelatorio.setOnAction((event) -> {
			RelatorioDto itemSelecionado = comboModeloRelatorio.getSelectionModel().getSelectedItem();
		    descricao.setText(itemSelecionado.getDescricao());
		    comportamentoComponentes(itemSelecionado);
		});
		
		botaoGerar.setOnAction((event) -> {
			System.out.println("caiu no evento");
			avisoCarregando.setText("Gerando relatório. Aguarde!");
			paneCarregando.setVisible(true);
			avisoCarregando.setVisible(true);
		});
	}	
	
	private void comportamentoComponentes(RelatorioDto itemSelecionado){
		if(itemSelecionado.getFlObrigaDatas() == 0 && itemSelecionado.getFlObrigaUsuario() == 0){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboUsuario.setDisable(true);
	    }
	    else if(itemSelecionado.getFlObrigaDatas() == 0){
	    	dataInicio.setDisable(true);
	    	dataFim.setDisable(true);
	    	comboUsuario.setDisable(false);
	    }
	    else if(itemSelecionado.getFlObrigaUsuario() == 0){
	    	dataInicio.setDisable(false);
	    	dataFim.setDisable(false);
	    	comboUsuario.setDisable(true);
	    }
	    else{
	    	dataInicio.setDisable(false);
	    	dataFim.setDisable(false);
	    	comboUsuario.setDisable(false);
	    }
	}
}