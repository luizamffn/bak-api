package maida.health.bankapi.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import maida.health.bankapi.modelo.Conta;
import maida.health.bankapi.modelo.Transferencia;
import maida.health.bankapi.modelo.Usuario;
import maida.health.bankapi.repository.ContaRepository;

public class TransferenciaForm {
	
	@NotNull @NotEmpty
	private String source_account_number;
	@NotNull @NotEmpty
	private String destination_account_number;
	@NotNull @Min(value = 0)
	private BigDecimal amount;
	
	public String getSource_account_number() {
		return source_account_number;
	}
	public void setSource_account_number(String source_account_number) {
		this.source_account_number = source_account_number;
	}

	public String getDestination_account_number() {
		return destination_account_number;
	}
	public void setDestination_account_number(String destination_account_number) {
		this.destination_account_number = destination_account_number;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Transferencia converter(ContaRepository contaRepository, Usuario usuariologado) {
		Conta source_account = contaRepository.findByNumber(source_account_number);
		Conta destination_account = contaRepository.findByNumber(destination_account_number);

		return new Transferencia(amount, source_account, destination_account);
	}
	
}
