package ec.edu.ups.icc.fundamentos01.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.icc.fundamentos01.projects.entity.ProjectEntity;
import ec.edu.ups.icc.fundamentos01.projects.service.ProjectServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200") 
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @GetMapping
    public List<ProjectEntity> getProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public ProjectEntity createProject(@RequestBody ProjectEntity project) {
        return projectService.saveProject(project);
    }
}