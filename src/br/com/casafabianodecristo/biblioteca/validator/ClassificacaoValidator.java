package br.com.casafabianodecristo.biblioteca.validator;

import java.util.List;
import br.com.casafabianodecristo.biblioteca.model.Classificacao;
import br.com.casafabianodecristo.biblioteca.service.ClassificacaoService;
import br.com.casafabianodecristo.biblioteca.utils.Alertas;

public class ClassificacaoValidator {
	private static Alertas alerta = new Alertas(); 
	private static ClassificacaoService classService = new ClassificacaoService();
	
	public static boolean validarValoresRepetidos(String nome, String hexa, boolean isEdit){
		List<Classificacao> classPorNome = classService.getClassificacaoPorNome(nome);
		List<Classificacao> classPorCor = classService.getClassificacaoPorCor(hexa);
			
		if(classPorNome.size() != 0 && isEdit == false){
			alerta.notificacaoErro("Cadastrar classificação", "Não é permitido cadastrar uma classificação de livros com um nome repetido.");
			return false;
		}
		else if(classPorCor.size() != 0){
			alerta.notificacaoErro("Cadastrar classificação", "Não é permitido cadastrar uma classificação de livros com uma cor repetida.");
			return false;
		}
		else if(hexa.equalsIgnoreCase("#FFFFFF")){
			alerta.notificacaoErro("Cadastrar classificação", "Não é permitido cadastrar uma classificação de livros com a cor branca.");
			return false;
		}
		else 
			return true;
	}
}