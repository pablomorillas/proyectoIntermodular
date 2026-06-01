package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.intermodular.requestructure_api.domain.Solicitud;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    @Override
    @EntityGraph(attributePaths = {"cliente", "comentarios"})
    List<Solicitud> findAll();
}
