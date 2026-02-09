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
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProjectResponseDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProjectEntity getOne(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public ProjectResponseDto create(@Valid @RequestBody CreateProjectDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ProjectEntity update(@PathVariable Long id, @RequestBody ProjectEntity entity) {
        return service.update(id, entity);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        service.delete(id);
        return Map.of("message", "Project deleted", "id", id);
    }


}