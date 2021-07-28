package maida.health.bankapi.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.UsuarioRepository;

public class UsuarioForm {
	
	@NotNull @NotEmpty
	private String name;
	@NotNull @NotEmpty
	private String email;
	@NotNull @NotEmpty
	private String senha;
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getSenha() {
		return senha;
	}
	public Usuario converter(UsuarioRepository usuarioRepository) {
		return new Usuario(name,email,senha);
	}	
	
}
