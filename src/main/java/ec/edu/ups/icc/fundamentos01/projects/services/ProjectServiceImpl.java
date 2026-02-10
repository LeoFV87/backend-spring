package ec.edu.ups.icc.fundamentos01.projects.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.projects.dtos.CreateProjectDto;
import ec.edu.ups.icc.fundamentos01.projects.dtos.ProjectResponseDto;
import ec.edu.ups.icc.fundamentos01.projects.entities.ProjectEntity;
import ec.edu.ups.icc.fundamentos01.projects.mappers.ProjectMapper;
import ec.edu.ups.icc.fundamentos01.projects.repository.ProjectRepository;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.repository.UserRepository;

import org.springframework.security.core.Authentication;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName(); 

        ProjectEntity entity = ProjectMapper.toEntity(dto);
        entity.setProgrammerId(currentEmail); 
        
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

    @Override
    public List<ProjectResponseDto> findMyProjects() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName(); 

        return repository.findAll().stream()
                .filter(p -> p.getProgrammerId() != null && p.getProgrammerId().equalsIgnoreCase(currentEmail))
                .map(ProjectMapper::toResponse)
                .toList();
    }

    @Override
    public List<ProjectResponseDto> findByProgrammerId(Long id) {
        //Buscamos el email del usuario usando su ID numérico
        String emailDelProgramador = userRepository.findById(id)
                .map(UserEntity::getEmail)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        //Filtramos los proyectos por ese EMAIL
        return repository.findAll().stream()
                .filter(p -> p.getProgrammerId() != null && p.getProgrammerId().equalsIgnoreCase(emailDelProgramador))
                .map(ProjectMapper::toResponse)
                .toList();
    }
}