package maida.health.bankapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import maida.health.bankapi.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
}
