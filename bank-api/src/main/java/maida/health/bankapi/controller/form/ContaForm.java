package maida.health.bankapi.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import maida.health.bankapi.modelo.Conta;
import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.ContaRepository;

public class ContaForm {
	
	@NotNull @NotEmpty
	private String number;
	
	@NotNull @Min(value = 0)
	private BigDecimal balance;
	
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Conta converter(ContaRepository contaRepository, Usuario logado) {
		return new Conta(number, balance, logado);
	}
	
}
