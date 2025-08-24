package spring_h2.nutrition.controller;

import spring_h2.nutrition.model.Medicion;
import spring_h2.nutrition.service.MedicionServiceConstructor;
import spring_h2.nutrition.service.MedicionServiceAutowired;
import spring_h2.nutrition.service.MedicionServiceSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/mediciones")
public class MedicionController {
    
    // Inyección por constructor
    private final MedicionServiceConstructor constructorService;
    
    // Inyección por campo
    @Autowired
    private MedicionServiceAutowired autowiredService;
    
    // Inyección por setter
    private MedicionServiceSetter setterService;
    
    public MedicionController(MedicionServiceConstructor constructorService) {
        this.constructorService = constructorService;
    }
    
    @Autowired
    public void setSetterService(MedicionServiceSetter setterService) {
        this.setterService = setterService;
    }
    
    @GetMapping("/constructor")
    public ResponseEntity<List<Medicion>> getAllMedicionesConstructor() {
        return ResponseEntity.ok(constructorService.findAll());
    }
    
    @GetMapping("/autowired")
    public ResponseEntity<List<Medicion>> getAllMedicionesAutowired() {
        return ResponseEntity.ok(autowiredService.findAll());
    }
    
    @GetMapping("/setter")
    public ResponseEntity<List<Medicion>> getAllMedicionesSetter() {
        return ResponseEntity.ok(setterService.findAll());
    }
    
    @GetMapping("/compare/{id}")
    public ResponseEntity<Map<String, Object>> compareMedicionById(@PathVariable Long id) {
        Optional<Medicion> constructorResult = constructorService.findById(id);
        Optional<Medicion> autowiredResult = autowiredService.findById(id);
        Optional<Medicion> setterResult = setterService.findById(id);
        
        Map<String, Object> response = new HashMap<>();
        response.put("constructorService", constructorResult.orElse(null));
        response.put("autowiredService", autowiredResult.orElse(null));
        response.put("setterService", setterResult.orElse(null));
        
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/constructor/paciente/{pacienteId}/nutricionista/{nutricionistaId}")
    public ResponseEntity<Medicion> createMedicionConstructor(
            @PathVariable Long pacienteId,
            @PathVariable Long nutricionistaId,
            @RequestBody Medicion medicion) {
        Medicion createdMedicion = constructorService.createMedicion(pacienteId, nutricionistaId, medicion);
        return new ResponseEntity<>(createdMedicion, HttpStatus.CREATED);
    }
    
    // Otros endpoints (omitidos por brevedad)

    @GetMapping("/{id}")
    public ResponseEntity<Medicion> getMedicionById(@PathVariable Long id) {
        Optional<Medicion> medicion = constructorService.findById(id);
        if (medicion.isPresent()) {
            return ResponseEntity.ok(medicion.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }   

    
}