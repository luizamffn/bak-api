package maida.health.bankapi.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transferencia {
	
	private Long id;
	private BigDecimal amount;
	
	private Conta sourceAccount;
	private Conta destinationAccount;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ManyToOne
	public Conta getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(Conta sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	
	@ManyToOne
	public Conta getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Conta destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	
}
