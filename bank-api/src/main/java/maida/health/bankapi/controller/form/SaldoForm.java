package maida.health.bankapi.controller.form;

import javax.validation.constraints.NotNull;

import maida.health.bankapi.modelo.Conta;
import maida.health.bankapi.repository.ContaRepository;

public class SaldoForm {
	
	@NotNull
	private String account_number;
	
	
	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public Conta retornarConta(ContaRepository contaRepository) {
		return contaRepository.findByNumber(account_number);
	}
	
}
