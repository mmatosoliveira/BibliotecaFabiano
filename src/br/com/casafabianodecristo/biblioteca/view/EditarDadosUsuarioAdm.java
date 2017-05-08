package br.com.casafabianodecristo.biblioteca.view;

import java.util.List;

import org.controlsfx.control.MaskerPane;

import br.com.casafabianodecristo.biblioteca.appservice.BibliotecaAppService;
import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.exceptions.ApplicationException;
import br.com.casafabianodecristo.biblioteca.interfacevalidator.CadastroUsuarioInterfaceValidator;
import br.com.casafabianodecristo.biblioteca.service.UsuarioService;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.utils.ConvertToMD5;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditarDadosUsuarioAdm {
	@FXML private TextField nomeUsuario;

	@FXML private PasswordField senhaAntiga;
	
	@FXML private PasswordField senhaNova;
	
	@FXML private PasswordField confirmacaoSenha;
	
	@FXML private TextField dicaSenha;
	
	@FXML private Button botaoSalvar;
	
	@FXML private Button botaoCancelar;
	
	@FXML private MaskerPane avisoCarregando;
	
	@FXML private BorderPane paneCarregando;
	
	private Alertas alerta = new Alertas();
	
	private UsuarioDto usuarioDto;
	
	private UsuarioService servico = new UsuarioService();
	
	@SuppressWarnings("rawtypes")
	private Task alterarInformacoes;
	
	@FXML private void initialize(){}
	
	@FXML private void salvar(){
		if(validarFormulario()){
			mudarEstadoCampos(true);
			alterarDadosUsuario();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public Task taskAlterarUsuario() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
        		alterarDadosUsuario();
        		System.out.println("Voltou");
            	return null;
            }
            
            @Override
    		protected void succeeded() {
            	Object result = (Object) getValue();
            	if (result == null){
            		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
                    stage.close();
            		alerta.notificacaoSucessoSalvarDados("Editar informações de acesso");
            	}
            	else{
            		alerta.notificacaoErro("Editar informações de acesso", "Ocorreu um erro ao alterar os dados. Tente novamente mais tarde.");
            		mudarEstadoCampos(false);
            	}
    		}
        };
    }
	
	protected void alterarDadosUsuario(){
		usuarioDto.setNomeUsuarioAcessoSistema(nomeUsuario.getText());
		usuarioDto.setSenha(senhaNova.getText());
		usuarioDto.setDicaSenha(dicaSenha.getText());
		usuarioDto.setExibeMsg(false);
		try {
			servico.atualizarUsuario(usuarioDto);
		} catch (ApplicationException e) {
			mudarEstadoCampos(false);
			alerta.notificacaoErro("Editar informações de acesso", "Ocorreu um erro ao editar as informações de acesso do usuário.\nTente novamente mais tarde.");
		}
		Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
		alerta.notificacaoSucessoSalvarDados("Editar informações de acesso");
	}
	
	private void mudarEstadoCampos(boolean estado){
		avisoCarregando.setText("Salvando informações... Aguarde!");
		avisoCarregando.setVisible(estado);
		paneCarregando.setVisible(estado);
	}
	
	private boolean validarFormulario(){
		usuarioDto = (UsuarioDto) botaoCancelar.getScene().getRoot().getUserData();
		String novaSenha = senhaNova.getText();
		String confirmacao = confirmacaoSenha.getText();
		String senhaDigitada = "";
		try {
			senhaDigitada = ConvertToMD5.convertPasswordToMD5(senhaAntiga.getText());
		}
		catch (Exception e) {
			System.out.println("erro!");
		}
		
		if(validarCamposVazios()){
			if(!usuarioDto.getSenhaAntesEdicao().equals(senhaDigitada)){
				senhaAntiga.setStyle("-fx-background-color: #ff7c7c;");
				alerta.notificacaoErro("Editar informações de acesso", "A senha digitada não corresponde com a senha já cadastrada.");
				return false;
			}
			else if(!confirmacao.equals(novaSenha)){
				senhaNova.setStyle("-fx-background-color: #ff7c7c;");
				confirmacaoSenha.setStyle("-fx-background-color: #ff7c7c;");
				alerta.notificacaoErro("Editar informações de acesso", "A nova senha e a confirmação não correspondem.");
				return false;
			}	
			else if(CadastroUsuarioInterfaceValidator.validarSenha(senhaNova, confirmacaoSenha, dicaSenha, "Editar informações de acesso"))
				return true;
			else return false;
		}
		else
			return false;	
	}
	
	private boolean validarCamposVazios(){
		if(nomeUsuario.getText().isEmpty()){
			nomeUsuario.requestFocus();
			nomeUsuario.setStyle("-fx-background-color: #ff7c7c;");
			alerta.notificacaoAlerta("Editar informações de acesso", "É obrigatório informar um nome de usuário para acesso ao sistema.");
			return false;
		}
		else if(senhaAntiga.getText().isEmpty()){
			senhaAntiga.requestFocus();
			senhaAntiga.setStyle("-fx-background-color: #ff7c7c;");
			alerta.notificacaoAlerta("Editar informações de acesso", "É obrigatório informar a senha já cadastrada.");
			return false;
		}else if(senhaNova.getText().isEmpty()){
			senhaNova.requestFocus();
			senhaNova.setStyle("-fx-background-color: #ff7c7c;");
			alerta.notificacaoAlerta("Editar informações de acesso", "É obrigatório informar a nova senha.");
			return false;
		}else if(confirmacaoSenha.getText().isEmpty()){
			confirmacaoSenha.requestFocus();
			confirmacaoSenha.setStyle("-fx-background-color: #ff7c7c;");
			alerta.notificacaoAlerta("Editar informações de acesso", "É obrigatório informar a confimação da senha.");
			return false;
		}
		else if(dicaSenha.getText().isEmpty()){
			dicaSenha.requestFocus();
			dicaSenha.setStyle("-fx-background-color: #ff7c7c;");
			alerta.notificacaoAlerta("Editar informações de acesso", "É obrigatório informar a dica de senha.");
			return false;
		}
		else 
			return true;
	}
}