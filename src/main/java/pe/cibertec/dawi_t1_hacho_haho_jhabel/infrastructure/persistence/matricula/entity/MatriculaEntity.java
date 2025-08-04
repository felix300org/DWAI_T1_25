package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.matricula.entity;

import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.shared.Auditoria;
import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.persona.entity.AlumnoEntity;
import pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.curso.entity.CursoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa las matrículas del sistema académico
 * Relaciona alumnos con cursos
 * RESPUESTA A PREGUNTA 02 DE LA EVALUACIÓN
 * 
 * @author HACHO HAHO JHABEL
 */
@Entity
@Table(name = "matriculas", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"alumno_id", "curso_id"}))
@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class MatriculaEntity extends Auditoria<String> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private AlumnoEntity alumno;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoEntity curso;
    
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;
    
    @Column(length = 20)
    @Builder.Default
    private String estado = "ACTIVA";
    
    @Column(name = "nota_final", precision = 4, scale = 2)
    private BigDecimal notaFinal;
    

}
