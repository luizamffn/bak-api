package maida.health.bankapi.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Conta {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String number;
	private BigDecimal balance;
	private LocalDateTime openDate = LocalDateTime.now();

	@ManyToOne
	private Usuario user;
	
	public Conta() {

	}
	
	public Conta(String number, BigDecimal balance, Usuario user) {
		this.number = number;
		this.balance = balance;
		this.user = user;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public LocalDateTime getOpenDate() {
		return openDate;
	}
	public void setOpenDate(LocalDateTime openDate) {
		this.openDate = openDate;
	}
	
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	
}
