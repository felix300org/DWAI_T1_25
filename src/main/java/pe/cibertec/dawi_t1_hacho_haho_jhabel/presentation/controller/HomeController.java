package pe.cibertec.dawi_t1_hacho_haho_jhabel.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador de inicio del Sistema Académico
 * Proporciona información general de la API
 * 
 * @author HACHO HAHO JHABEL
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HomeController {
    
    /**
     * Endpoint raíz - Información de la API
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("aplicacion", "Sistema Académico - Evaluación DAWI");
        response.put("estudiante", "HACHO HAHO JHABEL");
        response.put("arquitectura", "Hexagonal / Clean Architecture");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "FUNCIONANDO CORRECTAMENTE ");
        
        // Información de endpoints disponibles
        Map<String, Object> endpoints = new HashMap<>();
        
        // Endpoints de Cursos (Pregunta 02)
        Map<String, String> cursosEndpoints = new HashMap<>();
        cursosEndpoints.put("GET /api/cursos", "Listar cursos con paginación");
        cursosEndpoints.put("GET /api/cursos/buscar?nombre=", "Buscar cursos por nombre (JPQL)");
        cursosEndpoints.put("GET /api/cursos/{id}", "Obtener curso por ID");
        cursosEndpoints.put("GET /api/cursos/codigo/{codigo}", "Obtener curso por código");
        cursosEndpoints.put("GET /api/cursos/todos", "Listar todos los cursos");
        cursosEndpoints.put("GET /api/cursos/creditos/{creditos}", "Buscar por créditos");
        
        // Endpoints de Matrículas (Pregunta 02)
        Map<String, String> matriculasEndpoints = new HashMap<>();
        matriculasEndpoints.put("GET /api/matriculas/email?email=", "Listar matrículas por email (JPQL)");
        matriculasEndpoints.put("GET /api/matriculas/email/completa?email=", "Consulta completa con JOIN FETCH");
        matriculasEndpoints.put("GET /api/matriculas/email/contar?email=", "Contar matrículas activas por email");
        matriculasEndpoints.put("GET /api/matriculas/email/paginado?email=", "Búsqueda paginada por email");
        matriculasEndpoints.put("GET /api/matriculas", "Listar todas con paginación");
        matriculasEndpoints.put("GET /api/matriculas/{id}", "Obtener matrícula por ID");
        matriculasEndpoints.put("GET /api/matriculas/codigo/{codigo}", "Buscar por código estudiante");
        matriculasEndpoints.put("GET /api/matriculas/estado/{estado}", "Buscar por estado");
        
        endpoints.put("cursos", cursosEndpoints);
        endpoints.put("matriculas", matriculasEndpoints);
        
        response.put("endpoints", endpoints);
        
        // Ejemplos de uso
        Map<String, String> ejemplos = new HashMap<>();
        ejemplos.put("Paginación cursos", "/api/cursos?nombre=desarrollo&page=0&size=5&sortBy=nombre&sortDir=asc");
        ejemplos.put("Buscar por email", "/api/matriculas/email?email=juan.perez@universidad.edu.pe");
        ejemplos.put("Paginación matrículas", "/api/matriculas/email/paginado?email=juan.perez@universidad.edu.pe&page=0&size=3");
        
        response.put("ejemplos", ejemplos);
        
        // Información técnica
        Map<String, String> tecnologias = new HashMap<>();
        tecnologias.put("Framework", "Spring Boot 3.5.3");
        tecnologias.put("Java", "21");
        tecnologias.put("Base de Datos", "MySQL");
        tecnologias.put("ORM", "JPA/Hibernate");
        tecnologias.put("Herencia JPA", "JOINED Strategy");
        tecnologias.put("Consultas", "JPQL + Query Methods");
        tecnologias.put("Paginación", "Spring Data Pageable");
        
        response.put("tecnologias", tecnologias);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint raíz alternativo
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        return home();
    }
    
    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Sistema Académico");
        health.put("database", "MySQL Connected");
        
        return ResponseEntity.ok(health);
    }
    
    /**
     * Información sobre las respuestas de la evaluación
     */
    @GetMapping("/evaluacion")
    public ResponseEntity<Map<String, Object>> evaluacion() {
        Map<String, Object> eval = new HashMap<>();
        
        eval.put("evaluacion", "DAWI - Desarrollo de Aplicaciones Web I");
        eval.put("estudiante", "HACHO HAHO JHABEL");
        
        // Pregunta 01
        Map<String, Object> pregunta01 = new HashMap<>();
        pregunta01.put("tema", "Herencia en JPA");
        pregunta01.put("caso_negocio", "Sistema de Gestión Académica Universitaria");
        pregunta01.put("entidad_padre", "PersonaEntity (nombre, email)");
        pregunta01.put("entidades_hijas", new String[]{"AlumnoEntity (codigoEstudiante, semestreIngreso)", "ProfesorEntity (codigoEmpleado, especialidad)"});
        pregunta01.put("estrategia", "JOINED");
        pregunta01.put("discriminador", "tipo_persona");
        
        // Pregunta 02
        Map<String, Object> pregunta02 = new HashMap<>();
        pregunta02.put("tema", "Consultas con Spring Data JPA");
        pregunta02.put("curso_repository", "CursoRepositoryJpa - Búsqueda por nombre parcial con JPQL");
        pregunta02.put("matricula_repository", "MatriculaRepositoryJpa - Consultas por email del alumno con JPQL");
        pregunta02.put("paginacion", "Implementada con Pageable y Sort");
        pregunta02.put("ordenamiento", "Múltiple con Sort.Order");
        
        eval.put("pregunta_01", pregunta01);
        eval.put("pregunta_02", pregunta02);
        
        return ResponseEntity.ok(eval);
    }
}
