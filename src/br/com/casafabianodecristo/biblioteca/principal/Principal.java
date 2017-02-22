package br.com.casafabianodecristo.biblioteca.principal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import br.com.casafabianodecristo.biblioteca.controller.*;
import br.com.casafabianodecristo.biblioteca.dto.ClassificacaoDto;
import br.com.casafabianodecristo.biblioteca.dto.EmprestimoDto;
import br.com.casafabianodecristo.biblioteca.model.*;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import br.com.casafabianodecristo.biblioteca.view.InicialController;
import br.com.casafabianodecristo.biblioteca.view.LoginController;
import javafx.application.Application;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Principal extends Application {
	private Stage primaryStage;
	private AnchorPane login;
	private Alertas alerta = new Alertas();
	
	private void onCloseRequest(Stage pagina, boolean ehTelaInicial){
		pagina.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent ev) {
				if (alerta.alertaConfirmacaoSair().get() == ButtonType.OK){
					if (ehTelaInicial)
						System.exit(0);	   	
					else 
						pagina.hide();
		    	}	
				else {
					ev.consume();
				} 
        	}
		});
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.getIcons().add(new Image("file:resources/images/icon-principal.png"));
		primaryStage.setResizable(false);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("LIVRES - Sistema para gestão de livros espíritas");
		this.primaryStage.getIcons().add(new Image("file:resources/images/icon-principal.png"));
		this.primaryStage.setResizable(false);
		carregarLogin();
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
	
	public void carregarLogin(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/Login.fxml"));
			
			login = (AnchorPane) loader.load();
			
			Scene scene = new Scene(login);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
            primaryStage.show();
            
            LoginController controller = loader.getController();
            controller.setPrincipal(this);
            
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	public void carregarTelaInicial(List<Emprestimo> devolucoes, String nomeUsuario, int idUsuario){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/Inicial.fxml"));			
			AnchorPane page = (AnchorPane) loader.load();
			Stage pagina = new Stage();			
			Scene scene = new Scene (page);
			ObservableList<Emprestimo> itens =FXCollections.observableList(devolucoes); 
			TableView<Emprestimo> tabelaEmprestimosPendentes  = (TableView<Emprestimo>) scene.lookup("#tabelaEmprestimosPendentes");
			Label dataHora = (Label) scene.lookup("#dataHora");
			Label id = (Label) scene.lookup("#labelId");
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date data = new Date();
			
			tabelaEmprestimosPendentes.setItems(itens);
			id.setText(Integer.toString(idUsuario));
			
			String str = fmt.format(data);			
			dataHora.setText("Horário do acesso: " + str);
			
			Label usuarioAcesso = (Label) scene.lookup("#usuarioAcesso");
			usuarioAcesso.setText("Usuário logado: " + nomeUsuario);
			
			pagina.setTitle("LIVRES - Sistema para gestão de livros espíritas");
			pagina.getIcons().add(new Image("file:resources/images/icon-principal.png"));
			pagina.setResizable(true);
			pagina.setMaximized(false);
			pagina.setScene(scene);
			onCloseRequest(pagina, true);
			
			InicialController controller = loader.getController();
			controller.setPrincipal(this);
			
			pagina.show();
			primaryStage.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void carregarCadastrarClassificacao(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("../view/CadastrarClassificacao.fxml"));
		
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			
			Stage pagina = new Stage();
			pagina.setTitle("Cadastrar classificação");
			onCloseRequest(pagina, false);
			
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.show();
			
		} catch (IOException e) {}
	}
	
	public void carregarCadastroLivros(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/CadastrarLivro.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Scene scene = new Scene(page);
			
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-add.png"));
			pagina.setTitle("Cadastrar livro");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.show();
			onCloseRequest(pagina, false);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public void carregarResultadoBusca(List<Livro> livros){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("../view/ResultadoBuscaLivros.fxml"));
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene  = new Scene(page);
			
			ResultadoBuscaController controller = loader.getController();
			controller.setPrincipal(this);
			
			ObservableList<Livro> itens = FXCollections.observableList (livros);
			TableView<Livro> tabelaLivros = (TableView<Livro>) scene.lookup("#tabelaLivros");
			tabelaLivros.setItems(itens);
						
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-search.png"));
			pagina.setTitle("Resultado da busca de livros");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarRemoverDoarLivro(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("../view/RemoverDoarLivros.fxml"));
		try{
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			
			Stage pagina = new Stage();
			
			pagina.setTitle("Remover/Doar livros do acervo");
			pagina.setMaximized(true);
			pagina.initOwner(primaryStage);			
			pagina.setScene(scene);
			pagina.initModality(Modality.APPLICATION_MODAL);
			onCloseRequest(pagina, false);
			pagina.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void carregarConsultaEmprestimo(List<EmprestimoDto> emprestimos){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/Emprestimo.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Scene scene = new Scene(page);
			ObservableList<EmprestimoDto> itens = FXCollections.observableList (emprestimos);
			TableView<EmprestimoDto> tabelaEmprestimos = (TableView<EmprestimoDto>) scene.lookup("#emprestimos");
			tabelaEmprestimos.setItems(itens);
			
			Stage pagina = new Stage();
			pagina.setTitle("Consultar empréstimos");
			pagina.initOwner(primaryStage);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.setScene(scene);
			onCloseRequest(pagina, false);
			pagina.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarCadastroUsuario(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/CadastrarUsuario.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Scene scene = new Scene(page);
			
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-add.png"));
			pagina.setTitle("Cadastrar usuário");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.setScene(scene);
			onCloseRequest(pagina, false);
			pagina.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarEmprestimoLivro(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/OldEmprestarLivro.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			Scene scene = new Scene(page);
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-add.png"));
			pagina.setTitle("Realizar empréstimo");
			//pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.setScene(scene);
			onCloseRequest(pagina, false);
			pagina.show();
			
		}catch(IOException e){}
	}
	
	@SuppressWarnings("unchecked")
	public void carregarGerenciamentoClassificacoes(List<ClassificacaoDto> classificacoes){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/GerenciarClassificacoes.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			Stage pagina = new Stage();
			ObservableList<ClassificacaoDto> itens = FXCollections.observableList (classificacoes);
			TableView<ClassificacaoDto> tabelaEmprestimos = (TableView<ClassificacaoDto>) scene.lookup("#classificacoes");
			tabelaEmprestimos.setItems(itens);
			
			//pagina.getIcons().add(new Image("file:resources/images/icon-add.png"));
			pagina.setTitle("Gerenciar classificações");
			//pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.initModality(Modality.APPLICATION_MODAL);
			pagina.setScene(scene);
			onCloseRequest(pagina, false);
			pagina.show();
			
		}catch(IOException e){}
	}
	
	/*public void carregarBuscaUsuario(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/BuscarUsuarios.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			BuscarUsuarioController controller = loader.getController();
			controller.setPrincipal(this);		
			
			Scene scene = new Scene(page);
			
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-search.png"));
			pagina.setTitle("Buscar usuários");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarDadosUsuario(Usuario usuario){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/CadastrarUsuario.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			
			CadastroUsuarioController controller = loader.getController();
            controller.setPrincipal(this);
			
			Scene scene = new Scene(page);
			Label id = (Label) scene.lookup("#id");
			TextField nome = (TextField) scene.lookup("#nome");;
			TextField sobrenome = (TextField) scene.lookup("#sobrenome");
			TextField ddd = (TextField) scene.lookup("#ddd");
			TextField telefone = (TextField) scene.lookup("#telefone");
			CheckBox checkAdm = (CheckBox) scene.lookup("#checkAdm");
			Label tituloPagina = (Label) scene.lookup("#tituloPagina");
			
			tituloPagina.setText("Editar dados do usuário");
			id.setText(usuario.getIdUsuarioString());
			checkAdm.setDisable(true);
			nome.setText(usuario.getNomeUsuario());
			sobrenome.setText(usuario.getSobrenome());
			ddd.setText(usuario.getDddString());
			telefone.setText(usuario.getTelefoneString());
			
			Stage pagina = new Stage();
			pagina.getIcons().add(new Image("file:resources/images/icon-add.png"));
			pagina.setTitle("Editar dados do usuário");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarLogin(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Principal.class.getResource("../view/Login.fxml"));
			
			login = (AnchorPane) loader.load();
			
			Scene scene = new Scene(login);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
            primaryStage.show();
            
            LoginController controller = loader.getController();
            controller.setPrincipal(this);
            
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void carregarInformacoesGeraisLivro(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Principal.class.getResource("../view/InformacoesLivro.fxml"));
		
		try {
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene  = new Scene(page);
			
			Stage pagina = new Stage();
			pagina.setTitle("Pesquisa de livros.");
			pagina.setResizable(false);
			pagina.initOwner(primaryStage);
			pagina.setScene(scene);
			pagina.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
