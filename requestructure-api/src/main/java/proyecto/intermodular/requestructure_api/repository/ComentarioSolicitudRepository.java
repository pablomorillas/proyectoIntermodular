package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.intermodular.requestructure_api.domain.ComentarioSolicitud;

public interface ComentarioSolicitudRepository extends JpaRepository<ComentarioSolicitud, Long> {
}
