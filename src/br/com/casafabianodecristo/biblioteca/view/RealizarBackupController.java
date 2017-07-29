package br.com.casafabianodecristo.biblioteca.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RealizarBackupController {
	@FXML	private RadioButton radioEncerrarLivres;
	
	@FXML	private RadioButton radioEncerrarLivresESistema;
	
	@FXML	private Button botaoCancelar;
	
	@FXML	private Button iniciarBackup;
	
	@FXML	private Button restaurarBackup;
	
	@FXML	private TextField caminhoArquivoBackup;
	
	@FXML	private Button selecionarArquivo;
	
	@FXML	private ProgressBar progresso;
	
	private Alertas alerta = new Alertas();
	
	@FXML
	private void initialize(){
		iniciarBackup.setOnAction(e -> gerarBackup());
	}
	
	private void gerarBackup(){
		progresso.setVisible(true);
		SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-yyyy-HH-mm-ss]");
		Process exec;
		String user = System.getProperty("user.home");
		new File(user+"\\Documents\\Livres\\Backups").mkdirs();
		try {
			exec = Runtime.getRuntime().exec(new String[]{"cmd.exe","/c","C:\\\"Program Files\"\\MySQL\\\"MySQL Server 5.7\"\\bin\\mysqldump --user=user --password=user BibliotecaFabiano2 > "+user+"\\Documents\\Livres\\Backups\\Backup"+sdf.format(new Date())+".sql"});
			
			if(exec.waitFor() == 0)
			{
				if(radioEncerrarLivresESistema.isSelected()){
					alerta.alertaSucesso("Realizar backup", "Sucesso ao realizar backup!");
					System.exit(0);
					new ProcessBuilder("cmd.exe", "/c", "shutdown -s -f -t 00").start();
				}
				else if(radioEncerrarLivres.isSelected()){
					alerta.alertaSucesso("Realizar backup", "Sucesso ao realizar backup!");
					System.exit(0);
				}
				else{
					alerta.alertaSucesso("Realizar backup", "Sucesso ao realizar backup!");
					Stage stage = (Stage) progresso.getScene().getWindow();
					stage.close();
				}
				//exec.destroy();
			}
			else //ERROR
			{
				alerta.alertaErro("Realizar backup", "Ocorreu um erro ao realizar o backup. Tente novamente mais tarde.");
				progresso.setVisible(false);
				InputStream errorStream = exec.getErrorStream();
			    byte[] buffer = new byte[errorStream.available()];
			    errorStream.read(buffer);

			    String str = new String(buffer);
			    System.out.println(str);
				//exec.destroy();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}