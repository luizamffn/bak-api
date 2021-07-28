package maida.health.bankapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import maida.health.bankapi.modelo.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long>{

}
