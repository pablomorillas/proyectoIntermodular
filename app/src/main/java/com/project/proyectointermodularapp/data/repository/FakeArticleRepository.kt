package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ArticleModel
import com.project.proyectointermodularapp.domain.model.CommentModel

class FakeArticleRepository : ArticleRepository {

    override suspend fun getArticles(): List<ArticleModel> {
        return listOf(
            ArticleModel(
                id = 1,
                title = "Problemas con la pintura de una pared exterior",
                content = "Hola, necesito una empresa para repintar una pared exterior que tiene humedad y zonas levantadas. Busco un equipo que revise primero la causa y luego pinte con material resistente al agua.",
                author = "Lucia Martinez",
                date = "2026-05-01",
                imageUrl = "https://picsum.photos/seed/pintura-fachada/800/450",
                comments = listOf(
                    CommentModel(
                        id = 1,
                        author = "Carlos Perez",
                        message = "A mi me funciono NovaPaint. Primero hicieron saneado y despues dos capas con pintura impermeable.",
                        date = "2026-05-02",
                        replies = listOf(
                            CommentModel(
                                id = 2,
                                author = "Lucia Martinez",
                                message = "Gracias, justo eso estaba buscando.",
                                date = "2026-05-02"
                            )
                        )
                    )
                ),
                responses = listOf(
                    "NovaPaint Empresas: Podemos enviar presupuesto en 48h tras visita tecnica.",
                    "Fachadas Norte: Incluimos sellado de grietas y garantia de 3 anos."
                )
            ),
            ArticleModel(
                id = 2,
                title = "Reforma integral de piso de 85m2 en Madrid",
                content = "Necesito empresa constructora para reforma integral: cocina, bano, suelo y electricidad. Tengo presupuesto aproximado de 52.000 EUR y me gustaria empezar en dos meses.",
                author = "Javier Romero",
                date = "2026-05-03",
                imageUrl = "https://picsum.photos/seed/reforma-integral/800/450",
                comments = listOf(
                    CommentModel(
                        id = 3,
                        author = "Paula Navarro",
                        message = "Pide memoria de calidades y calendario por fases antes de firmar.",
                        date = "2026-05-03",
                        replies = listOf(
                            CommentModel(
                                id = 4,
                                author = "Javier Romero",
                                message = "Buena idea, lo voy a incluir en la solicitud.",
                                date = "2026-05-04"
                            )
                        )
                    )
                ),
                responses = listOf(
                    "Delta Obra: Visita gratuita, plazo estimado 10 semanas con jefe de obra asignado.",
                    "Requestructure Proyectos: Incluimos renders 3D y tres opciones de materiales dentro de tu presupuesto."
                )
            ),
            ArticleModel(
                id = 3,
                title = "Decoracion de oficina para estudio creativo",
                content = "Busco empresa de decoracion para oficina de 120m2. Queremos un estilo moderno, zonas colaborativas y una sala de reuniones aislada acusticamente.",
                author = "Ines Aguilar",
                date = "2026-05-05",
                imageUrl = "https://picsum.photos/seed/decoracion-oficina/800/450",
                comments = listOf(
                    CommentModel(
                        id = 5,
                        author = "Mario Vidal",
                        message = "Te recomiendo definir primero flujo de trabajo para que el diseno no quede solo bonito.",
                        date = "2026-05-05",
                        replies = listOf(
                            CommentModel(
                                id = 6,
                                author = "Ines Aguilar",
                                message = "Totalmente, lo estamos documentando con el equipo.",
                                date = "2026-05-06"
                            )
                        )
                    )
                ),
                responses = listOf(
                    "DecoLinea Studio: Propuesta de layout + moodboard en 5 dias laborables.",
                    "Espacio Vivo: Instalamos paneles acusticos, iluminacion led y mobiliario modular."
                )
            ),
            ArticleModel(
                id = 4,
                title = "Adecuacion de local comercial y licencia de apertura",
                content = "Necesito ayuda para adecuar un local de 70m2 para cafeteria: salida de humos, accesibilidad y tramite de licencia. Preferencia por empresas que gestionen obra + papeleo.",
                author = "Sergio Mena",
                date = "2026-05-07",
                imageUrl = "https://picsum.photos/seed/local-comercial/800/450",
                comments = listOf(
                    CommentModel(
                        id = 7,
                        author = "Ana Ruiz",
                        message = "Revisa bien normativa municipal porque ahi se va mucho tiempo si falta documentacion.",
                        date = "2026-05-07",
                        replies = listOf(
                            CommentModel(
                                id = 8,
                                author = "Sergio Mena",
                                message = "Gracias, justo quiero evitar retrasos con licencias.",
                                date = "2026-05-07"
                            )
                        )
                    )
                ),
                responses = listOf(
                    "ObraLegal Partners: Gestionamos proyecto tecnico y coordinacion con ayuntamiento.",
                    "Civika Construccion: Entregamos obra llave en mano y plan de ejecucion por hitos."
                )
            ),
            ArticleModel(
                id = 5,
                title = "Home staging y puesta en venta de vivienda",
                content = "Quiero preparar una vivienda para venderla en menos de 3 meses. Busco empresa que haga home staging, pequenas reparaciones y asesoramiento inmobiliario.",
                author = "Claudia Vega",
                date = "2026-05-09",
                imageUrl = "https://picsum.photos/seed/home-staging/800/450",
                comments = listOf(
                    CommentModel(
                        id = 9,
                        author = "Ruben Pastor",
                        message = "En mi caso funciono mucho mejorar iluminacion y pintar en tonos neutros antes de las fotos.",
                        date = "2026-05-09",
                        replies = listOf(
                            CommentModel(
                                id = 10,
                                author = "Claudia Vega",
                                message = "Perfecto, era justo el tipo de consejo que necesitaba.",
                                date = "2026-05-10"
                            )
                        )
                    )
                ),
                responses = listOf(
                    "CasaLista Pro: Incluimos staging, reportaje fotografico y publicacion en portales.",
                    "UrbanHome Advisors: Plan comercial con precio objetivo y seguimiento semanal."
                )
            )
        )
    }
}
