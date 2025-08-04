package pe.cibertec.dawi_t1_hacho_haho_jhabel.presentation.controller;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.entity.MatriculaEntity;
import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.jpa.MatriculaRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de matrículas
 * Implementa consultas JPQL por email del alumno
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@RestController
@RequestMapping("/api/matriculas")
@CrossOrigin(origins = "*")
public class MatriculaController {
    
    @Autowired
    private MatriculaRepositoryJpa matriculaRepository;
    
    /**
     * PREGUNTA 02: Lista matrículas por email del alumno usando JPQL
     * Ejemplo: GET /api/matriculas/email?email=juan.perez@universidad.edu.pe
     */
    @GetMapping("/email")
    public ResponseEntity<?> listarPorEmailAlumno(
            @RequestParam(required = false) String email) {
        
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El parámetro 'email' es requerido");
        }
        
        try {
            List<MatriculaEntity> matriculas = matriculaRepository.findByAlumnoEmail(email);
            return ResponseEntity.ok(matriculas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al buscar matrículas: " + e.getMessage());
        }
    }
    
    /**
     * PREGUNTA 02: Consulta completa con JOIN FETCH para evitar N+1
     * Ejemplo: GET /api/matriculas/email/completa?email=juan.perez@universidad.edu.pe
     */
    @GetMapping("/email/completa")
    public ResponseEntity<List<MatriculaEntity>> consultaCompletaPorEmail(@RequestParam String email) {
        List<MatriculaEntity> matriculas = matriculaRepository.findByAlumnoEmailWithJoinFetch(email);
        return ResponseEntity.ok(matriculas);
    }
    
    /**
     * PREGUNTA 02: Contar matrículas activas por email usando JPQL
     * Ejemplo: GET /api/matriculas/email/contar?email=juan.perez@universidad.edu.pe
     */
    @GetMapping("/email/contar")
    public ResponseEntity<Long> contarMatriculasActivasPorEmail(@RequestParam String email) {
        Long count = matriculaRepository.countActiveMatriculasByEmail(email);
        return ResponseEntity.ok(count);
    }
    
    /**
     * PREGUNTA 02: Búsqueda paginada por email del alumno
     * Ejemplo: GET /api/matriculas/email/paginado?email=juan.perez@universidad.edu.pe&page=0&size=5
     */
    @GetMapping("/email/paginado")
    public ResponseEntity<Page<MatriculaEntity>> buscarPorEmailPaginado(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaMatricula") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MatriculaEntity> matriculas = matriculaRepository.findByAlumnoEmailPaginated(email, pageable);
        
        return ResponseEntity.ok(matriculas);
    }
    
    /**
     * Listar todas las matrículas con paginación
     */
    @GetMapping
    public ResponseEntity<Page<MatriculaEntity>> listarTodas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaMatricula") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<MatriculaEntity> matriculas = matriculaRepository.findAll(pageable);
        
        return ResponseEntity.ok(matriculas);
    }
    
    /**
     * Obtener matrícula por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MatriculaEntity> obtenerPorId(@PathVariable Long id) {
        Optional<MatriculaEntity> matricula = matriculaRepository.findById(id);
        return matricula.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Buscar matrículas por código del estudiante
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<List<MatriculaEntity>> buscarPorCodigoEstudiante(@PathVariable String codigo) {
        List<MatriculaEntity> matriculas = matriculaRepository.findByAlumnoCodigoEstudiante(codigo);
        return ResponseEntity.ok(matriculas);
    }
    
    /**
     * Buscar matrículas por estado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MatriculaEntity>> buscarPorEstado(@PathVariable String estado) {
        List<MatriculaEntity> matriculas = matriculaRepository.findByEstado(estado);
        return ResponseEntity.ok(matriculas);
    }
}
