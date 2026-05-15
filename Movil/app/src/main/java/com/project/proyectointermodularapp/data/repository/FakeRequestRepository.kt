package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.ComentarioRespuestaModel
import com.project.proyectointermodularapp.domain.model.ComentarioSolicitudModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.EstadoRespuesta
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

class FakeRequestRepository : RequestRepository {

    private val clientes = listOf(
        ClienteModel(
            id = 1,
            username = "lucia.martinez",
            email = "lucia@email.com",
            direccion = "Calle Mayor 12, Madrid",
            password = "123456",
            solicitudesIds = listOf(1)
        ),
        ClienteModel(
            id = 2,
            username = "javier.romero",
            email = "javier@email.com",
            direccion = "Calle Prado 23, Madrid",
            password = "123456",
            solicitudesIds = listOf(2)
        ),
        ClienteModel(
            id = 3,
            username = "ines.aguilar",
            email = "ines@email.com",
            direccion = "Avenida Norte 8, Madrid",
            password = "123456",
            solicitudesIds = listOf(3)
        ),
        ClienteModel(
            id = 4,
            username = "sergio.mena",
            email = "sergio@email.com",
            direccion = "Calle Sol 99, Madrid",
            password = "123456",
            solicitudesIds = listOf(4)
        ),
        ClienteModel(
            id = 5,
            username = "claudia.vega",
            email = "claudia@email.com",
            direccion = "Calle Luna 4, Madrid",
            password = "123456",
            solicitudesIds = listOf(5)
        )
    )

    private val empresas = listOf(
        EmpresaModel(
            id = 101,
            nombre = "NovaPaint Empresas",
            email = "contacto@novapaint.com",
            nif = "B12345671",
            direccion = "Poligono Sur 14, Madrid"
        ),
        EmpresaModel(
            id = 102,
            nombre = "Fachadas Norte",
            email = "info@fachadasnorte.com",
            nif = "B12345672",
            direccion = "Calle Industria 3, Madrid"
        ),
        EmpresaModel(
            id = 103,
            nombre = "Delta Obra",
            email = "hola@deltaobra.com",
            nif = "B12345673",
            direccion = "Avenida Reforma 22, Madrid"
        ),
        EmpresaModel(
            id = 104,
            nombre = "Requestructure Proyectos",
            email = "equipo@requestructure.com",
            nif = "B12345674",
            direccion = "Calle Arquitectura 7, Madrid"
        ),
        EmpresaModel(
            id = 105,
            nombre = "DecoLinea Studio",
            email = "contacto@decolinea.com",
            nif = "B12345675",
            direccion = "Calle Diseno 18, Madrid"
        ),
        EmpresaModel(
            id = 106,
            nombre = "Espacio Vivo",
            email = "info@espaciovivo.com",
            nif = "B12345676",
            direccion = "Avenida Creativa 9, Madrid"
        )
    )

