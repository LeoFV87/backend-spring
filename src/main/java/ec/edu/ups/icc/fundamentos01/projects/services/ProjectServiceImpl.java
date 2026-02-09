package ec.edu.ups.icc.fundamentos01.projects.services;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.projects.dtos.CreateProjectDto;
import ec.edu.ups.icc.fundamentos01.projects.dtos.ProjectResponseDto;
import ec.edu.ups.icc.fundamentos01.projects.entities.ProjectEntity;
import ec.edu.ups.icc.fundamentos01.projects.mappers.ProjectMapper;
import ec.edu.ups.icc.fundamentos01.projects.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

   private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectResponseDto> findAll() {
    return repository.findAll().stream()
            .map(ProjectMapper::toResponse)
            .toList();
    }

    @Override
    public ProjectEntity findOne(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado"));
    }

    @Override
    public ProjectResponseDto create(CreateProjectDto dto) {
    ProjectEntity entity = ProjectMapper.toEntity(dto);
    return ProjectMapper.toResponse(repository.save(entity));
    }

    @Override
    public ProjectEntity update(Long id, ProjectEntity entity) {
        ProjectEntity existing = findOne(id);
        
        existing.setTitle(entity.getTitle());
        existing.setDescription(entity.getDescription());
        existing.setImageUrl(entity.getImageUrl());
        existing.setDemoUrl(entity.getDemoUrl());
        existing.setRepoUrl(entity.getRepoUrl());
        existing.setRole(entity.getRole());
        existing.setType(entity.getType());
        existing.setTechnologies(entity.getTechnologies());

        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        ProjectEntity entity = findOne(id);
        repository.delete(entity);
    }
    
}