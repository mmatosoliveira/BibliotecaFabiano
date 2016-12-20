package br.com.casafabianodecristo.biblioteca.controller;

import java.security.NoSuchAlgorithmException;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.appservice.*;
import br.com.casafabianodecristo.biblioteca.components.Numberfield;
import br.com.casafabianodecristo.biblioteca.model.Usuario;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CadastroUsuarioController {
	@FXML
	private Button botaoSalvar;
	
	@FXML
	private Button botaoCancelar;
	
	@FXML
	private CheckBox checkAdm;
	
	@FXML
	private TextField nomeUsuario;
	
	@FXML
	private TextField nome;
	
	@FXML
	private TextField sobrenome;
	
	@FXML
	private Numberfield ddd; 
	
	@FXML
	private Numberfield telefone = new Numberfield();
	
	@FXML
	private PasswordField senha;
	
	@FXML
	private PasswordField confirmacaoSenha;
	
	@FXML
	private TextField dicaSenha;
	
	@FXML
	private Label id;
	
	@FXML 
	private Label lblNomeUsuario;
	
	@FXML
	private Label lblSenha;
	
	@FXML
	private Label lblConfirmaSenha;
	
	@FXML
	private Label lblDicaSenha;
	
	@FXML
	private Label lblTamanhoCaracteres;
	
	@FXML
	private ProgressIndicator indicador;
	
	@FXML
	private Label tituloPagina;
	
	@SuppressWarnings("rawtypes")
	private Task operacao;
	
	private Alertas alerta = new Alertas();
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	private int caracteres;
	
	Stage stage;
	
	Principal principal = new Principal();
	
	public CadastroUsuarioController() {}
	
	@FXML
	public void initialize(){
		//ddd.setMaxLength(3);
		//telefone.setMaxLength(9);
		botaoCancelar.setOnAction((event) -> {
			Stage stage = (Stage) botaoCancelar.getScene().getWindow();
            stage.close();
		});
		
		botaoSalvar.setOnAction((event) -> {
			boolean camposObrigatorios = validarCamposObrigatorios(checkAdm.isSelected());
        	boolean senhas = validarSenha(checkAdm.isSelected());
        	boolean telefone = validarTelefone();
        	String texto = this.id.getText();
        	int id = 0;
        	if (!texto.equals(""))
        		id = Integer.parseInt(this.id.getText());

        	if (id == 0){
        		if (camposObrigatorios && senhas && telefone){
            		try {
            			indicador.setVisible(true);
            			operacao = taskCadastrarUsuario();
            			Thread t = new Thread(operacao);
            			t.setDaemon(true);
            			t.start();
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
            	}
        	}
        	else{
        		camposObrigatorios = validarCamposObrigatorios(checkAdm.isSelected());
        		telefone = validarTelefone();
        		if(camposObrigatorios && telefone){
        			indicador.setVisible(true);
        			operacao = taskAlterarUsuario();
        			Thread t = new Thread(operacao);
        			t.setDaemon(true);
        			t.start();
        		}
        	}
		});
		
		checkAdm.setOnAction((event) -> {
		    desabilitarComportamento();
		});
	}
	
	protected boolean cadastrarUsuario(boolean adm) throws Exception{
		String nome = this.nome.getText();
		String sobrenome = this.sobrenome.getText();
		int ddd = Integer.parseInt(this.ddd.getText());
		int telefone = Integer.parseInt(this.telefone.getText());
		String nomeUsuario = this.nomeUsuario.getText();
		String senha = this.senha.getText();
		String dicaSenha = this.dicaSenha.getText();
		/*servico.createEntityManagerFactory();
		Usuario usuario;
		if (adm){
			usuario = new Usuario(nome, sobrenome, nomeUsuario, ddd, telefone, 1, dicaSenha);
			try {
				usuario.setSenhaCriptografada(senha);
			} catch (NoSuchAlgorithmException e) {e.printStackTrace();}
		}
		else {
			usuario = new Usuario(nome, sobrenome, ddd, telefone, 0);
		}		
			boolean resultado = servico.cadastrarUsuario(usuario);
		servico.closeEntityManagerFactory();*/
		
		return true;
	}
	
	protected boolean alterarDadosUsuario(){
		int id = Integer.parseInt(this.id.getText());
		String nome = this.nome.getText();
		String sobrenome = this.sobrenome.getText();
		int ddd = Integer.parseInt(this.ddd.getText());
		int telefone = Integer.parseInt(this.telefone.getText());
		
		/*suario usuario = new Usuario(id, nome, sobrenome, ddd, telefone);
		servico.createEntityManagerFactory();
			boolean resultado = servico.alterarDadosUsuario(id, usuario);
		servico.closeEntityManagerFactory();*/
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskCadastrarUsuario() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		return cadastrarUsuario(checkAdm.isSelected());
            }
            
            @Override
    		protected void succeeded() {
            	boolean result = (boolean) getValue();
            	if (result == true){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
                    stage.close();
            		alerta.notificacaoSucessoSalvarDados("Cadastrar usu�rio");
            	}
            	else{
            		alerta.notificacaoErro("Cadastrar usu�rio", "O nome de usu�rio escolhido para acesso ao sistema j� est� em uso. \nEscolha outro e tente novamente!");
            		indicador.setVisible(false);
            	}
        			
    		}
        };
    }
	
	@SuppressWarnings("rawtypes")
	public Task taskAlterarUsuario() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		return alterarDadosUsuario();
            }
            
            @Override
    		protected void succeeded() {
            	boolean result = (boolean) getValue();
            	if (result == true){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
                    stage.close();
            		alerta.notificacaoSucessoSalvarDados("Alterar dados do usu�rio");
            	}
            	else{
            		alerta.notificacaoErro("Alterar dados do usu�rio", "Ocorreu um erro ao alterar os dados. Tente novamente mais tarde.");
            		indicador.setVisible(false);
            	}
        			
    		}
        };
    }
	
	protected boolean validarSenha(boolean adm){
		String senha = this.senha.getText();
		String confirmacaoSenha = this.confirmacaoSenha.getText();
		String dicaSenha = this.dicaSenha.getText();
		boolean resultado = true;
		if (adm){
			if (senha.equals(confirmacaoSenha) && !dicaSenha.equals(senha) && senha.length() <= 8)
				resultado = true;
			else if (senha.equals(confirmacaoSenha) && dicaSenha.equals(senha)){
				alerta.alertaErro("Cadastrar usu�rio", "A dica de senha n�o pode ser igual a senha.");
				resultado = false;
			}
			else if (senha.length()>8){
				alerta.notificacaoErro("Cadastrar usu�rio", "A senha deve ter no m�ximo 8 caracteres.");
				this.senha.requestFocus();
				resultado = false;
			}
			else{
				alerta.notificacaoErro("Cadastrar usu�rio", "As senhas digitadas n�o coincidem.");
				resultado = false;
			}
		}		
		return resultado;
	}
	
	@SuppressWarnings("unused")
	protected boolean validarTelefone(){
		int ddd = 0;
		int telefone = 0;
		boolean retorno;
		if (this.ddd.getText().equals(""))
			ddd = 0;
		else 
			ddd = Integer.parseInt(this.ddd.getText());
		
		caracteres = this.telefone.getText().length();
		if (caracteres == 9){
			lblTamanhoCaracteres.setText("M�ximo: 9 caracteres.");
			lblTamanhoCaracteres.setVisible(true);
		}
		
		String dddString = Integer.toString(ddd);
		String telefoneString = Integer.toString(telefone);
		
		
		if (dddString.length() > 3){
			alerta.alertaAviso("Cadastrar usu�rios", "O DDD pode ter at� 3 digitos.");
			retorno =  false;
		}			
		
		if (caracteres > 9){
			alerta.alertaAviso("Cadastrar usu�rios", "O telefone pode ter no m�ximo 9 digitos.");
			retorno =  false;
		}
		else{
			telefone = Integer.parseInt(this.telefone.getText());
			retorno = true;	
		}	
		return retorno;
	}
	
	protected boolean validarCamposObrigatorios (boolean adm){
		String nome = this.nome.getText();
		String sobrenome = this.sobrenome.getText();
		int ddd;
		String nomeUsuario = this.nomeUsuario.getText();
		String senha = this.senha.getText();
		String confirmacaoSenha = this.confirmacaoSenha.getText();
		String dicaSenha = this.dicaSenha.getText();
		boolean resultado;
		
		if (this.ddd.getText().equals(""))
			ddd = 0;
		else 
			ddd = Integer.parseInt(this.ddd.getText());
		
		if (adm){
			if (!nome.equals("") && !sobrenome.equals("") && ddd != 0 && 
				caracteres != 0 && !nomeUsuario.equals("") && !senha.equals("") && 
				!confirmacaoSenha.equals("") && !dicaSenha.equals(""))				
				resultado = true;
			else{
				alerta.alertaErro("Usu�rio", "Verifique os campos de preenchimento obrigat�rio e tente novamente.");
				resultado = false;
			}				
		}
		else{
			if (!nome.equals("") && !sobrenome.equals("") && ddd != 0 && caracteres != 0)
				resultado =  true;
			else{
				alerta.alertaErro("Usu�rio", "Verifique os campos de preenchimento obrigat�rio e tente novamente.");
				resultado = false;
			}
		}
		return resultado;	
	}
	
	protected void desabilitarComportamento(){
		if (checkAdm.isSelected()){
    		nomeUsuario.setDisable(false);
    		senha.setDisable(false);
    		confirmacaoSenha.setDisable(false);
    		dicaSenha.setDisable(false);
    		lblNomeUsuario.setDisable(false);
    		lblSenha.setDisable(false);
    		lblConfirmaSenha.setDisable(false);
    		lblDicaSenha.setDisable(false);
    	}
    	else {
    		nomeUsuario.setDisable(true);
    		senha.setDisable(true);
    		confirmacaoSenha.setDisable(true);
    		dicaSenha.setDisable(true);
    		nomeUsuario.setText("");
    		senha.setText("");
    		confirmacaoSenha.setText("");
    		dicaSenha.setText("");
    		lblNomeUsuario.setDisable(true);
    		lblSenha.setDisable(true);
    		lblConfirmaSenha.setDisable(true);
    		lblDicaSenha.setDisable(true);
    	}
	}
	
	public void setPrincipal(Principal p){
		principal = p;
	}
}