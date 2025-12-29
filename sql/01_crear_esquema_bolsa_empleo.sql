-- ============================================================
-- ESQUEMA DE BASE DE DATOS - BOLSA DE EMPLEO
-- Registro de Postulante
-- PostgreSQL 16
-- ============================================================

-- Crear esquema si no existe
CREATE SCHEMA IF NOT EXISTS bolsa_empleo;
SET search_path TO bolsa_empleo, public;

-- ============================================================
-- TABLA PRINCIPAL: POSTULANTE
-- ============================================================
CREATE TABLE bolsa_empleo.postulante (
    id_postulante BIGSERIAL PRIMARY KEY,
    numero_cedula VARCHAR(10) NOT NULL UNIQUE,
    fecha_emision DATE NOT NULL, -- Usado como "contraseña" en fase de prueba
    nombres VARCHAR(100),
    apellidos VARCHAR(100),
    tipo_identificacion VARCHAR(50) DEFAULT 'Cédula ecuatoriana',
    fecha_nacimiento DATE,
    genero VARCHAR(20),
    estado_civil VARCHAR(50),
    autodefinicion_etnica VARCHAR(100),
    correo_electronico VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    celular VARCHAR(20),
    perfil_profesional TEXT,
    pais_residencia VARCHAR(100) DEFAULT 'Ecuador',
    provincia VARCHAR(100),
    canton VARCHAR(100),
    parroquia VARCHAR(100),
    direccion_domicilio TEXT,
    codigo_postal VARCHAR(10),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO', -- ACTIVO, INACTIVO, ELIMINADO
    CONSTRAINT chk_cedula CHECK (LENGTH(numero_cedula) = 10),
    CONSTRAINT chk_email CHECK (correo_electronico ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

-- Índices para búsquedas frecuentes
CREATE INDEX idx_postulante_cedula ON bolsa_empleo.postulante(numero_cedula);
CREATE INDEX idx_postulante_email ON bolsa_empleo.postulante(correo_electronico);
CREATE INDEX idx_postulante_estado ON bolsa_empleo.postulante(estado);

-- ============================================================
-- TABLA: FORMACIÓN ACADÉMICA
-- ============================================================
CREATE TABLE bolsa_empleo.formacion_academica (
    id_formacion BIGSERIAL PRIMARY KEY,
    id_postulante BIGINT NOT NULL,
    nivel_educativo VARCHAR(100) NOT NULL, -- Primaria, Secundaria, Técnico, Tecnológico, Universitario, Postgrado
    institucion VARCHAR(200) NOT NULL,
    titulo_obtenido VARCHAR(200),
    area_estudio VARCHAR(200),
    fecha_inicio DATE,
    fecha_fin DATE,
    estado_estudio VARCHAR(50), -- En curso, Graduado, Abandonado
    pais_estudio VARCHAR(100) DEFAULT 'Ecuador',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    CONSTRAINT fk_formacion_postulante FOREIGN KEY (id_postulante) 
        REFERENCES bolsa_empleo.postulante(id_postulante) ON DELETE CASCADE
);

CREATE INDEX idx_formacion_postulante ON bolsa_empleo.formacion_academica(id_postulante);
CREATE INDEX idx_formacion_nivel ON bolsa_empleo.formacion_academica(nivel_educativo);

-- ============================================================
-- TABLA: OCUPACIÓN
-- ============================================================
CREATE TABLE bolsa_empleo.ocupacion (
    id_ocupacion BIGSERIAL PRIMARY KEY,
    id_postulante BIGINT NOT NULL,
    ocupacion_actual VARCHAR(200),
    area_trabajo VARCHAR(200),
    tiempo_experiencia VARCHAR(50), -- Años de experiencia
    disponibilidad_inmediata BOOLEAN DEFAULT FALSE,
    disponibilidad_tiempo VARCHAR(50), -- Tiempo completo, Medio tiempo, Por horas
    salario_esperado DECIMAL(10,2),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    CONSTRAINT fk_ocupacion_postulante FOREIGN KEY (id_postulante) 
        REFERENCES bolsa_empleo.postulante(id_postulante) ON DELETE CASCADE
);

CREATE INDEX idx_ocupacion_postulante ON bolsa_empleo.ocupacion(id_postulante);

-- ============================================================
-- TABLA: EXPERIENCIA LABORAL
-- ============================================================
CREATE TABLE bolsa_empleo.experiencia_laboral (
    id_experiencia BIGSERIAL PRIMARY KEY,
    id_postulante BIGINT NOT NULL,
    nombre_empresa VARCHAR(200) NOT NULL,
    cargo VARCHAR(200) NOT NULL,
    area_trabajo VARCHAR(200),
    funciones TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE, -- NULL si es trabajo actual
    trabajo_actual BOOLEAN DEFAULT FALSE,
    motivo_salida VARCHAR(500),
    referencia_nombre VARCHAR(200),
    referencia_telefono VARCHAR(20),
    referencia_email VARCHAR(100),
    pais_trabajo VARCHAR(100) DEFAULT 'Ecuador',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    CONSTRAINT fk_experiencia_postulante FOREIGN KEY (id_postulante) 
        REFERENCES bolsa_empleo.postulante(id_postulante) ON DELETE CASCADE,
    CONSTRAINT chk_fechas_experiencia CHECK (fecha_fin IS NULL OR fecha_fin >= fecha_inicio)
);

CREATE INDEX idx_experiencia_postulante ON bolsa_empleo.experiencia_laboral(id_postulante);
CREATE INDEX idx_experiencia_fechas ON bolsa_empleo.experiencia_laboral(fecha_inicio, fecha_fin);

-- ============================================================
-- TABLA: CURSOS Y CERTIFICACIONES
-- ============================================================
CREATE TABLE bolsa_empleo.curso_certificacion (
    id_curso BIGSERIAL PRIMARY KEY,
    id_postulante BIGINT NOT NULL,
    nombre_curso VARCHAR(200) NOT NULL,
    institucion VARCHAR(200),
    tipo VARCHAR(50), -- Curso, Taller, Certificación, Diplomado
    area_estudio VARCHAR(200),
    fecha_inicio DATE,
    fecha_fin DATE,
    horas_duracion INTEGER,
    certificado BOOLEAN DEFAULT FALSE,
    numero_certificado VARCHAR(100),
    fecha_vencimiento DATE,
    pais_curso VARCHAR(100) DEFAULT 'Ecuador',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    CONSTRAINT fk_curso_postulante FOREIGN KEY (id_postulante) 
        REFERENCES bolsa_empleo.postulante(id_postulante) ON DELETE CASCADE
);

CREATE INDEX idx_curso_postulante ON bolsa_empleo.curso_certificacion(id_postulante);
CREATE INDEX idx_curso_tipo ON bolsa_empleo.curso_certificacion(tipo);

-- ============================================================
-- TABLA: DISCAPACIDADES
-- ============================================================
CREATE TABLE bolsa_empleo.discapacidad (
    id_discapacidad BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ACTIVO'
);

-- Tabla de relación: Postulante - Discapacidad (Muchos a Muchos)
CREATE TABLE bolsa_empleo.postulante_discapacidad (
    id_postulante BIGINT NOT NULL,
    id_discapacidad BIGINT NOT NULL,
    porcentaje_discapacidad INTEGER, -- 0-100
    certificado_discapacidad BOOLEAN DEFAULT FALSE,
    numero_certificado VARCHAR(100),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_postulante, id_discapacidad),
    CONSTRAINT fk_pd_postulante FOREIGN KEY (id_postulante) 
        REFERENCES bolsa_empleo.postulante(id_postulante) ON DELETE CASCADE,
    CONSTRAINT fk_pd_discapacidad FOREIGN KEY (id_discapacidad) 
        REFERENCES bolsa_empleo.discapacidad(id_discapacidad) ON DELETE CASCADE,
    CONSTRAINT chk_porcentaje CHECK (porcentaje_discapacidad >= 0 AND porcentaje_discapacidad <= 100)
);

CREATE INDEX idx_pd_postulante ON bolsa_empleo.postulante_discapacidad(id_postulante);
CREATE INDEX idx_pd_discapacidad ON bolsa_empleo.postulante_discapacidad(id_discapacidad);

-- ============================================================
-- DATOS INICIALES: DISCAPACIDADES
-- ============================================================
INSERT INTO bolsa_empleo.discapacidad (codigo, nombre, descripcion) VALUES
('FISICA', 'Discapacidad Física', 'Discapacidad relacionada con movilidad o funciones físicas'),
('VISUAL', 'Discapacidad Visual', 'Discapacidad relacionada con la visión'),
('AUDITIVA', 'Discapacidad Auditiva', 'Discapacidad relacionada con la audición'),
('INTELECTUAL', 'Discapacidad Intelectual', 'Discapacidad relacionada con el desarrollo intelectual'),
('PSICOSOCIAL', 'Discapacidad Psicosocial', 'Discapacidad relacionada con la salud mental'),
('MULTIPLE', 'Discapacidad Múltiple', 'Presencia de dos o más discapacidades');

-- ============================================================
-- FUNCIONES: ACTUALIZAR FECHA_ACTUALIZACION
-- ============================================================
CREATE OR REPLACE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.fecha_actualizacion = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers para actualizar fecha_actualizacion automáticamente
CREATE TRIGGER trg_postulante_actualizar
    BEFORE UPDATE ON bolsa_empleo.postulante
    FOR EACH ROW
    EXECUTE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion();

CREATE TRIGGER trg_formacion_actualizar
    BEFORE UPDATE ON bolsa_empleo.formacion_academica
    FOR EACH ROW
    EXECUTE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion();

CREATE TRIGGER trg_ocupacion_actualizar
    BEFORE UPDATE ON bolsa_empleo.ocupacion
    FOR EACH ROW
    EXECUTE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion();

CREATE TRIGGER trg_experiencia_actualizar
    BEFORE UPDATE ON bolsa_empleo.experiencia_laboral
    FOR EACH ROW
    EXECUTE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion();

CREATE TRIGGER trg_curso_actualizar
    BEFORE UPDATE ON bolsa_empleo.curso_certificacion
    FOR EACH ROW
    EXECUTE FUNCTION bolsa_empleo.actualizar_fecha_actualizacion();

-- ============================================================
-- COMENTARIOS EN TABLAS Y COLUMNAS
-- ============================================================
COMMENT ON SCHEMA bolsa_empleo IS 'Esquema para el sistema de Bolsa de Empleo - Registro de Postulantes';
COMMENT ON TABLE bolsa_empleo.postulante IS 'Tabla principal de postulantes';
COMMENT ON COLUMN bolsa_empleo.postulante.fecha_emision IS 'Fecha de emisión de la cédula - usado como "contraseña" en fase de prueba';
COMMENT ON TABLE bolsa_empleo.formacion_academica IS 'Formación académica del postulante';
COMMENT ON TABLE bolsa_empleo.ocupacion IS 'Información de ocupación y disponibilidad laboral';
COMMENT ON TABLE bolsa_empleo.experiencia_laboral IS 'Experiencia laboral del postulante';
COMMENT ON TABLE bolsa_empleo.curso_certificacion IS 'Cursos y certificaciones del postulante';
COMMENT ON TABLE bolsa_empleo.discapacidad IS 'Catálogo de tipos de discapacidades';
COMMENT ON TABLE bolsa_empleo.postulante_discapacidad IS 'Relación entre postulantes y discapacidades';

-- ============================================================
-- FIN DEL SCRIPT
-- ============================================================


