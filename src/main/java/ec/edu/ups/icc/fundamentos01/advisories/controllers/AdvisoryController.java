package ec.edu.ups.icc.fundamentos01.advisories.controllers;

import java.util.List;
import java.util.Map;
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

     // El servicio filtrará automáticamente si es que es solo user 
     // O un administrador
    @GetMapping
    public List<AdvisoryResponseDto> getAll() {
        return service.findAll();
    }

    // Si no es tu asesoria lanza el error 403 Forbidden
    @GetMapping("/{id}")
    public AdvisoryResponseDto getOne(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public AdvisoryResponseDto create(@Valid @RequestBody CreateAdvisoryDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}/status")
    public AdvisoryResponseDto updateStatus(
            @PathVariable Long id, 
            @RequestParam String status,
            @RequestParam(required = false) String replyMessage) {

        // Solo si eres el dueño (programador) o ADMIN podras cambiar el estado
        return service.updateStatus(id, status, replyMessage);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        service.delete(id);
        return Map.of(
            "message", "Advisory deleted successfully",
            "id", id
        );
    }
}