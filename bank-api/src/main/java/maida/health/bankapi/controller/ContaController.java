package maida.health.bankapi.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import maida.health.bankapi.config.security.TokenService;
import maida.health.bankapi.controller.dto.ContaDto;
import maida.health.bankapi.controller.dto.SaldoDto;
import maida.health.bankapi.controller.dto.TransferenciaDto;
import maida.health.bankapi.controller.dto.mensagem.AcessoNegadoDto;
import maida.health.bankapi.controller.dto.mensagem.ErroDto;
import maida.health.bankapi.controller.form.ContaForm;
import maida.health.bankapi.controller.form.SaldoForm;
import maida.health.bankapi.controller.form.TransferenciaForm;
import maida.health.bankapi.modelo.Conta;
import maida.health.bankapi.modelo.Transferencia;
import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.ContaRepository;
import maida.health.bankapi.repository.TransferenciaRepository;
import maida.health.bankapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/accounts")
public class ContaController {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TransferenciaRepository transferenciaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> cadastrar(@RequestBody @Valid ContaForm form, @RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder) {
		Usuario usuariologado = retornarUsuarioLogado(token);
		if(usuariologado != null) {
			Conta contaExistente = contaRepository.findByNumber(form.getNumber());
			if(contaExistente == null) {
				Conta conta = form.converter(contaRepository, usuariologado);
				contaRepository.save(conta);
				
				URI uri = uriBuilder.path("/accounts/").buildAndExpand(conta.getId()).toUri();
				return ResponseEntity.created(uri).body(new ContaDto(conta));
			}
			
			return ResponseEntity.badRequest().body(new ErroDto("Já existe uma conta com o número informado."));
		}
		return ResponseEntity.badRequest().body(new AcessoNegadoDto("Acesso negado"));
	}
	
	@PostMapping("/transfer")
	@Transactional
	public ResponseEntity<TransferenciaDto> Transferencia(@RequestBody @Valid TransferenciaForm form, @RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder) {
		Usuario usuariologado = retornarUsuarioLogado(token);
		if(usuariologado != null) {
			Transferencia transferencia = form.converter(contaRepository, usuariologado);
			transferenciaRepository.save(transferencia);
			
			URI uri = uriBuilder.path("/accounts/transfer").buildAndExpand(transferencia.getId()).toUri();
			return ResponseEntity.created(uri).body(new TransferenciaDto(transferencia, usuariologado));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/balance")
	@Transactional
	public ResponseEntity<SaldoDto> Transferencia(@RequestBody @Valid SaldoForm form, @RequestHeader("Authorization") String token, UriComponentsBuilder uriBuilder) {
		if(retornarUsuarioLogado(token) != null) {
			Conta conta = form.retornarConta(contaRepository);
			
			URI uri = uriBuilder.path("/accounts/balance").buildAndExpand(conta.getId()).toUri();
			return ResponseEntity.created(uri).body(new SaldoDto(conta));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	private Usuario retornarUsuarioLogado(String token) {
		Long idUsuario = tokenService.getIdUsuario(token.substring(7, token.length()));
		if(idUsuario != null) {
			return usuarioRepository.findById(idUsuario).get();
		}
		return null;
	}

}
