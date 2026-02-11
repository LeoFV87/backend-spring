package ec.edu.ups.icc.fundamentos01.projects.controllers;

import org.springframework.web.bind.annotation.*;
import ec.edu.ups.icc.fundamentos01.projects.dtos.CreateProjectDto;
import ec.edu.ups.icc.fundamentos01.projects.dtos.ProjectResponseDto;
import ec.edu.ups.icc.fundamentos01.projects.entities.ProjectEntity;
import ec.edu.ups.icc.fundamentos01.projects.services.ProjectService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    // 1. Ruta para el Dashboard del programador logueado
    // El Service debe obtener el ID del usuario desde el SecurityContext/Token
    @GetMapping("/my-projects")
    public List<ProjectResponseDto> getMyProjects() {
        return service.findMyProjects();
    }

    // 2. Ruta para ver el portafolio de otro (Público)
    // Cambiado de String a Long para consistencia con Angular y la BD
    @GetMapping("/programmer/{id}")
    public List<ProjectResponseDto> getByProgrammer(@PathVariable("id") Long id) {
        return service.findByProgrammerId(id);
    }

    @GetMapping
    public List<ProjectResponseDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProjectEntity getOne(@PathVariable("id") Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public ProjectResponseDto create(@Valid @RequestBody CreateProjectDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProjectEntity update(@PathVariable("id") Long id, @RequestBody ProjectEntity entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return Map.of("message", "Project deleted", "id", id);
    }
}