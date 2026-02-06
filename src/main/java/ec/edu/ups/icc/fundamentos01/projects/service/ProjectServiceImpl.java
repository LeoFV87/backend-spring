package ec.edu.ups.icc.fundamentos01.projects.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.projects.entity.ProjectEntity;
import ec.edu.ups.icc.fundamentos01.projects.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectServiceImpl {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectEntity saveProject(ProjectEntity project) {
        return projectRepository.save(project);
    }
}