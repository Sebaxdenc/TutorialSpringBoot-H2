package spring_h2.nutrition.repository;

import spring_h2.nutrition.model.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    
    List<Medicion> findByPacienteIdOrderByFechaDesc(Long pacienteId);
    
    Optional<Medicion> findFirstByPacienteIdOrderByFechaDesc(Long pacienteId);
    
}