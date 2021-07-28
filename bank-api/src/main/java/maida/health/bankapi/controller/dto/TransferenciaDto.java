package maida.health.bankapi.controller.dto;

import java.math.BigDecimal;

import maida.health.bankapi.modelo.Transferencia;
import maida.health.bankapi.modelo.Usuario;

public class TransferenciaDto {
	
	private BigDecimal amount;
	private String source_account_number;
	private String destination_account_number;
	private UsuarioDto user;
	
	
	public TransferenciaDto(Transferencia transferencia, Usuario usuariologado) {
		this.amount = transferencia.getAmount();
		this.source_account_number = transferencia.getSourceAccount().getNumber();
		this.destination_account_number = transferencia.getDestinationAccount().getNumber();
		this.user = new UsuarioDto(usuariologado);
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public String getSource_account_number() {
		return source_account_number;
	}
	public String getDestination_account_number() {
		return destination_account_number;
	}
	public UsuarioDto getUser() {
		return user;
	}
	
}
