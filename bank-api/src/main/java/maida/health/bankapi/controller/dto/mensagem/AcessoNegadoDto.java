package maida.health.bankapi.controller.dto.mensagem;

public class AcessoNegadoDto {
	private String mensage;
	
	public AcessoNegadoDto(String mensage) {
		this.mensage = mensage;
	}

	public String getMensage() {
		return mensage;
	}
}
