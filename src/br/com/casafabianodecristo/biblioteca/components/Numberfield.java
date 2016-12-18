package br.com.casafabianodecristo.biblioteca.components;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class Numberfield extends AnchorPane {
	@FXML
	private TextField numberField;

	public Numberfield(){
		FXMLLoader loader = new FXMLLoader(Numberfield.class.getResource("NumberField.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try{
			loader.load();
		}catch(IOException e){}
	}
	
	@FXML
	private void initialize(){
		numberField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	numberField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
	}
}
