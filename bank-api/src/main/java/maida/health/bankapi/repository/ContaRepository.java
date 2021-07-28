package maida.health.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import maida.health.bankapi.modelo.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	Conta findByNumber(String source_account_number);

}
