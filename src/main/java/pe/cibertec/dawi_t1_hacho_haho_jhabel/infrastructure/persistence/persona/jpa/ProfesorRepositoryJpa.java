package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.entity.ProfesorEntity;

/**
 * Repositorio JPA para la entidad ProfesorEntity
 * Permite realizar operaciones CRUD y consultas personalizadas
 * 
 * @author HACHO HAHO JHABEL
 */
public interface ProfesorRepositoryJpa extends JpaRepository<ProfesorEntity, Long> {
    
    /**
     * Busca un profesor por su código único
     * @param codigoProfesor Código único del profesor
     * @return ProfesorEntity si se encuentra, null en caso contrario
     */
    ProfesorEntity findByCodigoProfesor(String codigoProfesor);
    
    /**
     * Verifica si existe un profesor con el código especificado
     * @param codigoProfesor Código único del profesor
     * @return true si existe, false en caso contrario
     */
    boolean existsByCodigoProfesor(String codigoProfesor);
}
