package maida.health.bankapi.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import maida.health.bankapi.controller.dto.ErroDto;
import maida.health.bankapi.controller.dto.UsuarioDto;
import maida.health.bankapi.controller.form.UsuarioForm;
import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/users")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(form.getEmail());
		if (usuarioExistente.isPresent()) {
			return ResponseEntity.ok(new ErroDto("Já existe um usuário com o email informado."));
		}
		
		Usuario usuario = form.converter(usuarioRepository);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuario);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
	}

}
