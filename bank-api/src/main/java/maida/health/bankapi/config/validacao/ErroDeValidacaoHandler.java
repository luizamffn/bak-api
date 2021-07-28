package maida.health.bankapi.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			if(e.getCode().equals("NotEmpty")) {
				mensagem = "O Campo " + e.getField() + " não pode ser vazio";
			}else if (e.getCode().equals("NotNull")) {
				mensagem = "O Campo " + e.getField() + " não pode ser nulo";
			}else if (e.getCode().equals("Length")) {
				mensagem = e.getField() + " muito curta";
			}else if (e.getCode().equals("Email")) {
				mensagem = "Email inválido";
			}else if (e.getCode().equals("Min")) {
				mensagem = "O Campo " + e.getField() + " deve ser maior ou igual a zero";
			}
			
			ErroDeFormularioDto erro = new ErroDeFormularioDto(mensagem);
			dto.add(erro);
		});
		
		return dto;
	}

}
