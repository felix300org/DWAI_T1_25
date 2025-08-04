package pe.cibertec.dawi_t1_hacho_haho_jhabel.infrastructure.persistence.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Clase base para auditoría automática de entidades
 * Proporciona campos de auditoría estándar para todas las entidades
 * Siguiendo el patrón del proyecto ciber-rrhh-service-main
 * 
 * @param <U> Tipo del usuario que realiza la operación
 * @author HACHO HAHO JHABEL
 */
@lombok.Getter
@lombok.Setter
@lombok.RequiredArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditoria<U> {

    @CreatedBy
    @JsonIgnore
    @Column(name = "usuario_creacion", updatable = false)
    private U usuarioCreacion;

    @CreatedDate
    @JsonIgnore
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedBy
    @JsonIgnore
    @Column(name = "usuario_actualizacion")
    private U usuarioActualizacion;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    

}
