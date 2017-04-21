package br.com.casafabianodecristo.biblioteca.view;

import java.util.ArrayList;
import java.util.List;
import org.controlsfx.control.MaskerPane;
import br.com.casafabianodecristo.biblioteca.principal.Principal;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.appservice.*;
import br.com.casafabianodecristo.biblioteca.components.Numberfield;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.CadastroUsuarioInterfaceValidator;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
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
	private Label tituloPagina;
	
	@SuppressWarnings("rawtypes")
	private Task operacao;
	
	private Alertas alerta = new Alertas();
	
	private BibliotecaAppService servico = new BibliotecaAppService();
	
	@FXML
	private MaskerPane avisoCarregando = new MaskerPane();
	
	@FXML
	private BorderPane paneCarregando = new BorderPane();
	
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
        					try {
								criarTask();
							} catch (ApplicationException e) {
								e.printStackTrace();
							}
        				}
        			} else
						try {
							criarTask();
						} catch (ApplicationException e) {
							e.printStackTrace();
						}
        		}
        	}
		});
		
		checkAdm.setOnAction((event) -> desabilitarComportamento());
	}
	
	@SuppressWarnings("unchecked")
	private void criarTask() throws ApplicationException{
		List<Object> dados = (List<Object>) botaoCancelar.getScene().getRoot().getUserData();
		boolean isEdit = false;
		int id = 0; 
		if(dados != null){
			isEdit = (boolean) dados.get(2);
			id = (int) dados.get(1);
		}
		if(id == 0 && isEdit){
			throw new ApplicationException("Erro ao editar usuário. Não foi possível identificar o Id do usuário a ser editado.", "Editar dados do usuário", "Erro ao editar usuário. Não foi possível identificar o Id do usuário a ser editado.");
		}
		exibirMascara(true, isEdit);
			
		try {
			
			if(isEdit == false){
				operacao = taskCadastrarUsuario();
				Thread t = new Thread(operacao);
				t.setDaemon(true);
				t.start();
			}
			else {
				operacao = taskAlterarUsuario();
				Thread t = new Thread(operacao);
				t.setDaemon(true);
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void exibirMascara(boolean visible, boolean isEdit){
		if(isEdit)
			avisoCarregando.setText("Alterando dados do usuário... Aguarde!");
		else
			avisoCarregando.setText("Cadastrando usuário... Aguarde!");
		
		avisoCarregando.setVisible(visible);
		paneCarregando.setVisible(visible); 
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
	
	@SuppressWarnings("unchecked")
	protected void alterarDadosUsuario(){
		List<Object> dados = (List<Object>) botaoCancelar.getScene().getRoot().getUserData();
		
		int id = (int) dados.get(1);
		String nome = this.nome.getText();
		String sobrenome = this.sobrenome.getText();
		int ddd = this.ddd.getValue();
		int telefone = this.telefone.getValue();
		
		UsuarioDto dto = new UsuarioDto(id, nome, sobrenome, ddd, telefone, 0);
		servico.atualizarUsuario(dto);
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
            		atualizarTableView();
                    stage.close();
            		alerta.notificacaoSucessoSalvarDados("Cadastrar usuário");
            	}
            	else{
            		alerta.notificacaoErro("Cadastrar usuário", "O nome de usuário escolhido para acesso ao sistema já está em uso. \nEscolha outro e tente novamente!");
            		exibirMascara(false, false);
            	}
        			
    		}
        };
    }
	
	@SuppressWarnings("unchecked")
	private void atualizarTableView(){
		List<Object> dados = (List<Object>) botaoCancelar.getScene().getRoot().getUserData();
		TableView<UsuarioDto> usuarios = (TableView<UsuarioDto>) dados.get(0);
		List<UsuarioDto> lista = servico.getUsuarios("");
		usuarios.setItems(FXCollections.observableList(lista));
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskAlterarUsuario() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		alterarDadosUsuario();
            	return null;
            }
            
            @Override
    		protected void succeeded() {
            	Object result = (Object) getValue();
            	if (result == null){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
            		atualizarTableView();
                    stage.close();
            		alerta.notificacaoSucessoSalvarDados("Editar dados do usuário");
            	}
            	else{
            		alerta.notificacaoErro("Editar dados do usuário", "Ocorreu um erro ao alterar os dados. Tente novamente mais tarde.");
            		exibirMascara(false, false);
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