package br.com.casafabianodecristo.biblioteca.view;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.Toolkit;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.InicialDto;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.LoginInterfaceValidator;
import br.com.casafabianodecristo.biblioteca.utils.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

public class LoginController {
	@FXML
	private TextField nomeUsuario;
	
	@FXML
	private PasswordField senha;
	
	@FXML
	private Button botaoLogar;
	
	@FXML
	private Label labelDicaSenha;
	
	@FXML
	private ProgressIndicator indicador;
		
	private Principal principal;
	
	private Alertas alerta = new Alertas();
	
	int tentativas = 0;
	
    @SuppressWarnings("rawtypes")
	private Task logar;
    
    private boolean logado = false;
    
    private BibliotecaAppService appService = new BibliotecaAppService();
    
    private String dicaSenha;
    
    private InicialDto dto = new InicialDto(); 
    
    @FXML
    private ImageView iconeAttention = new ImageView();
	
	public LoginController() {}
	
	@FXML
	private void initialize(){
	botaoLogar.getStylesheets().add(EmprestarLivroController.class.getResource("style.css").toExternalForm());		
		senha.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				//boolean capsLigado = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);
				if (event.getCode() == KeyCode.ENTER){
					validarLogin();
				}
			}
		}));
		
		nomeUsuario.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					validarLogin();
				}				
			}
		}));
		
		botaoLogar.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER){
					validarLogin();
				}				
			}
		}));
		
		botaoLogar.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	validarLogin();		    	
		    }
		});
	}
	
	public void setPrincipal (Principal principal){
		this.principal = principal;
	}
	
	public void validarLogin() {			 		
		indicador.setVisible(true);
		nomeUsuario.setDisable(true);
		senha.setDisable(true);
		botaoLogar.setDisable(true);
		
		if (LoginInterfaceValidator.validarCamposObrigatorios(nomeUsuario, senha)){
			logar = taskLogar();
			Thread t = new Thread(logar);
			t.setDaemon(true);
			t.start();
		}
		else {
			indicador.setVisible(false);
			nomeUsuario.setDisable(false);
			senha.setDisable(false);
			botaoLogar.setDisable(false);
		}
	}
	
	private boolean logar(){
		try {			
			String password = ConvertToMD5.convertPasswordToMD5(senha.getText());
			dto = appService.logar(nomeUsuario.getText(), password);
			tentativas = tentativas + 1;
			
			if (dto != null){
				logado = true;
			}
			else{
				logado = false;
				if (tentativas == 3){
					dicaSenha = appService.getDicaSenha(nomeUsuario.getText());
				}
			}
		} 
		catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		
		return logado;
	}	
	
	@SuppressWarnings("rawtypes")
	public Task taskLogar() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		return logar();
            }
            
            @Override
    		protected void succeeded() {
            	boolean result = (boolean) getValue();
            	if (tentativas == 3){ 
            		if (dicaSenha != null){
            			labelDicaSenha.setText("Dica de senha: " + dicaSenha);
        				labelDicaSenha.setVisible(true);
        				tentativas = 0;
            		}    				
    			}    
            	if (result == true){
            		principal.carregarTelaInicial(dto.getEmprestimo(), dto.getUsuario().getNomeUsuario(), dto.getUsuario().getId());
            	}
            	else {
            		indicador.setVisible(false);
            		nomeUsuario.setDisable(false);
        			senha.setDisable(false);
        			botaoLogar.setDisable(false);
            		alerta.notificacaoErro("Login", "Usuário ou senha incorretos, verifique os dados e tente novamente.");
            	}
            		
    		}
        };
    }
}