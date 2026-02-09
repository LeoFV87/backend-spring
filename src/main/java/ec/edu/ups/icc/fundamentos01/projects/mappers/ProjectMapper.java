package ec.edu.ups.icc.fundamentos01.projects.mappers;


import ec.edu.ups.icc.fundamentos01.projects.dtos.*;
import ec.edu.ups.icc.fundamentos01.projects.entities.ProjectEntity;

public class ProjectMapper {

    public static ProjectResponseDto toResponse(ProjectEntity entity) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageUrl(entity.getImageUrl());
        dto.setDemoUrl(entity.getDemoUrl());
        dto.setRepoUrl(entity.getRepoUrl());
        dto.setRole(entity.getRole());
        dto.setType(entity.getType());
        dto.setProgrammerId(entity.getProgrammerId());
        dto.setTechnologies(entity.getTechnologies());
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        return dto;
    }

    public static ProjectEntity toEntity(CreateProjectDto dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setImageUrl(dto.getImageUrl());
        entity.setDemoUrl(dto.getDemoUrl());
        entity.setRepoUrl(dto.getRepoUrl());
        entity.setRole(dto.getRole());
        entity.setType(dto.getType());
        entity.setProgrammerId(dto.getProgrammerId());
        entity.setTechnologies(dto.getTechnologies());
        return entity;
    }
}