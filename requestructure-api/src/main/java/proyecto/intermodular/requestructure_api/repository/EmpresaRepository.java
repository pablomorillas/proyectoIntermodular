package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.intermodular.requestructure_api.domain.Empresa;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByEmail(String email);
    boolean existsByNif(String nif);

    Optional<Empresa> findByNif(String nif);
}
