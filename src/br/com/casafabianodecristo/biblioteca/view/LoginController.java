package br.com.casafabianodecristo.biblioteca.view;

import java.security.NoSuchAlgorithmException;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.InicialDto;
import br.com.casafabianodecristo.biblioteca.utils.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	
	public LoginController() {}
	
	@FXML
	private void initialize(){
		
		senha.setOnKeyPressed((new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
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
				
		if (nomeUsuario.getText().equals("") && senha.getText().equals("")){
			nomeUsuario.setStyle("-fx-background-color: #ff7c7c;");
    		senha.setStyle("-fx-background-color: #ff7c7c;");
    		alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		nomeUsuario.requestFocus();
			indicador.setVisible(false);
    	}
    	else if (senha.getText().equals("") && !nomeUsuario.getText().equals("")){
    		senha.setStyle("-fx-background-color: #ff7c7c;");
    		nomeUsuario.setStyle("-fx-background-color: white;");
    		alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		senha.requestFocus();
			indicador.setVisible(false);
		}
		else if (nomeUsuario.getText().equals("") && !senha.getText().equals("")){
			nomeUsuario.setStyle("-fx-background-color: #ff7c7c;");
			senha.setStyle("-fx-background-color: white;");
			alerta.notificacaoAlerta("Login", "Verifique os campos obrigatórios e tente novamente.");
    		nomeUsuario.requestFocus();
    		indicador.setVisible(false);
		}	
		else {
			senha.setStyle("-fx-background-color: white;");
			nomeUsuario.setStyle("-fx-background-color: white;");
			logar = taskLogar();
			Thread t = new Thread(logar);
			t.setDaemon(true);
			t.start();
		}			
	}
	
	private boolean logar(){
		try {			
			String password = ConvertToMD5.convertPasswordToMD5(senha.getText());
			dto = appService.logar(nomeUsuario.getText(), password);
			tentativas = tentativas + 1;
			
			if (dto != null){
				logado = true;
				if (tentativas == 3)
					dicaSenha = appService.getDicaSenha(nomeUsuario.getText());
			}
			else
				logado = false;			
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
            		principal.carregarTelaInicial(dto.getTextoLembrete(), dto.getEmprestimo(), dto.getUsuario().getNomeUsuario());
            	}
            	else {
            		indicador.setVisible(false);
            		alerta.notificacaoErro("Login", "Usuário ou senha incorretos, verifique os dados e tente novamente.");
            	}
            		
    		}
        };
    }
}