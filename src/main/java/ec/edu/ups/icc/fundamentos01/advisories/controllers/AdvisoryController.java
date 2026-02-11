package ec.edu.ups.icc.fundamentos01.advisories.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ec.edu.ups.icc.fundamentos01.advisories.dtos.*;
import ec.edu.ups.icc.fundamentos01.advisories.services.AdvisoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/advisories")
public class AdvisoryController {

    private final AdvisoryService service;

    public AdvisoryController(AdvisoryService service) {
        this.service = service;
    }

    // 1. Obtener todas (Admin)
    @GetMapping
    public List<AdvisoryResponseDto> getAll() {
        return service.findAll();
    }

    // 2. Obtener asesorías del cliente logueado
    @GetMapping("/my-advisories")
    public List<AdvisoryResponseDto> getMyAdvisories() {
        return service.findMyAdvisories();
    }

    // 3. Obtener asesorías asignadas al programador logueado
    @GetMapping("/assigned")
    public List<AdvisoryResponseDto> getAssigned() {
        return service.findAssignedAdvisories();
    }

    // 4. Obtener una por ID (Mantenla después de las rutas fijas)
    @GetMapping("/{id}")
    public AdvisoryResponseDto getOne(@PathVariable("id") Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public AdvisoryResponseDto create(@Valid @RequestBody CreateAdvisoryDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}/status")
    public AdvisoryResponseDto updateStatus(
            @PathVariable("id") Long id, 
            @RequestParam("status") String status,
            @RequestParam(value = "replyMessage", required = false) String replyMessage) { 
        return service.updateStatus(id, status, replyMessage);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return Map.of(
            "message", "Advisory deleted successfully",
            "id", id
        );
    }

    @GetMapping("/stats/admin")
    public Map<String, Long> getAdminStats() {
    return service.getAdminStats();
    }

    @GetMapping("/stats/advisories")
    public Map<String, Long> getAdvisoryStats() {
    return service.getAdvisoryStats(); 
    }

    @PutMapping("/{id}/respond")
    public ResponseEntity<?> respond(@PathVariable("id") Long id, @RequestBody Map<String, String> request) {
    try {
        service.respondAdvisory(id, request.get("status"), request.get("replyMessage"));
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        // Esto imprimirá el error real en la consola de Spring Boot
        e.printStackTrace(); 
        return ResponseEntity.status(500).body("Error: " + e.getMessage());
    }
}
    


}
