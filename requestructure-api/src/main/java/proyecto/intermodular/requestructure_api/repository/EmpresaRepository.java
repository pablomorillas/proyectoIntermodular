package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.intermodular.requestructure_api.domain.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByEmail(String email);
    boolean existsByNif(String nif);
}
