package br.com.casafabianodecristo.biblioteca.view;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.appservice.*;
import br.com.casafabianodecristo.biblioteca.components.Numberfield;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.CadastroUsuarioInterfaceValidator;
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
	private Numberfield telefone;
	
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
		ddd.setMaxLength(3);
		ddd.setMinLength(3);
		telefone.setMaxLength(9);
		telefone.setMinLength(9);
		botaoCancelar.setOnAction((event) -> {
			Stage stage = (Stage) botaoCancelar.getScene().getWindow();
            stage.close();
		});
		
		botaoSalvar.setOnAction((event) -> {        	
        	if(CadastroUsuarioInterfaceValidator.validarCamposObrigatorios(checkAdm.isSelected(), getListaCampos())){
        		if(CadastroUsuarioInterfaceValidator.validarTamanhosObrigatorios(checkAdm.isSelected(), nome, sobrenome, ddd, telefone, senha, confirmacaoSenha, dicaSenha)){
        			if(checkAdm.isSelected()){
        				if(CadastroUsuarioInterfaceValidator.validarSenha(senha, confirmacaoSenha, dicaSenha)){
        					criarTask();
        				}
        			}
        			else criarTask();
        		}
        	}
		});
		
		checkAdm.setOnAction((event) -> desabilitarComportamento());
	}
	
	private void criarTask(){
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
	
	private List<TextField> getListaCampos(){
		List<TextField> lista = new ArrayList<TextField>();
		lista.add(nome);
		lista.add(sobrenome);
		lista.add(ddd);
		lista.add(telefone);
		lista.add(nomeUsuario);
		lista.add(senha);
		lista.add(confirmacaoSenha);
		lista.add(dicaSenha);
		
		return lista;
	}
	
	protected boolean cadastrarUsuario(boolean adm) throws Exception{
		String nome = this.nome.getText();
		String sobrenome = this.sobrenome.getText();
		int ddd = this.ddd.getValue();
		int telefone = this.telefone.getValue();
		String nomeUsuario = (this.nomeUsuario.getText().equals("")) ? null : this.nomeUsuario.getText();
		String senha = (this.senha.getText().equals("")) ? null : this.senha.getText();
		String dicaSenha = (this.dicaSenha.getText().equals("")) ? null : this.dicaSenha.getText();
		
		UsuarioDto dto = new UsuarioDto(0, nome, sobrenome, nomeUsuario, senha, ddd, telefone, checkAdm.isSelected(), dicaSenha, 0);
		
		return servico.cadastrarUsuario(dto);
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
            		alerta.notificacaoSucessoSalvarDados("Cadastrar usuário");
            	}
            	else{
            		alerta.notificacaoErro("Cadastrar usuário", "O nome de usuário escolhido para acesso ao sistema já está em uso. \nEscolha outro e tente novamente!");
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