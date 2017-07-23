package br.com.casafabianodecristo.biblioteca.updater;

import br.com.casafabianodecristo.biblioteca.dto.UsuarioDto;
import br.com.casafabianodecristo.biblioteca.model.Usuario;

public class UsuarioUpdater {

	public static Usuario update(UsuarioDto dto, Usuario usuario){
		
		usuario.setId(dto.getId());
		usuario.setNomeUsuarioAcessoSistema(dto.getNomeUsuarioAcessoSistema());
		usuario.setSenha(dto.getSenha());
		usuario.setDicaSenha(dto.getDicaSenha());
		usuario.setDdd(dto.getDdd());
		usuario.setTelefone(dto.getTelefone());
		usuario.setFlAdministrador(dto.getFlAdministrador());
		usuario.setFlInativo(dto.getFlInativo());
		usuario.setNomeCompleto(dto.getNomeCompleto());
		
		return usuario;		
	}
}