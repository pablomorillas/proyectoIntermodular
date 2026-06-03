package proyecto.intermodular.requestructure_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import proyecto.intermodular.requestructure_api.config.PasswordHasher;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.domain.Empresa;
import proyecto.intermodular.requestructure_api.domain.EstadoRespuesta;
import proyecto.intermodular.requestructure_api.domain.EstadoSolicitud;
import proyecto.intermodular.requestructure_api.domain.Respuesta;
import proyecto.intermodular.requestructure_api.domain.Solicitud;
import proyecto.intermodular.requestructure_api.repository.ClienteRepository;
import proyecto.intermodular.requestructure_api.repository.EmpresaRepository;
import proyecto.intermodular.requestructure_api.repository.RespuestaRepository;
import proyecto.intermodular.requestructure_api.repository.SolicitudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final SolicitudRepository solicitudRepository;
    private final RespuestaRepository respuestaRepository;

    public DatabaseSeeder(
            ClienteRepository clienteRepository,
            EmpresaRepository empresaRepository,
            SolicitudRepository solicitudRepository,
            RespuestaRepository respuestaRepository
    ) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
        this.solicitudRepository = solicitudRepository;
        this.respuestaRepository = respuestaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Cliente lucia = upsertCliente("lucia.martinez", "lucia@email.com", "Calle Mayor 12, Madrid");
        Empresa novapaint = upsertEmpresa("NovaPaint Empresas", "contacto@novapaint.com", "B12345671", "Poligono Sur 14, Madrid");

        if (solicitudRepository.count() > 0) {
            return;
        }

        Solicitud pintura = solicitud(
                lucia,
                "Problemas con la pintura de una pared exterior",
                "Necesito empresa para repintar una pared exterior con humedad y zonas levantadas.",
                "https://picsum.photos/seed/pintura-fachada/800/450",
                false,
                LocalDateTime.of(2026, 5, 1, 10, 15)
        );

        solicitudRepository.saveAll(List.of(pintura));

        respuestaRepository.saveAll(List.of(
                respuesta(pintura, novapaint, lucia, EstadoRespuesta.EN_ESPERA, "Podemos enviar presupuesto en 48h tras visita tecnica.", LocalDateTime.of(2026, 5, 2, 13, 10))
        ));
    }

    private Cliente upsertCliente(String username, String email, String direccion) {
        Optional<Cliente> existingOpt = clienteRepository.findByUsername(username);
        if (existingOpt.isPresent()) {
            Cliente existing = existingOpt.get();
            existing.setPassword(PasswordHasher.hash("123456"));
            return clienteRepository.save(existing);
        }
        Cliente c = new Cliente();
        c.setUsername(username);
        c.setEmail(email);
        c.setDireccion(direccion);
        c.setPassword(PasswordHasher.hash("123456"));
        return clienteRepository.save(c);
    }

    private Empresa upsertEmpresa(String nombre, String email, String nif, String direccion) {
        return empresaRepository.findByNif(nif)
                .orElseGet(() -> {
                    Empresa e = new Empresa();
                    e.setNombre(nombre);
                    e.setEmail(email);
                    e.setNif(nif);
                    e.setDireccion(direccion);
                    return empresaRepository.save(e);
                });
    }

    private Solicitud solicitud(
            Cliente cliente,
            String titulo,
            String contenido,
            String imagen,
            boolean privada,
            LocalDateTime fechaHora
    ) {
        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(cliente);
        solicitud.setTitulo(titulo);
        solicitud.setContenido(contenido);
        solicitud.setImagenes(List.of(imagen));
        solicitud.setPrivada(privada);
        solicitud.setFechaHora(fechaHora);
        solicitud.setEstado(EstadoSolicitud.ABIERTA);
        cliente.getSolicitudes().add(solicitud);
        return solicitud;
    }

    private Respuesta respuesta(
            Solicitud solicitud,
            Empresa empresa,
            Cliente cliente,
            EstadoRespuesta estado,
            String contenido,
            LocalDateTime fechaHora
    ) {
        Respuesta respuesta = new Respuesta();
        respuesta.setSolicitud(solicitud);
        respuesta.setEmpresa(empresa);
        respuesta.setCliente(cliente);
        respuesta.setEstado(estado);
        respuesta.setContenido(contenido);
        respuesta.setFechaHora(fechaHora);
        solicitud.getRespuestas().add(respuesta);
        empresa.getRespuestas().add(respuesta);
        cliente.getRespuestas().add(respuesta);
        return respuesta;
    }
}
