CREATE DATABASE IF NOT EXISTS requestructure_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'requestructure'@'localhost' IDENTIFIED BY 'requestructure';
GRANT ALL PRIVILEGES ON requestructure_db.* TO 'requestructure'@'localhost';
FLUSH PRIVILEGES;

USE requestructure_db;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS comentarios_respuesta;
DROP TABLE IF EXISTS comentarios_solicitud;
DROP TABLE IF EXISTS respuestas;
DROP TABLE IF EXISTS solicitud_imagenes;
DROP TABLE IF EXISTS solicitudes;
DROP TABLE IF EXISTS empresas;
DROP TABLE IF EXISTS clientes;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE clientes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(80) NOT NULL,
    email VARCHAR(120) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_cliente_username UNIQUE (username),
    CONSTRAINT uk_cliente_email UNIQUE (email)
);

CREATE TABLE empresas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    nif VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_empresa_email UNIQUE (email),
    CONSTRAINT uk_empresa_nif UNIQUE (nif)
);

CREATE TABLE solicitudes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    titulo VARCHAR(160) NOT NULL,
    contenido VARCHAR(3000) NOT NULL,
    fecha_hora DATETIME(6) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    privada BIT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_solicitudes_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);

CREATE TABLE solicitud_imagenes (
    solicitud_id BIGINT NOT NULL,
    url_imagen VARCHAR(500) NOT NULL,
    CONSTRAINT fk_solicitud_imagenes_solicitud
        FOREIGN KEY (solicitud_id) REFERENCES solicitudes (id)
);

CREATE TABLE respuestas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    solicitud_id BIGINT NOT NULL,
    empresa_id BIGINT NOT NULL,
    cliente_id BIGINT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    contenido VARCHAR(3000) NOT NULL,
    fecha_hora DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_respuestas_solicitud
        FOREIGN KEY (solicitud_id) REFERENCES solicitudes (id),
    CONSTRAINT fk_respuestas_empresa
        FOREIGN KEY (empresa_id) REFERENCES empresas (id),
    CONSTRAINT fk_respuestas_cliente
        FOREIGN KEY (cliente_id) REFERENCES clientes (id)
);

CREATE TABLE comentarios_solicitud (
    id BIGINT NOT NULL AUTO_INCREMENT,
    solicitud_id BIGINT NOT NULL,
    comentario_padre_id BIGINT NULL,
    autor VARCHAR(80) NOT NULL,
    contenido VARCHAR(1500) NOT NULL,
    fecha_hora DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_comentarios_solicitud_solicitud
        FOREIGN KEY (solicitud_id) REFERENCES solicitudes (id),
    CONSTRAINT fk_comentarios_solicitud_padre
        FOREIGN KEY (comentario_padre_id) REFERENCES comentarios_solicitud (id)
);

CREATE TABLE comentarios_respuesta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    respuesta_id BIGINT NOT NULL,
    comentario_padre_id BIGINT NULL,
    autor VARCHAR(80) NOT NULL,
    contenido VARCHAR(1500) NOT NULL,
    fecha_hora DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_comentarios_respuesta_respuesta
        FOREIGN KEY (respuesta_id) REFERENCES respuestas (id),
    CONSTRAINT fk_comentarios_respuesta_padre
        FOREIGN KEY (comentario_padre_id) REFERENCES comentarios_respuesta (id)
);

INSERT INTO clientes (id, username, email, direccion, password) VALUES
    (1, 'lucia.martinez', 'lucia@email.com', 'Calle Mayor 12, Madrid', 'g84KhRxsV8MG8CDFQwaVHLSt2f6Oc1EXwZzf9+ia1a0='),
    (2, 'javier.romero', 'javier@email.com', 'Calle Prado 23, Madrid', 'g84KhRxsV8MG8CDFQwaVHLSt2f6Oc1EXwZzf9+ia1a0='),
    (3, 'ines.aguilar', 'ines@email.com', 'Avenida Norte 8, Madrid', 'g84KhRxsV8MG8CDFQwaVHLSt2f6Oc1EXwZzf9+ia1a0='),
    (4, 'sergio.mena', 'sergio@email.com', 'Calle Sol 99, Madrid', 'g84KhRxsV8MG8CDFQwaVHLSt2f6Oc1EXwZzf9+ia1a0='),
    (5, 'claudia.vega', 'claudia@email.com', 'Calle Luna 4, Madrid', 'g84KhRxsV8MG8CDFQwaVHLSt2f6Oc1EXwZzf9+ia1a0=');

