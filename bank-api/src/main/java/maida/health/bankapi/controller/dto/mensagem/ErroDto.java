package maida.health.bankapi.controller.dto.mensagem;

public class ErroDto {
	private String erro;
	
	public ErroDto(String erro) {
		this.erro = erro;
	}

	public String getErro() {
		return erro;
	}
}
