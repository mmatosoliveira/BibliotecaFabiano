package br.com.casafabianodecristo.biblioteca.components;

import java.io.IOException;
import javafx.fxml.*;
import javafx.scene.control.*;

public class Numberfield extends TextField {
	@FXML
	private TextField numberField;
	
	private int maxLength;

	public Numberfield(){
		FXMLLoader loader = new FXMLLoader(Numberfield.class.getResource("NumberField.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try{
			loader.load();
		}catch(IOException e){}
	}
	
	public void setMaxLength(int length){
		maxLength = length;
	}
	
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
	    if (getText().length() < maxLength || text.equals("")) {
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