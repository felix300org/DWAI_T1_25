package pe.cibertec.dawi_t1_hacho_haho_jhabel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Aplicación principal del Sistema Académico
 * Implementa Arquitectura Hexagonal siguiendo el patrón del proyecto ciber-rrhh-service-main
 * 
 * @author HACHO HAHO JHABEL
 * @version 1.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
public class DawiT1HachoHahoJhabelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawiT1HachoHahoJhabelApplication.class, args);
        System.out.println("=".repeat(60));
        System.out.println(" SISTEMA ACADÉMICO INICIADO CORRECTAMENTE");
        System.out.println(" EVALUACIÓN DAWI - HACHO HAHO JHABEL");
        System.out.println(" Arquitectura: Hexagonal / Clean Architecture");
        System.out.println("=".repeat(60));
        System.out.println(" ENDPOINTS DISPONIBLES:");
        System.out.println(" - GET http://localhost:8080/");
        System.out.println(" - GET http://localhost:8080/test");
        System.out.println(" - GET http://localhost:8080/api/cursos/buscar?nombre=desarrollo");
        System.out.println(" - GET http://localhost:8080/api/matriculas/email?email=juan.perez@universidad.edu.pe");
        System.out.println("=".repeat(60));
    }
}
