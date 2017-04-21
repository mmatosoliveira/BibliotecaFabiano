package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BuscarUsuarioController {
	@FXML
	private Button botaoFechar;
	
	@FXML
	private Button botaoEditar;
	
	@FXML
	private Button botaoAdicionar;
	
	@FXML
	private Button botaoRemover;
	
	@FXML
	private TextField campoNome;
	
	@FXML
	private TableView <UsuarioDto> usuarios;
	
	@FXML
	private TableColumn<UsuarioDto, String> colunaNome;
	
	@FXML
	private TableColumn<UsuarioDto, String> colunaSobrenome;
	
	@FXML
	private TableColumn<UsuarioDto, String> colunaAdministrador;
	
	@FXML
	private Button botaoAplicarFiltro;
	
	Alertas alerta = new Alertas();
	
	private Principal principal = new Principal();
	
	BibliotecaAppService servico = new BibliotecaAppService();
	
	private void atualizarGrid(){
		colunaNome.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeCompleto()));
		colunaAdministrador.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlAdministradorString()));
	}
	
	private void realizarConsulta(String filtro){
		List<UsuarioDto> resultadoPesquisa = servico.getUsuarios(filtro);
		ObservableList<UsuarioDto> itens = FXCollections.observableList(resultadoPesquisa);
    	usuarios.setItems(itens);
    	atualizarGrid();
	}
	
	@FXML
	private void initialize(){
		botaoAdicionar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoEditar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoRemover.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoFechar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		botaoAplicarFiltro.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());
		
		realizarConsulta("");
		
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
            	stage.fireEvent(new WindowEvent(
            	        stage,
            	        WindowEvent.WINDOW_CLOSE_REQUEST
            	    ));
            }            
        });
		
		campoNome.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					aplicarFiltro();
				}				
			}
		}));
		
		botaoAplicarFiltro.setOnMousePressed(e -> aplicarFiltro());
		
		botaoEditar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	UsuarioDto user = usuarios.getSelectionModel().getSelectedItem();
            	if (user == null)
            		alerta.notificacaoAlerta("Editar dados do usuário", "Você deve selecionar um usuário para editar.");
            	else	{
            		principal.carregarEditarDadosUsuario(user, usuarios);
            	}
            }            
        });
		
		botaoAdicionar.setOnAction((e) -> principal.carregarTelaCadastro("CadastrarUsuario", "Cadastrar usuário", true, false, usuarios));
	}
	
	private void aplicarFiltro(){
		String nomeUsuario = campoNome.getText();
		realizarConsulta(nomeUsuario);
	}
	
	public void setPrincipal(Principal principal){
		this.principal = principal;
	}
}