package ec.edu.ups.icc.fundamentos01.projects.services;

import java.util.List;

import ec.edu.ups.icc.fundamentos01.projects.dtos.CreateProjectDto;
import ec.edu.ups.icc.fundamentos01.projects.dtos.ProjectResponseDto;
import ec.edu.ups.icc.fundamentos01.projects.entities.ProjectEntity;


public interface ProjectService {
    List<ProjectResponseDto> findAll();
    ProjectEntity findOne(Long id);
    ProjectResponseDto create(CreateProjectDto dto);
    ProjectEntity update(Long id, ProjectEntity entity);
    void delete(Long id);
    
}
