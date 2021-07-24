package maida.health.bankapi.modelo;

import java.math.BigDecimal;

public class Transferencia {
	
	private Long id;
	private BigDecimal amount;
	private Conta sourceAccount;
	private Conta destinationAccount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Conta getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(Conta sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public Conta getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Conta destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	
}
