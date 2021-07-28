package maida.health.bankapi.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import maida.health.bankapi.modelo.Conta;
import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.ContaRepository;

public class ContaForm {
	
	@NotNull @NotEmpty
	private String number;
	
	@NotNull
	private BigDecimal balance;
	
	public String getNumber() {
		return number;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public Conta converter(ContaRepository contaRepository, Usuario logado) {
		return new Conta(number, balance, logado);
	}
	
	
	
}
