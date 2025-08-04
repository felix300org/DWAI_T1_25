-- ====================================
-- SISTEMA ACADÉMICO - MySQL
-- Base de datos con herencia JPA y campos de auditoría
-- Evaluación DAWI - Pregunta 01 y 02
-- ====================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS sistema_academico;
USE sistema_academico;

-- ====================================
-- PREGUNTA 01: HERENCIA EN JPA
-- Caso de Negocio: Sistema de Personas en una Universidad
-- Entidad Padre: Persona
-- Entidades Hijas: Alumno, Profesor
-- ====================================

-- Tabla padre: Persona (usando estrategia JOINED)
CREATE TABLE personas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    tipo_persona VARCHAR(31) NOT NULL, -- Discriminador para herencia
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM'
);

-- Tabla hija: Alumnos
CREATE TABLE alumnos (
    id BIGINT PRIMARY KEY,
    codigo_estudiante VARCHAR(20) UNIQUE NOT NULL,
    semestre_ingreso INT NOT NULL,
    FOREIGN KEY (id) REFERENCES personas(id) ON DELETE CASCADE
);

-- Tabla hija: Profesores
CREATE TABLE profesores (
    id BIGINT PRIMARY KEY,
    codigo_empleado VARCHAR(20) UNIQUE NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES personas(id) ON DELETE CASCADE
);

-- ====================================
-- PREGUNTA 02: CONSULTAS CON SPRING DATA JPA
-- ====================================

-- Tabla: Cursos
CREATE TABLE cursos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    creditos INT NOT NULL CHECK (creditos > 0),
    codigo_curso VARCHAR(10) UNIQUE NOT NULL,
    descripcion TEXT,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM'
);

-- Tabla: Matrículas (relación entre Alumno y Curso)
CREATE TABLE matriculas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alumno_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    fecha_matricula DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVA',
    nota_final DECIMAL(4,2) DEFAULT NULL,
    -- Campos de auditoría
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_creacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    usuario_actualizacion VARCHAR(50) NOT NULL DEFAULT 'SYSTEM',
    -- Claves foráneas
    FOREIGN KEY (alumno_id) REFERENCES alumnos(id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES cursos(id) ON DELETE CASCADE,
    -- Constraint único para evitar matrículas duplicadas
    UNIQUE KEY unique_matricula (alumno_id, curso_id)
);

-- ====================================
-- DATOS DE PRUEBA
-- ====================================

-- Insertar personas (alumnos)
INSERT INTO personas (nombre, email, tipo_persona) VALUES
('Juan Pérez García', 'juan.perez@universidad.edu.pe', 'Alumno'),
('María González López', 'maria.gonzalez@universidad.edu.pe', 'Alumno'),
('Carlos Rodríguez Silva', 'carlos.rodriguez@universidad.edu.pe', 'Alumno'),
('Ana Martínez Torres', 'ana.martinez@universidad.edu.pe', 'Profesor'),
('Luis Fernández Ruiz', 'luis.fernandez@universidad.edu.pe', 'Profesor');

-- Insertar alumnos
INSERT INTO alumnos (id, codigo_estudiante, semestre_ingreso) VALUES
(1, 'EST001', 202401),
(2, 'EST002', 202401),
(3, 'EST003', 202402);

-- Insertar profesores
INSERT INTO profesores (id, codigo_empleado, especialidad) VALUES
(4, 'PROF001', 'Ingeniería de Software'),
(5, 'PROF002', 'Base de Datos');

-- Insertar cursos
INSERT INTO cursos (nombre, creditos, codigo_curso, descripcion) VALUES
('Desarrollo de Aplicaciones Web I', 4, 'DAWI001', 'Curso de desarrollo web con Spring Boot'),
('Base de Datos Avanzadas', 3, 'BDA001', 'Curso de bases de datos relacionales y NoSQL'),
('Arquitectura de Software', 4, 'AS001', 'Patrones de diseño y arquitecturas empresariales'),
('Programación Orientada a Objetos', 3, 'POO001', 'Fundamentos de POO con Java');

-- Insertar matrículas
INSERT INTO matriculas (alumno_id, curso_id, fecha_matricula, estado, nota_final) VALUES
(1, 1, '2024-03-01', 'ACTIVA', NULL),
(1, 2, '2024-03-01', 'ACTIVA', NULL),
(2, 1, '2024-03-01', 'ACTIVA', 16.5),
(2, 3, '2024-03-01', 'COMPLETADA', 18.0),
(3, 1, '2024-08-01', 'ACTIVA', NULL),
(3, 4, '2024-08-01', 'ACTIVA', NULL);

-- ====================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- ====================================

-- Índices para búsquedas frecuentes
CREATE INDEX idx_personas_email ON personas(email);
CREATE INDEX idx_personas_tipo ON personas(tipo_persona);
CREATE INDEX idx_alumnos_codigo ON alumnos(codigo_estudiante);
CREATE INDEX idx_profesores_codigo ON profesores(codigo_empleado);
CREATE INDEX idx_cursos_nombre ON cursos(nombre);
CREATE INDEX idx_cursos_codigo ON cursos(codigo_curso);
CREATE INDEX idx_matriculas_alumno ON matriculas(alumno_id);
CREATE INDEX idx_matriculas_curso ON matriculas(curso_id);
CREATE INDEX idx_matriculas_fecha ON matriculas(fecha_matricula);

-- ====================================
-- VISTAS ÚTILES PARA CONSULTAS
-- ====================================

-- Vista para obtener información completa de alumnos
CREATE VIEW vista_alumnos AS
SELECT 
    a.id,
    p.nombre,
    p.email,
    a.codigo_estudiante,
    a.semestre_ingreso,
    p.fecha_creacion
FROM personas p
INNER JOIN alumnos a ON p.id = a.id
WHERE p.tipo_persona = 'Alumno';

-- Vista para obtener información completa de profesores
CREATE VIEW vista_profesores AS
SELECT 
    pr.id,
    p.nombre,
    p.email,
    pr.codigo_empleado,
    pr.especialidad,
    p.fecha_creacion
FROM personas p
INNER JOIN profesores pr ON p.id = pr.id
WHERE p.tipo_persona = 'Profesor';

-- Vista para matrículas con información completa
CREATE VIEW vista_matriculas_completa AS
SELECT 
    m.id as matricula_id,
    va.nombre as alumno_nombre,
    va.email as alumno_email,
    va.codigo_estudiante,
    c.nombre as curso_nombre,
    c.codigo_curso,
    c.creditos,
    m.fecha_matricula,
    m.estado,
    m.nota_final
FROM matriculas m
INNER JOIN vista_alumnos va ON m.alumno_id = va.id
INNER JOIN cursos c ON m.curso_id = c.id;

-- ====================================
-- CONSULTAS DE VERIFICACIÓN
-- ====================================

-- Verificar la herencia
SELECT 'Verificación de herencia JPA:' as mensaje;
SELECT p.tipo_persona, COUNT(*) as cantidad 
FROM personas p 
GROUP BY p.tipo_persona;

-- Verificar las matrículas
SELECT 'Verificación de matrículas:' as mensaje;
SELECT * FROM vista_matriculas_completa;

COMMIT;
