package br.com.casafabianodecristo.biblioteca.components;

import java.io.IOException;
import javafx.fxml.*;
import javafx.scene.control.*;
/**
 *Componente JavaFX que aceita apenas números e que permite inserir uma
 *quantidade máxima de caracteres.
 *@see NumberField
 *@author Matheus de Matos Oliveira
 */
public class Numberfield extends TextField {
	@FXML
	private TextField numberField;
	
	private int maxLength = 100;
	private int minLength = 1; 

	public Numberfield(){
		FXMLLoader loader = new FXMLLoader(Numberfield.class.getResource("NumberField.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try{
			loader.load();
		}catch(IOException e){}
	}
	
	/**
	 * Seta o tamanho máximo de caracteres para o {@code NumberField}
	 * @param length
	 * defaults to 100 
	 */
	public void setMaxLength(int length){
		maxLength = length;
	}
	
	/**
	 * Seta o tamanho mínimo de caracteres para o {@code NumberField}
	 * @param length
	 * defaults to 1 
	 */
	public void setMinLength(int length){
		minLength = length;
	}
	
	/**
	 * Retorna o valor inteiro digitado no NumberField
	 */
	public int getValue(){
		return Integer.parseInt(getText());
	}
	
	@Override
	public void replaceText(int start, int end, String text) {
	    if (!text.equals("")) {
	        if (!text.matches("[0-9]")) {
	            return;
	        }
	    }
	    if (getText().length() < maxLength || text.equals("") || getText().length() > minLength) {
	        super.replaceText(start, end, text);
	    }
	}

    public void replaceSelection(String text)
    {
        if (validate(text) && text.length() < maxLength)
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }
}