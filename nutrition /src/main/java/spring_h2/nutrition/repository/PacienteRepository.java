package spring_h2.nutrition.repository;

import spring_h2.nutrition.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Métodos de consulta por campos específicos
    Optional<Paciente> findByEmail(String email);
    
    List<Paciente> findByNombreContainingIgnoreCase(String nombre);
    
    List<Paciente> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Paciente> findByActivo(boolean activo);
    
    List<Paciente> findByActivoTrue();
    
    List<Paciente> findByActivoFalse();
    
    // Búsqueda por fecha de nacimiento
    List<Paciente> findByFechaNacimiento(LocalDate fechaNacimiento);
    
    List<Paciente> findByFechaNacimientoBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    List<Paciente> findByFechaNacimientoAfter(LocalDate fecha);
    
    List<Paciente> findByFechaNacimientoBefore(LocalDate fecha);
    
    // Búsqueda combinada por nombre y apellido
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
           "LOWER(p.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Paciente> findByNombreAndApellido(@Param("nombre") String nombre, 
                                          @Param("apellido") String apellido);
    
    // Buscar por nombre completo (nombre + apellido)
    @Query("SELECT p FROM Paciente p WHERE " +
           "LOWER(CONCAT(p.nombre, ' ', p.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Paciente> findByNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
    
    // Buscar pacientes por nutricionista
    List<Paciente> findByNutricionistaId(Long nutricionistaId);
    
    List<Paciente> findByNutricionistaIdAndActivoTrue(Long nutricionistaId);
    
    // Verificar si existe por email (útil para validaciones)
    boolean existsByEmail(String email);
    
    // Contar pacientes activos
    @Query("SELECT COUNT(p) FROM Paciente p WHERE p.activo = true")
    long countActivosPacientes();
    
    // Buscar pacientes por rango de edad
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento IS NOT NULL AND " +
           "EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.fechaNacimiento) BETWEEN :edadMin AND :edadMax")
    List<Paciente> findByRangoEdad(@Param("edadMin") int edadMin, @Param("edadMax") int edadMax);
    
    // Buscar pacientes menores de cierta edad
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento IS NOT NULL AND " +
           "EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.fechaNacimiento) < :edad")
    List<Paciente> findMenoresDeEdad(@Param("edad") int edad);
    
    // Buscar pacientes mayores de cierta edad
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento IS NOT NULL AND " +
           "EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.fechaNacimiento) > :edad")
    List<Paciente> findMayoresDeEdad(@Param("edad") int edad);
    
    // Buscar pacientes que tienen mediciones
    @Query("SELECT DISTINCT p FROM Paciente p WHERE SIZE(p.mediciones) > 0")
    List<Paciente> findPacientesConMediciones();
    
    // Buscar pacientes sin mediciones
    @Query("SELECT p FROM Paciente p WHERE SIZE(p.mediciones) = 0")
    List<Paciente> findPacientesSinMediciones();
    
    // Buscar pacientes que tienen notas
    @Query("SELECT DISTINCT p FROM Paciente p WHERE SIZE(p.notas) > 0")
    List<Paciente> findPacientesConNotas();
    
    // Contar mediciones por paciente
    @Query("SELECT p, SIZE(p.mediciones) FROM Paciente p WHERE p.id = :pacienteId")
    Object[] countMedicionesByPacienteId(@Param("pacienteId") Long pacienteId);
    
    // Buscar pacientes registrados en un período
    @Query("SELECT p FROM Paciente p WHERE p.fechaNacimiento >= :fechaInicio AND p.fechaNacimiento <= :fechaFin AND p.activo = true")
    List<Paciente> findPacientesActivosEnPeriodo(@Param("fechaInicio") LocalDate fechaInicio, 
                                                @Param("fechaFin") LocalDate fechaFin);
    
    // Buscar pacientes por nutricionista y que estén activos
    @Query("SELECT p FROM Paciente p WHERE p.nutricionista.id = :nutricionistaId AND p.activo = true ORDER BY p.nombre, p.apellido")
    List<Paciente> findActivosByNutricionistaIdOrderByNombre(@Param("nutricionistaId") Long nutricionistaId);
    
    // Estadísticas: Edad promedio de pacientes
    @Query("SELECT AVG(EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM p.fechaNacimiento)) FROM Paciente p WHERE p.activo = true AND p.fechaNacimiento IS NOT NULL")
    Double findEdadPromedioActivos();
    
    // Buscar cumpleañeros del mes
    @Query("SELECT p FROM Paciente p WHERE EXTRACT(MONTH FROM p.fechaNacimiento) = EXTRACT(MONTH FROM CURRENT_DATE) AND p.activo = true")
    List<Paciente> findCumpleanerosMesActual();
    
}
