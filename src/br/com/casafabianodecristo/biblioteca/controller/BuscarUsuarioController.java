package br.com.casafabianodecristo.biblioteca.controller;

import java.util.List;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.model.Usuario;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BuscarUsuarioController {
	@FXML
	private Button botaoFechar;
	
	@FXML
	private Button botaoEditarDados;
	
	@FXML
	private ImageView iconePesquisar;
	
	@FXML
	private TextField campoNome;
	
	@FXML
	private TableView <Usuario> usuarios;
	
	@FXML
	private TableColumn<Usuario, String> colunaNome;
	
	@FXML
	private TableColumn<Usuario, String> colunaSobrenome;
	
	@FXML
	private TableColumn<Usuario, String> colunaAdministrador;
	
	Alertas alerta = new Alertas();
	
	private Principal principal;
	
	BibliotecaAppService servico = new BibliotecaAppService();
	
	private void atualizarGrid(){
		colunaNome.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getNomeUsuario()));
		colunaSobrenome.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getSobrenome()));
		colunaAdministrador.setCellValueFactory(x -> new ReadOnlyStringWrapper(x.getValue().getFlAdministradorString()));
	}
	
	private void realizarConsulta(){
		List<Usuario> resultadoPesquisa;
    	servico.createEntityManagerFactory();
    		resultadoPesquisa = servico.getUsuarios(campoNome.getText());
    	servico.closeEntityManagerFactory();
    	ObservableList<Usuario> itens = FXCollections.observableList(resultadoPesquisa);
    	usuarios.setItems(itens);
    	atualizarGrid();
	}
	
	@FXML
	private void initialize(){
		botaoFechar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Stage stage = (Stage) botaoFechar.getScene().getWindow();
	            stage.close();
            }            
        });
		
		campoNome.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					realizarConsulta();
				}				
			}
		}));
		
		iconePesquisar.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
            public void handle(MouseEvent event) {
            	realizarConsulta();
            }            
        });
		
		botaoEditarDados.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Usuario user = usuarios.getSelectionModel().getSelectedItem();
            	//System.out.println(user);
            	if (user == null)
            		alerta.notificacaoErro("Editar dados do usu�rio", "Voc� deve selecionar um registro da grid para realizar essa opera��o.");
            	else if (user.getFlAdministrador() == 1)
            		alerta.notificacaoErro("Editar dados do usu�rio", "N�o � permitido alterar os dados de um Administrador!");
            	else	
            		principal.carregarDadosUsuario(user);
            }            
        });
	}
	
	public void setPrincipal(Principal principal){
		this.principal = principal;
	}
}