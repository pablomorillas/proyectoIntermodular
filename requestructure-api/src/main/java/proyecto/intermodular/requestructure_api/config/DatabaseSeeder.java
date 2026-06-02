package proyecto.intermodular.requestructure_api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import proyecto.intermodular.requestructure_api.config.PasswordHasher;
import proyecto.intermodular.requestructure_api.domain.Cliente;
import proyecto.intermodular.requestructure_api.domain.ComentarioRespuesta;
import proyecto.intermodular.requestructure_api.domain.ComentarioSolicitud;
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
        List<Cliente> clientes = List.of(
                upsertCliente("lucia.martinez", "lucia@email.com", "Calle Mayor 12, Madrid"),
                upsertCliente("javier.romero", "javier@email.com", "Calle Prado 23, Madrid"),
                upsertCliente("ines.aguilar", "ines@email.com", "Avenida Norte 8, Madrid"),
                upsertCliente("sergio.mena", "sergio@email.com", "Calle Sol 99, Madrid"),
                upsertCliente("claudia.vega", "claudia@email.com", "Calle Luna 4, Madrid")
        );

        List<Empresa> empresas = List.of(
                upsertEmpresa("NovaPaint Empresas", "contacto@novapaint.com", "B12345671", "Poligono Sur 14, Madrid"),
                upsertEmpresa("Fachadas Norte", "info@fachadasnorte.com", "B12345672", "Calle Industria 3, Madrid"),
                upsertEmpresa("Delta Obra", "hola@deltaobra.com", "B12345673", "Avenida Reforma 22, Madrid"),
                upsertEmpresa("Requestructure Proyectos", "equipo@requestructure.com", "B12345674", "Calle Arquitectura 7, Madrid"),
                upsertEmpresa("DecoLinea Studio", "contacto@decolinea.com", "B12345675", "Calle Diseno 18, Madrid"),
                upsertEmpresa("Espacio Vivo", "info@espaciovivo.com", "B12345676", "Avenida Creativa 9, Madrid")
        );

        if (solicitudRepository.count() > 0) {
            return;
        }

        Solicitud pintura = solicitud(
                clientes.get(0),
                "Problemas con la pintura de una pared exterior",
                "Necesito empresa para repintar una pared exterior con humedad y zonas levantadas.",
                "https://picsum.photos/seed/pintura-fachada/800/450",
                false,
                LocalDateTime.of(2026, 5, 1, 10, 15)
        );
        ComentarioSolicitud pinturaComentario = comentarioSolicitud(
                pintura,
                null,
                "carlos.perez",
                "A mi me funciono NovaPaint tras hacer saneado.",
                LocalDateTime.of(2026, 5, 2, 9, 0)
        );
        comentarioSolicitud(
                pintura,
                pinturaComentario,
                "lucia.martinez",
                "Gracias, justo algo asi buscaba.",
                LocalDateTime.of(2026, 5, 2, 9, 10)
        );

        Solicitud reforma = solicitud(
                clientes.get(1),
                "Reforma integral de piso de 85m2 en Madrid",
                "Necesito empresa para reforma integral de cocina, bano, suelo y electricidad.",
                "https://picsum.photos/seed/reforma-integral/800/450",
                false,
                LocalDateTime.of(2026, 5, 3, 18, 45)
        );
        comentarioSolicitud(
                reforma,
                null,
                "paula.navarro",
                "Pide memoria de calidades y calendario por fases.",
                LocalDateTime.of(2026, 5, 3, 19, 20)
        );

        Solicitud oficina = solicitud(
                clientes.get(2),
                "Decoracion de oficina para estudio creativo",
                "Busco empresa para oficina de 120m2 con zonas colaborativas.",
                "https://picsum.photos/seed/decoracion-oficina/800/450",
                false,
                LocalDateTime.of(2026, 5, 5, 11, 30)
        );
        comentarioSolicitud(
                oficina,
                null,
                "mario.vidal",
                "Define flujo de trabajo antes del diseno para evitar retrabajo.",
                LocalDateTime.of(2026, 5, 5, 12, 40)
        );

        Solicitud local = solicitud(
                clientes.get(3),
                "Adecuacion de local comercial y licencia de apertura",
                "Necesito empresa para adecuar local de 70m2 y tramitar licencia.",
                "https://picsum.photos/seed/local-comercial/800/450",
                false,
                LocalDateTime.of(2026, 5, 7, 8, 10)
        );
        comentarioSolicitud(
                local,
                null,
                "ana.ruiz",
                "Revisa normativa municipal para evitar retrasos con documentacion.",
                LocalDateTime.of(2026, 5, 7, 9, 0)
        );

        Solicitud homeStaging = solicitud(
                clientes.get(4),
                "Home staging y puesta en venta de vivienda",
                "Busco empresa para home staging, reparaciones menores y asesoramiento.",
                "https://picsum.photos/seed/home-staging/800/450",
                false,
                LocalDateTime.of(2026, 5, 9, 16, 5)
        );
        comentarioSolicitud(
                homeStaging,
                null,
                "ruben.pastor",
                "Suele ayudar mucho mejorar iluminacion antes de las fotos.",
                LocalDateTime.of(2026, 5, 9, 17, 20)
        );

        solicitudRepository.saveAll(List.of(pintura, reforma, oficina, local, homeStaging));

        Respuesta respuestaPintura = respuesta(
                pintura,
                empresas.get(0),
                clientes.get(0),
                EstadoRespuesta.EN_ESPERA,
                "Podemos enviar presupuesto en 48h tras visita tecnica.",
                LocalDateTime.of(2026, 5, 2, 13, 10)
        );
        comentarioRespuesta(
                respuestaPintura,
                null,
                "lucia.martinez",
                "Perfecto. Podeis venir por la tarde?",
                LocalDateTime.of(2026, 5, 2, 14, 0)
        );

        respuestaRepository.saveAll(List.of(
                respuestaPintura,
                respuesta(pintura, empresas.get(1), clientes.get(0), EstadoRespuesta.ACEPTADA, "Incluimos sellado de grietas y garantia de 3 anos.", LocalDateTime.of(2026, 5, 2, 13, 30)),
                respuesta(reforma, empresas.get(2), clientes.get(1), EstadoRespuesta.EN_ESPERA, "Visita gratuita y plazo estimado de 10 semanas.", LocalDateTime.of(2026, 5, 4, 11, 0)),
                respuesta(reforma, empresas.get(3), clientes.get(1), EstadoRespuesta.RECHAZADA, "No podemos ajustarnos al calendario solicitado.", LocalDateTime.of(2026, 5, 4, 11, 30)),
                respuesta(oficina, empresas.get(4), clientes.get(2), EstadoRespuesta.EN_ESPERA, "Propuesta de layout y moodboard en 5 dias laborables.", LocalDateTime.of(2026, 5, 6, 10, 15)),
                respuesta(oficina, empresas.get(5), clientes.get(2), EstadoRespuesta.EN_ESPERA, "Instalamos paneles acusticos, iluminacion led y mobiliario modular.", LocalDateTime.of(2026, 5, 6, 10, 45))
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

    private ComentarioSolicitud comentarioSolicitud(
            Solicitud solicitud,
            ComentarioSolicitud comentarioPadre,
            String autor,
            String contenido,
            LocalDateTime fechaHora
    ) {
        ComentarioSolicitud comentario = new ComentarioSolicitud();
        comentario.setSolicitud(solicitud);
        comentario.setComentarioPadre(comentarioPadre);
        comentario.setAutor(autor);
        comentario.setContenido(contenido);
        comentario.setFechaHora(fechaHora);

        if (comentarioPadre == null) {
            solicitud.getComentarios().add(comentario);
        } else {
            comentarioPadre.getRespuestas().add(comentario);
        }

        return comentario;
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

    private ComentarioRespuesta comentarioRespuesta(
            Respuesta respuesta,
            ComentarioRespuesta comentarioPadre,
            String autor,
            String contenido,
            LocalDateTime fechaHora
    ) {
        ComentarioRespuesta comentario = new ComentarioRespuesta();
        comentario.setRespuesta(respuesta);
        comentario.setComentarioPadre(comentarioPadre);
        comentario.setAutor(autor);
        comentario.setContenido(contenido);
        comentario.setFechaHora(fechaHora);

        if (comentarioPadre == null) {
            respuesta.getComentarios().add(comentario);
        } else {
            comentarioPadre.getRespuestas().add(comentario);
        }

        return comentario;
    }
}