    private val solicitudes = listOf(
        SolicitudModel(
            id = 1,
            clienteId = 1,
            titulo = "Problemas con la pintura de una pared exterior",
            contenido = "Necesito empresa para repintar una pared exterior con humedad y zonas levantadas.",
            fechaHora = "2026-05-01 10:15",
            imagenes = listOf("https://picsum.photos/seed/pintura-fachada/800/450"),
            privada = false,
            comentarios = listOf(
                ComentarioSolicitudModel(
                    id = 1,
                    solicitudId = 1,
                    autor = "carlos.perez",
                    contenido = "A mi me funciono NovaPaint tras hacer saneado.",
                    fechaHora = "2026-05-02 09:00",
                    respuestas = listOf(
                        ComentarioSolicitudModel(
                            id = 2,
                            solicitudId = 1,
                            autor = "lucia.martinez",
                            contenido = "Gracias, justo algo asi buscaba.",
                            fechaHora = "2026-05-02 09:10"
                        )
                    )
                )
            )
        ),
        SolicitudModel(
            id = 2,
            clienteId = 2,
            titulo = "Reforma integral de piso de 85m2 en Madrid",
            contenido = "Necesito empresa para reforma integral de cocina, bano, suelo y electricidad.",
            fechaHora = "2026-05-03 18:45",
            imagenes = listOf("https://picsum.photos/seed/reforma-integral/800/450"),
            privada = true,
            comentarios = listOf(
                ComentarioSolicitudModel(
                    id = 3,
                    solicitudId = 2,
                    autor = "paula.navarro",
                    contenido = "Pide memoria de calidades y calendario por fases.",
                    fechaHora = "2026-05-03 19:20"
                )
            )
        ),
        SolicitudModel(
            id = 3,
            clienteId = 3,
            titulo = "Decoracion de oficina para estudio creativo",
            contenido = "Busco empresa para oficina de 120m2 con zonas colaborativas.",
            fechaHora = "2026-05-05 11:30",
            imagenes = listOf("https://picsum.photos/seed/decoracion-oficina/800/450"),
            privada = false,
            comentarios = listOf(
                ComentarioSolicitudModel(
                    id = 4,
                    solicitudId = 3,
                    autor = "mario.vidal",
                    contenido = "Define flujo de trabajo antes del diseno para evitar retrabajo.",
                    fechaHora = "2026-05-05 12:40"
                )
            )
        ),
        SolicitudModel(
            id = 4,
            clienteId = 4,
            titulo = "Adecuacion de local comercial y licencia de apertura",
            contenido = "Necesito empresa para adecuar local de 70m2 y tramitar licencia.",
            fechaHora = "2026-05-07 08:10",
            imagenes = listOf("https://picsum.photos/seed/local-comercial/800/450"),
            privada = true,
            comentarios = listOf(
                ComentarioSolicitudModel(
                    id = 5,
                    solicitudId = 4,
                    autor = "ana.ruiz",
                    contenido = "Revisa normativa municipal para evitar retrasos con documentacion.",
                    fechaHora = "2026-05-07 09:00"
                )
            )
        ),
        SolicitudModel(
            id = 5,
            clienteId = 5,
            titulo = "Home staging y puesta en venta de vivienda",
            contenido = "Busco empresa para home staging, reparaciones menores y asesoramiento.",
            fechaHora = "2026-05-09 16:05",
            imagenes = listOf("https://picsum.photos/seed/home-staging/800/450"),
            privada = false,
            comentarios = listOf(
                ComentarioSolicitudModel(
                    id = 6,
                    solicitudId = 5,
                    autor = "ruben.pastor",
                    contenido = "Suele ayudar mucho mejorar iluminacion antes de las fotos.",
                    fechaHora = "2026-05-09 17:20"
                )
            )
        )
    )

    private val respuestas = listOf(
        RespuestaModel(
            id = 1001,
            solicitudId = 1,
            empresaId = 101,
            clienteId = 1,
            estado = EstadoRespuesta.EN_ESPERA,
            contenido = "Podemos enviar presupuesto en 48h tras visita tecnica.",
            fechaHora = "2026-05-02 13:10",
            comentarios = listOf(
                ComentarioRespuestaModel(
                    id = 501,
                    respuestaId = 1001,
                    autor = "lucia.martinez",
                    contenido = "Perfecto. Podeis venir por la tarde?",
                    fechaHora = "2026-05-02 14:00"
                )
            )
        ),
        RespuestaModel(
            id = 1002,
            solicitudId = 1,
            empresaId = 102,
            clienteId = 1,
            estado = EstadoRespuesta.ACEPTADA,
            contenido = "Incluimos sellado de grietas y garantia de 3 anos.",
            fechaHora = "2026-05-02 13:30"
        ),
        RespuestaModel(
            id = 1003,
            solicitudId = 2,
            empresaId = 103,
            clienteId = 2,
            estado = EstadoRespuesta.EN_ESPERA,
            contenido = "Visita gratuita y plazo estimado de 10 semanas.",
            fechaHora = "2026-05-04 11:00"
        ),
        RespuestaModel(
            id = 1004,
            solicitudId = 2,
            empresaId = 104,
            clienteId = 2,
            estado = EstadoRespuesta.RECHAZADA,
            contenido = "No podemos ajustarnos al calendario solicitado.",
            fechaHora = "2026-05-04 11:30"
        ),
        RespuestaModel(
            id = 1005,
            solicitudId = 3,
            empresaId = 105,
            clienteId = 3,
            estado = EstadoRespuesta.EN_ESPERA,
            contenido = "Propuesta de layout y moodboard en 5 dias laborables.",
            fechaHora = "2026-05-06 10:15"
        ),
        RespuestaModel(
            id = 1006,
            solicitudId = 3,
            empresaId = 106,
            clienteId = 3,
            estado = EstadoRespuesta.EN_ESPERA,
            contenido = "Instalamos paneles acusticos, iluminacion led y mobiliario modular.",
            fechaHora = "2026-05-06 10:45"
        )
    )

    override suspend fun getClientes(): List<ClienteModel> = clientes

    override suspend fun getEmpresas(): List<EmpresaModel> = empresas

    override suspend fun getSolicitudes(): List<SolicitudModel> = solicitudes

    override suspend fun getRespuestas(): List<RespuestaModel> = respuestas
}

