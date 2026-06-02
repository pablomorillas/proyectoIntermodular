package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import proyecto.intermodular.requestructure_api.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Override
    @EntityGraph(attributePaths = {"solicitudes"})
    List<Cliente> findAll();

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
