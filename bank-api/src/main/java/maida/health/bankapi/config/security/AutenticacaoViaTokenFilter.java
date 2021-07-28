package maida.health.bankapi.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {		
		String token = recuperarToken(request);

		StringBuffer url = request.getRequestURL();
		if(url.toString().contains("/accounts")) {
			if(token == null) {
				acessoNegado(response);
				return;
			}
		}
		
		boolean valido = tokenService.isTokenValido(token);
		
		if (valido) {
			autenticarCliente(token);
		}
		
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
//			acessoNegado(response);
//			return;
		}
		
	}

	private void autenticarCliente(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = usuarioRepository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(autentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
	
	private void acessoNegado(HttpServletResponse response) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		 httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);     
		 httpResponse.getWriter().write("{\"message\": \"Acesso negado\"}");     
		 httpResponse.getWriter().flush();     
		 httpResponse.getWriter().close();
	}

}