INSERT INTO empresas (id, nombre, email, nif, direccion) VALUES
    (101, 'NovaPaint Empresas', 'contacto@novapaint.com', 'B12345671', 'Poligono Sur 14, Madrid'),
    (102, 'Fachadas Norte', 'info@fachadasnorte.com', 'B12345672', 'Calle Industria 3, Madrid'),
    (103, 'Delta Obra', 'hola@deltaobra.com', 'B12345673', 'Avenida Reforma 22, Madrid'),
    (104, 'Requestructure Proyectos', 'equipo@requestructure.com', 'B12345674', 'Calle Arquitectura 7, Madrid'),
    (105, 'DecoLinea Studio', 'contacto@decolinea.com', 'B12345675', 'Calle Diseno 18, Madrid'),
    (106, 'Espacio Vivo', 'info@espaciovivo.com', 'B12345676', 'Avenida Creativa 9, Madrid');

INSERT INTO solicitudes (id, cliente_id, titulo, contenido, fecha_hora, estado, privada) VALUES
    (1, 1, 'Problemas con la pintura de una pared exterior', 'Necesito empresa para repintar una pared exterior con humedad y zonas levantadas.', '2026-05-01 10:15:00', 'ABIERTA', 0),
    (2, 2, 'Reforma integral de piso de 85m2 en Madrid', 'Necesito empresa para reforma integral de cocina, bano, suelo y electricidad.', '2026-05-03 18:45:00', 'ABIERTA', 1),
    (3, 3, 'Decoracion de oficina para estudio creativo', 'Busco empresa para oficina de 120m2 con zonas colaborativas.', '2026-05-05 11:30:00', 'ABIERTA', 0),
    (4, 4, 'Adecuacion de local comercial y licencia de apertura', 'Necesito empresa para adecuar local de 70m2 y tramitar licencia.', '2026-05-07 08:10:00', 'ABIERTA', 1),
    (5, 5, 'Home staging y puesta en venta de vivienda', 'Busco empresa para home staging, reparaciones menores y asesoramiento.', '2026-05-09 16:05:00', 'ABIERTA', 0);

INSERT INTO solicitud_imagenes (solicitud_id, url_imagen) VALUES
    (1, 'https://picsum.photos/seed/pintura-fachada/800/450'),
    (2, 'https://picsum.photos/seed/reforma-integral/800/450'),
    (3, 'https://picsum.photos/seed/decoracion-oficina/800/450'),
    (4, 'https://picsum.photos/seed/local-comercial/800/450'),
    (5, 'https://picsum.photos/seed/home-staging/800/450');

INSERT INTO comentarios_solicitud (id, solicitud_id, comentario_padre_id, autor, contenido, fecha_hora) VALUES
    (1, 1, NULL, 'carlos.perez', 'A mi me funciono NovaPaint tras hacer saneado.', '2026-05-02 09:00:00'),
    (2, 1, 1, 'lucia.martinez', 'Gracias, justo algo asi buscaba.', '2026-05-02 09:10:00'),
    (3, 2, NULL, 'paula.navarro', 'Pide memoria de calidades y calendario por fases.', '2026-05-03 19:20:00'),
    (4, 3, NULL, 'mario.vidal', 'Define flujo de trabajo antes del diseno para evitar retrabajo.', '2026-05-05 12:40:00'),
    (5, 4, NULL, 'ana.ruiz', 'Revisa normativa municipal para evitar retrasos con documentacion.', '2026-05-07 09:00:00'),
    (6, 5, NULL, 'ruben.pastor', 'Suele ayudar mucho mejorar iluminacion antes de las fotos.', '2026-05-09 17:20:00');

INSERT INTO respuestas (id, solicitud_id, empresa_id, cliente_id, estado, contenido, fecha_hora) VALUES
    (1001, 1, 101, 1, 'EN_ESPERA', 'Podemos enviar presupuesto en 48h tras visita tecnica.', '2026-05-02 13:10:00'),
    (1002, 1, 102, 1, 'ACEPTADA', 'Incluimos sellado de grietas y garantia de 3 anos.', '2026-05-02 13:30:00'),
    (1003, 2, 103, 2, 'EN_ESPERA', 'Visita gratuita y plazo estimado de 10 semanas.', '2026-05-04 11:00:00'),
    (1004, 2, 104, 2, 'RECHAZADA', 'No podemos ajustarnos al calendario solicitado.', '2026-05-04 11:30:00'),
    (1005, 3, 105, 3, 'EN_ESPERA', 'Propuesta de layout y moodboard en 5 dias laborables.', '2026-05-06 10:15:00'),
    (1006, 3, 106, 3, 'EN_ESPERA', 'Instalamos paneles acusticos, iluminacion led y mobiliario modular.', '2026-05-06 10:45:00');

INSERT INTO comentarios_respuesta (id, respuesta_id, comentario_padre_id, autor, contenido, fecha_hora) VALUES
    (501, 1001, NULL, 'lucia.martinez', 'Perfecto. Podeis venir por la tarde?', '2026-05-02 14:00:00');
