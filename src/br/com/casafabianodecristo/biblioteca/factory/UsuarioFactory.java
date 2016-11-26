package br.com.casafabianodecristo.biblioteca.factory;

import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.model.Usuario;

public class UsuarioFactory {

	public static Usuario create(UsuarioDto dto){
		Usuario usuario = new Usuario();
		
		usuario.setNomeUsuario(dto.getNomeUsuario());
		usuario.setSobrenome(dto.getSobrenome());
		usuario.setNomeUsuarioAcessoSistema(dto.getNomeUsuarioAcessoSistema());
		//usuario.setSenha(dto.getDicaSenha());
		usuario.setDicaSenha(dto.getDicaSenha());
		usuario.setDdd(dto.getDdd());
		usuario.setTelefone(dto.getTelefone());
		usuario.setFlAdministrador(dto.getFlAdministrador());
		usuario.setFlInativo(dto.getFlInativo());
		
		return usuario;
		
	}
}
