package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;

import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.utils.RGBConverter;
import br.com.casafabianodecristo.biblioteca.validator.ClassificacaoValidator;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CadastrarClassificacaoController {
	@FXML
	private Button botaoSalvar;
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private TextField descricao;
	
	@FXML
	private ColorPicker escolherCor;
	
	@FXML
	private Label lblTitutlo;
	
	@FXML
	private Label lblId;
	
	private BibliotecaAppService appService = new BibliotecaAppService();
	
	Alertas alerta = new Alertas();
	
	GerenciarClassificacoesController c = new GerenciarClassificacoesController();
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando;
	
	@SuppressWarnings("rawtypes")
	private Task cadastrarClassificacao;
	
	private TableView<ClassificacaoDto> tabela;
	
	private int id = 0;
	
	boolean isEdit = false;
	
	public CadastrarClassificacaoController (){}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize(){	
		botaoSalvar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	List<Object> dados = (List<Object>) botaoCancelar.getScene().getRoot().getUserData();
				tabela = (TableView<ClassificacaoDto>) dados.get(0);
				id = (Integer) dados.get(1);
				System.out.println("Id: " + id);
				System.out.println("IdTabela: " + tabela.getId());
				
				isEdit = (id == 0) ? false : true;
	    		alterarEstadoCampos(true);            		
		    	if(validarCampos() && ClassificacaoValidator.validarValoresRepetidos(descricao.getText(), RGBConverter.toRGBCode(escolherCor.getValue()), isEdit)){
		    		cadastrarClassificacao = taskCadastrarClassificacaoLivro();
        			Thread t = new Thread(cadastrarClassificacao);
        			t.setDaemon(true);
        			t.start();
		    	}
		    	else{
		    		alterarEstadoCampos(false);
		    	}
		    }
		});
			
		botaoCancelar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	if (alerta.alertaConfirmacaoSairTelaCadastro().get() == ButtonType.OK){
		    		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
		    		stage.close();	    	
		    	}		    	
		    }
		});
	}
	
	private void alterarEstadoCampos(boolean estado){
		avisoCarregando.setText("Salvando... Aguarde!");
		avisoCarregando.setVisible(estado);
		paneCarregando.setVisible(estado);
	}
	
	private boolean validarCampos(){
		 boolean result;		 
		 if (descricao.getText() == null || descricao.getText().equals(""))
		 {
			 result = false;
			 descricao.setStyle("-fx-background-color: #ff7c7c;");
			 alerta.notificacaoErro("Cadastrar classificação", "É obrigatório informar uma descrição para a classificação de livros.");
		 }
		 else{
			 result = true;
			 descricao.setStyle("-fx-background-color: white;");
			 escolherCor.setStyle("-fx-background-color: white;");
		 }
		 return result;
	 }
	
	@SuppressWarnings("rawtypes")
	public Task taskCadastrarClassificacaoLivro() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		return cadastrarClassificacao();
            }
            
			@Override
    		protected void succeeded() {
            	
            	boolean result = (boolean) getValue();
            	if (result){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
            		setarItensTabela();
                    stage.close();                    
                    alerta.notificacaoSucessoSalvarDados("Cadastrar classificação"); 
            	}
            	else if (result && id != 0){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
                    stage.close();
                    setarItensTabela();
                    alerta.notificacaoSucessoSalvarDados("Editar classificação"); 
            	}
    		}
        };
    }
	
	private void setarItensTabela(){
		if(tabela != null){
			List<ClassificacaoDto> lista = appService.getClassificacoes();
			tabela.setItems(FXCollections.observableArrayList(lista));
		}
	}
	
	private boolean cadastrarClassificacao(){
		int result;
		
		if(!isEdit){
			ClassificacaoDto dto = new ClassificacaoDto(0, descricao.getText(), RGBConverter.toRGBCode(escolherCor.getValue()));
    		result = appService.cadastrarClassificacao(dto);
		}
		else {
			ClassificacaoDto dto = new ClassificacaoDto(id, descricao.getText(), RGBConverter.toRGBCode(escolherCor.getValue()));
			result = appService.atualizarClassificacao(dto);
		}
		if(result == 0){
			return true;
		}
		else if(result == 1){
			alerta.notificacaoErro("Classificação de livros", "Ocorreu um erro ao tentar salvar a classificação, tente novamente mais tarde.");
			return false;
		}
		else if(result != 2){
			alerta.notificacaoErro("Classificação de livros", "Ops, algo deu errado. Tente novamente mais tarde, se o problema persistir contate o Administrador do sistema.");
			return false;
		}
		return true;
	}
}
