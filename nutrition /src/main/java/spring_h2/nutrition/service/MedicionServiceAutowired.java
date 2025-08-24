package spring_h2.nutrition.service;

import spring_h2.nutrition.model.Medicion;
import spring_h2.nutrition.repository.MedicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MedicionServiceAutowired {
    
    // Inyección de dependencias a nivel de campo usando @Autowired
    @Autowired
    private MedicionRepository medicionRepository;
    
    @Transactional(readOnly = true)
    public List<Medicion> findAll() {
        return medicionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Medicion> findById(Long id) {
        return medicionRepository.findById(id);
    }

    // Otros métodos del servicio (omitidos por brevedad)
    
    @Transactional(readOnly = true)
    public List<Medicion> findByPacienteId(Long pacienteId) {
        return medicionRepository.findByPacienteIdOrderByFechaDesc(pacienteId);
    }
    
    @Transactional(readOnly = true)
    public Optional<Medicion> findLatestByPacienteId(Long pacienteId) {
        return medicionRepository.findFirstByPacienteIdOrderByFechaDesc(pacienteId);
    }
    
    @Transactional
    public Medicion save(Medicion medicion) {
        return medicionRepository.save(medicion);
    }
    
    @Transactional
    public Medicion update(Medicion medicion) {
        return medicionRepository.save(medicion);
    }
    
    @Transactional
    public void deleteById(Long id) {
        medicionRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return medicionRepository.existsById(id);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return medicionRepository.count();
    }
    
}   