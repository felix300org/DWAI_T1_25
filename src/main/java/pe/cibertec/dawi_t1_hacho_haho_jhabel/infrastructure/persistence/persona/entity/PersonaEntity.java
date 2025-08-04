package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.entity;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.shared.Auditoria;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad padre para el sistema de herencia JPA
 * Representa a todas las personas del sistema académico
 * Usa estrategia JOINED para herencia
 * RESPUESTA A PREGUNTA 01 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_persona", discriminatorType = DiscriminatorType.STRING)
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public abstract class PersonaEntity extends Auditoria<String> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    

}
