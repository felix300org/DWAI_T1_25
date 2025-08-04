package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.jpa;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.entity.CursoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Curso
 * Implementa consultas JPQL para búsqueda por nombre parcial
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Repository
public interface CursoRepositoryJpa extends JpaRepository<CursoEntity, Long> {
    
    /**
     * PREGUNTA 02: Busca cursos por nombre parcial usando JPQL
     * @param nombreParcial Parte del nombre del curso
     * @return Lista de cursos que contienen el texto en su nombre
     */
    @Query("SELECT c FROM CursoEntity c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombreParcial, '%'))")
    List<CursoEntity> findByNombreContainingJpql(@Param("nombreParcial") String nombreParcial);
    
    /**
     * Alternativa usando Query Methods (sin JPQL)
     */
    List<CursoEntity> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * PREGUNTA 02: Búsqueda paginada con ordenamiento usando JPQL
     */
    @Query("SELECT c FROM CursoEntity c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    Page<CursoEntity> findByNombreContainingJpql(
        @Param("nombre") String nombre, 
        Pageable pageable
    );
    
    /**
     * Usando Query Methods con paginación
     */
    Page<CursoEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    
    /**
     * Búsqueda por código de curso
     */
    @Query("SELECT c FROM CursoEntity c WHERE c.codigoCurso = :codigo")
    Optional<CursoEntity> findByCodigoCurso(@Param("codigo") String codigo);
    
    /**
     * Búsqueda por código de curso (alias para compatibilidad)
     */
    @Query("SELECT c FROM CursoEntity c WHERE c.codigoCurso = :codigo")
    Optional<CursoEntity> findByCodigo(@Param("codigo") String codigo);
    
    /**
     * Buscar cursos por número de créditos
     */
    @Query("SELECT c FROM CursoEntity c WHERE c.creditos = :creditos ORDER BY c.nombre ASC")
    List<CursoEntity> findByCreditos(@Param("creditos") Integer creditos);
    
    /**
     * Buscar cursos con más de X créditos
     */
    @Query("SELECT c FROM CursoEntity c WHERE c.creditos >= :minCreditos ORDER BY c.creditos DESC, c.nombre ASC")
    List<CursoEntity> findByMinCreditos(@Param("minCreditos") Integer minCreditos);
}
