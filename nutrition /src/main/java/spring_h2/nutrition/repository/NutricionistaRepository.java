package spring_h2.nutrition.repository;

import spring_h2.nutrition.model.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {
    
    // Métodos de consulta por campos específicos
    Optional<Nutricionista> findByEmail(String email);
    
    Optional<Nutricionista> findByNumeroLicencia(String numeroLicencia);
    
    List<Nutricionista> findByNombreContainingIgnoreCase(String nombre);
    
    List<Nutricionista> findByApellidoContainingIgnoreCase(String apellido);
    
    List<Nutricionista> findByEspecialidad(String especialidad);
    
    List<Nutricionista> findByActivo(boolean activo);
    
    List<Nutricionista> findByActivoTrue();
    
    List<Nutricionista> findByActivoFalse();
    
    // Búsqueda combinada por nombre y apellido
    @Query("SELECT n FROM Nutricionista n WHERE " +
           "LOWER(n.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
           "LOWER(n.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))")
    List<Nutricionista> findByNombreAndApellido(@Param("nombre") String nombre, 
                                               @Param("apellido") String apellido);
    
    // Buscar nutricionistas por especialidad y que estén activos
    List<Nutricionista> findByEspecialidadAndActivoTrue(String especialidad);
    
    // Buscar por nombre completo (nombre + apellido)
    @Query("SELECT n FROM Nutricionista n WHERE " +
           "LOWER(CONCAT(n.nombre, ' ', n.apellido)) LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Nutricionista> findByNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
    
    // Verificar si existe por email (útil para validaciones)
    boolean existsByEmail(String email);
    
    // Verificar si existe por número de licencia
    boolean existsByNumeroLicencia(String numeroLicencia);
    
    // Contar nutricionistas activos
    @Query("SELECT COUNT(n) FROM Nutricionista n WHERE n.activo = true")
    long countActivosNutricionistas();
    
    // Obtener todas las especialidades disponibles
    @Query("SELECT DISTINCT n.especialidad FROM Nutricionista n WHERE n.especialidad IS NOT NULL ORDER BY n.especialidad")
    List<String> findAllEspecialidades();
    
    // Buscar nutricionistas que tienen pacientes asignados
    @Query("SELECT DISTINCT n FROM Nutricionista n WHERE SIZE(n.pacientes) > 0")
    List<Nutricionista> findNutricionistasConPacientes();
    
    // Buscar nutricionistas sin pacientes
    @Query("SELECT n FROM Nutricionista n WHERE SIZE(n.pacientes) = 0")
    List<Nutricionista> findNutricionistasSinPacientes();
    
    // Contar pacientes por nutricionista
    @Query("SELECT n, SIZE(n.pacientes) FROM Nutricionista n WHERE n.id = :nutricionistaId")
    Object[] countPacientesByNutricionistaId(@Param("nutricionistaId") Long nutricionistaId);
    
}
