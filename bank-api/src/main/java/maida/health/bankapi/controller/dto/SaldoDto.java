package maida.health.bankapi.controller.dto;

import java.math.BigDecimal;

import maida.health.bankapi.modelo.Conta;

public class SaldoDto {
	
	private String account_number;
	private BigDecimal balance;
	
	public SaldoDto(Conta conta) {
		this.account_number = conta.getNumber();
		this.balance = conta.getBalance();
	}
	
	
	public String getAccount_number() {
		return account_number;
	}

	public BigDecimal getBalance() {
		return balance;
	}

}
