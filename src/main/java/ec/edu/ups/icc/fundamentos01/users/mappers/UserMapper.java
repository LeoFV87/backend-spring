package ec.edu.ups.icc.fundamentos01.users.mappers;

import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;

public class UserMapper {

    public static UserResponseDto toResponse(UserEntity entity) {
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
        dto.setSkills(entity.getSkills());
        dto.setAvailability(entity.getAvailability());
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        return dto;
    }

    public static UserEntity toEntity(CreateUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setDisplayName(dto.getDisplayName());
        entity.setDescription(dto.getDescription());
        entity.setPhotoURL(dto.getPhotoURL());
        entity.setGithub(dto.getGithub());
        entity.setLinkedin(dto.getLinkedin());
        entity.setRole(dto.getRole());
        entity.setSpecialty(dto.getSpecialty());
        entity.setSkills(dto.getSkills());
        entity.setAvailability(dto.getAvailability());
        return entity;
    }
}