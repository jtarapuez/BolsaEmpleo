-- ============================================================
-- DATOS DE PRUEBA - BOLSA DE EMPLEO
-- ============================================================

SET search_path TO bolsa_empleo, public;

-- Insertar postulante de prueba
INSERT INTO bolsa_empleo.postulante (
    numero_cedula,
    fecha_emision,
    nombres,
    apellidos,
    tipo_identificacion,
    fecha_nacimiento,
    genero,
    estado_civil,
    autodefinicion_etnica,
    correo_electronico,
    telefono,
    celular,
    perfil_profesional,
    provincia,
    canton,
    parroquia,
    direccion_domicilio
) VALUES (
    '1234567890',
    '2010-05-15',
    'JUAN PABLO',
    'PEREZ GARCIA',
    'Cédula ecuatoriana',
    '1990-03-20',
    'Masculino',
    'Soltero',
    'Mestizo',
    'juan.perez@example.com',
    '022345678',
    '0991234567',
    'Ingeniero de Sistemas con 5 años de experiencia en desarrollo web y aplicaciones empresariales. Especializado en Java EE y bases de datos.',
    'Pichincha',
    'Quito',
    'Centro Histórico',
    'Av. Amazonas N12-34 y Colón'
);

-- Obtener el ID del postulante insertado
DO $$
DECLARE
    v_id_postulante BIGINT;
BEGIN
    SELECT id_postulante INTO v_id_postulante 
    FROM bolsa_empleo.postulante 
    WHERE numero_cedula = '1234567890';

    -- Formación académica
    INSERT INTO bolsa_empleo.formacion_academica (
        id_postulante,
        nivel_educativo,
        institucion,
        titulo_obtenido,
        area_estudio,
        fecha_inicio,
        fecha_fin,
        estado_estudio
    ) VALUES
    (v_id_postulante, 'Universitario', 'Escuela Politécnica Nacional', 'Ingeniero en Sistemas', 'Ingeniería de Sistemas', '2008-09-01', '2013-07-15', 'Graduado'),
    (v_id_postulante, 'Técnico', 'Instituto Tecnológico Superior', 'Técnico en Programación', 'Informática', '2006-09-01', '2008-06-30', 'Graduado');

    -- Ocupación
    INSERT INTO bolsa_empleo.ocupacion (
        id_postulante,
        ocupacion_actual,
        area_trabajo,
        tiempo_experiencia,
        disponibilidad_inmediata,
        disponibilidad_tiempo,
        salario_esperado
    ) VALUES (
        v_id_postulante,
        'Desarrollador de Software',
        'Tecnologías de la Información',
        '5 años',
        TRUE,
        'Tiempo completo',
        1500.00
    );

    -- Experiencia laboral
    INSERT INTO bolsa_empleo.experiencia_laboral (
        id_postulante,
        nombre_empresa,
        cargo,
        area_trabajo,
        funciones,
        fecha_inicio,
        fecha_fin,
        trabajo_actual,
        pais_trabajo
    ) VALUES
    (v_id_postulante, 'Tech Solutions S.A.', 'Desarrollador Senior', 'Desarrollo de Software', 'Desarrollo de aplicaciones web con Java EE, mantenimiento de sistemas legacy, liderazgo de equipo de desarrollo', '2019-01-15', NULL, TRUE, 'Ecuador'),
    (v_id_postulante, 'Sistemas Empresariales', 'Desarrollador Junior', 'Desarrollo de Software', 'Desarrollo de módulos en Java, soporte técnico, pruebas de software', '2015-03-01', '2018-12-31', FALSE, 'Ecuador');

    -- Cursos y certificaciones
    INSERT INTO bolsa_empleo.curso_certificacion (
        id_postulante,
        nombre_curso,
        institucion,
        tipo,
        area_estudio,
        fecha_inicio,
        fecha_fin,
        horas_duracion,
        certificado,
        numero_certificado
    ) VALUES
    (v_id_postulante, 'Certificación Java EE 7', 'Oracle University', 'Certificación', 'Programación Java', '2020-06-01', '2020-08-30', 120, TRUE, 'OCP-JAVA-EE-12345'),
    (v_id_postulante, 'Curso de PostgreSQL Avanzado', 'Platzi', 'Curso', 'Bases de Datos', '2021-03-15', '2021-05-20', 40, TRUE, 'PLATZI-PG-67890'),
    (v_id_postulante, 'Diplomado en Arquitectura de Software', 'Universidad Central', 'Diplomado', 'Arquitectura de Software', '2019-09-01', '2020-02-28', 200, TRUE, 'DIP-ARQ-2020-001');

END $$;

-- Verificar datos insertados
SELECT 
    p.numero_cedula,
    p.nombres || ' ' || p.apellidos AS nombre_completo,
    p.correo_electronico,
    COUNT(DISTINCT f.id_formacion) AS total_formaciones,
    COUNT(DISTINCT e.id_experiencia) AS total_experiencias,
    COUNT(DISTINCT c.id_curso) AS total_cursos
FROM bolsa_empleo.postulante p
LEFT JOIN bolsa_empleo.formacion_academica f ON p.id_postulante = f.id_postulante
LEFT JOIN bolsa_empleo.experiencia_laboral e ON p.id_postulante = e.id_postulante
LEFT JOIN bolsa_empleo.curso_certificacion c ON p.id_postulante = c.id_postulante
WHERE p.numero_cedula = '1234567890'
GROUP BY p.id_postulante, p.numero_cedula, p.nombres, p.apellidos, p.correo_electronico;


