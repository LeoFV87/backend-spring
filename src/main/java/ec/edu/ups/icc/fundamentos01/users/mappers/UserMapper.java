package ec.edu.ups.icc.fundamentos01.users.mappers;

import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import java.util.ArrayList;

public class UserMapper {

    
    //Convierte la Entidad (BD) al DTO de Respuesta (JSON para Angular)
     
    public static UserResponseDto toResponse(UserEntity entity) {
        if (entity == null) return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setDisplayName(entity.getDisplayName());
        dto.setDescription(entity.getDescription());
        dto.setPhotoURL(entity.getPhotoURL());
        dto.setGithub(entity.getGithub());
        dto.setLinkedin(entity.getLinkedin());
        dto.setRole(entity.getRole());
        dto.setSpecialty(entity.getSpecialty());

        // Evitamos nulls en las listas para que Angular no de errores de tipo
        dto.setSkills(entity.getSkills() != null ? entity.getSkills() : new ArrayList<>());
        dto.setAvailability(entity.getAvailability() != null ? entity.getAvailability() : new ArrayList<>());

        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        
        return dto;
    }

    
    //Convierte el DTO de Creación (Registro) a una Entidad nueva
    
    public static UserEntity toEntity(CreateUserDto dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword()); 
        entity.setDisplayName(dto.getDisplayName());
        entity.setRole(dto.getRole());
        
        // Campos opcionales iniciales
        entity.setDescription(dto.getDescription());
        entity.setPhotoURL(dto.getPhotoURL());
        entity.setGithub(dto.getGithub());
        entity.setLinkedin(dto.getLinkedin());
        entity.setSpecialty(dto.getSpecialty());
        
        entity.setSkills(dto.getSkills() != null ? dto.getSkills() : new ArrayList<>());
        entity.setAvailability(dto.getAvailability() != null ? dto.getAvailability() : new ArrayList<>());
        
        return entity;
    }

    
    // Mapea los datos de una actualización parcial a una entidad existente
    public static void updateEntity(UserEntity entity, UpdateProfileDto dto) {
    if (dto == null || entity == null) return;

    if (dto.description() != null) entity.setDescription(dto.description());
    if (dto.specialty() != null) entity.setSpecialty(dto.specialty());
    if (dto.photoURL() != null) entity.setPhotoURL(dto.photoURL());
    if (dto.linkedin() != null) entity.setLinkedin(dto.linkedin());
    if (dto.github() != null) entity.setGithub(dto.github());
    if (dto.skills() != null) entity.setSkills(dto.skills());
    
   
    if (dto.availability() != null) entity.setAvailability(dto.availability()); 
    }
}