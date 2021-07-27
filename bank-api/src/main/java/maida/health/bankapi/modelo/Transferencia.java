package maida.health.bankapi.modelo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transferencia {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal amount;
	
	@ManyToOne
	private Conta sourceAccount;
	
	@ManyToOne
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
