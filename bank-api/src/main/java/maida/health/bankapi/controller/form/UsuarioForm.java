package maida.health.bankapi.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.UsuarioRepository;

public class UsuarioForm {
	
	@NotNull @NotEmpty
	private String name;
	@NotNull @NotEmpty @Email
	private String email;
	@NotNull @NotEmpty @Length(min = 5)
	private String senha;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}


	public Usuario converter(UsuarioRepository usuarioRepository) {
		return new Usuario(name,email,senha);
	}	
	
}
