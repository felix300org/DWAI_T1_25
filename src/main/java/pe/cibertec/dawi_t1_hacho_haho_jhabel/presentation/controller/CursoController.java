package pe.cibertec.dawi_t1_hacho_haho_jhabel.presentation.controller;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.entity.CursoEntity;
import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.jpa.CursoRepositoryJpa;
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
 * Controlador REST para la gestión de cursos
 * Implementa paginación y ordenamiento
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@RestController
@RequestMapping("/api/cursos")
@CrossOrigin(origins = "*")
public class CursoController {
    
    @Autowired
    private CursoRepositoryJpa cursoRepository;
    
    /**
     * PREGUNTA 02: Endpoint con paginación y ordenamiento
     * Ejemplo: GET /api/cursos?nombre=desarrollo&page=0&size=10&sortBy=nombre&sortDir=asc
     */
    @GetMapping
    public ResponseEntity<Page<CursoEntity>> listarCursos(
            @RequestParam(defaultValue = "") String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        // Crear Sort
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        // Crear Pageable
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Buscar con paginación
        Page<CursoEntity> cursos;
        if (nombre.isEmpty()) {
            cursos = cursoRepository.findAll(pageable);
        } else {
            cursos = cursoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * PREGUNTA 02: Búsqueda por nombre usando JPQL
     * Ejemplo: GET /api/cursos/buscar?nombre=desarrollo
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<CursoEntity>> buscarPorNombre(@RequestParam String nombre) {
        List<CursoEntity> cursos = cursoRepository.findByNombreContainingJpql(nombre);
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * Obtener curso por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> obtenerPorId(@PathVariable Long id) {
        Optional<CursoEntity> curso = cursoRepository.findById(id);
        return curso.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Obtener curso por código
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CursoEntity> obtenerPorCodigo(@PathVariable String codigo) {
        Optional<CursoEntity> curso = cursoRepository.findByCodigo(codigo);
        return curso.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Listar todos los cursos sin paginación
     */
    @GetMapping("/todos")
    public ResponseEntity<List<CursoEntity>> listarTodos() {
        List<CursoEntity> cursos = cursoRepository.findAll();
        return ResponseEntity.ok(cursos);
    }
    
    /**
     * Buscar por créditos
     */
    @GetMapping("/creditos/{creditos}")
    public ResponseEntity<List<CursoEntity>> buscarPorCreditos(@PathVariable Integer creditos) {
        List<CursoEntity> cursos = cursoRepository.findByCreditos(creditos);
        return ResponseEntity.ok(cursos);
    }
}
