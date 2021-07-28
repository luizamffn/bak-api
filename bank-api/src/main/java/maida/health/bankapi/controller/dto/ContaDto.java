package maida.health.bankapi.controller.dto;

import java.math.BigDecimal;

import maida.health.bankapi.modelo.Conta;

public class ContaDto {
	
	private String number;
	private BigDecimal balance;
	private UsuarioDto userDto;
	
	public ContaDto(Conta conta) {
		this.number = conta.getNumber();
		this.balance = conta.getBalance();
		this.userDto = new UsuarioDto(conta.getUser());
	}
	
	public String getNumber() {
		return number;
	}
	public BigDecimal getBalance() {
		return balance;
	}

	public UsuarioDto getUserDto() {
		return userDto;
	}
	
}
