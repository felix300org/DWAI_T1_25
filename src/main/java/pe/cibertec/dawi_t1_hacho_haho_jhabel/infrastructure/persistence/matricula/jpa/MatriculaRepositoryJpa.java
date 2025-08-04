package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.jpa;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.entity.MatriculaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Matrícula
 * Implementa consultas JPQL para búsqueda por email del alumno
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Repository
public interface MatriculaRepositoryJpa extends JpaRepository<MatriculaEntity, Long> {
    
    /**
     * PREGUNTA 02: Lista matrículas por email del alumno usando JPQL
     * @param emailAlumno Email del alumno
     * @return Lista de matrículas del alumno
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findMatriculasByAlumnoEmail(@Param("emailAlumno") String emailAlumno);
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("""
        SELECT DISTINCT m FROM MatriculaEntity m 
        LEFT JOIN FETCH m.alumno a
        LEFT JOIN FETCH m.curso c
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findByAlumnoEmail(@Param("emailAlumno") String emailAlumno);
    
    /**
     * PREGUNTA 02: Consulta con información completa usando JPQL con JOIN FETCH
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        LEFT JOIN FETCH m.alumno a 
        LEFT JOIN FETCH m.curso c
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findMatriculasCompletasByAlumnoEmail(@Param("emailAlumno") String emailAlumno);
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        LEFT JOIN FETCH m.alumno a 
        LEFT JOIN FETCH m.curso c
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findByAlumnoEmailWithJoinFetch(@Param("emailAlumno") String emailAlumno);
    
    /**
     * PREGUNTA 02: Contar matrículas activas por alumno
     */
    @Query("""
        SELECT COUNT(m) FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE LOWER(a.email) = LOWER(:emailAlumno) 
        AND m.estado = 'ACTIVA'
        """)
    Long countMatriculasActivasByAlumnoEmail(@Param("emailAlumno") String emailAlumno);
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("""
        SELECT COUNT(m) FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE LOWER(a.email) = LOWER(:emailAlumno) 
        AND m.estado = 'ACTIVA'
        """)
    Long countActiveMatriculasByEmail(@Param("emailAlumno") String emailAlumno);
    
    /**
     * PREGUNTA 02: Búsqueda paginada de matrículas por email
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        """)
    Page<MatriculaEntity> findMatriculasByAlumnoEmailPaginado(
        @Param("emailAlumno") String emailAlumno, 
        Pageable pageable
    );
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE LOWER(a.email) = LOWER(:emailAlumno)
        """)
    Page<MatriculaEntity> findByAlumnoEmailPaginated(
        @Param("emailAlumno") String emailAlumno, 
        Pageable pageable
    );
    
    /**
     * Buscar matrículas por código de estudiante
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE a.codigoEstudiante = :codigoEstudiante
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findMatriculasByCodigoEstudiante(@Param("codigoEstudiante") String codigoEstudiante);
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.alumno a 
        WHERE a.codigoEstudiante = :codigoEstudiante
        ORDER BY m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findByAlumnoCodigoEstudiante(@Param("codigoEstudiante") String codigoEstudiante);
    
    /**
     * Buscar matrículas por nombre del curso
     */
    @Query("""
        SELECT m FROM MatriculaEntity m 
        INNER JOIN m.curso c 
        WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombreCurso, '%'))
        ORDER BY c.nombre ASC, m.fechaMatricula DESC
        """)
    List<MatriculaEntity> findMatriculasByNombreCurso(@Param("nombreCurso") String nombreCurso);
    
    /**
     * Buscar matrículas por estado
     */
    @Query("SELECT m FROM MatriculaEntity m WHERE m.estado = :estado ORDER BY m.fechaMatricula DESC")
    List<MatriculaEntity> findMatriculasByEstado(@Param("estado") String estado);
    
    /**
     * Alias para compatibilidad con el controlador
     */
    @Query("SELECT m FROM MatriculaEntity m WHERE m.estado = :estado ORDER BY m.fechaMatricula DESC")
    List<MatriculaEntity> findByEstado(@Param("estado") String estado);
}
