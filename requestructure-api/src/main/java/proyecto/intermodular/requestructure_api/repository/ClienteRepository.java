package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import proyecto.intermodular.requestructure_api.domain.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Override
    @EntityGraph(attributePaths = {"solicitudes"})
    List<Cliente> findAll();
}
