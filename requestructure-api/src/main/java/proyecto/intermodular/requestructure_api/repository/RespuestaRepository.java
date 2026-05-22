package proyecto.intermodular.requestructure_api.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import proyecto.intermodular.requestructure_api.domain.Respuesta;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    @Override
    @EntityGraph(attributePaths = {"solicitud", "empresa", "cliente", "comentarios"})
    List<Respuesta> findAll();
}
