package ec.edu.ups.icc.fundamentos01.users.mappers;

import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.models.User;

public class UserMapper {

   // Convierte la Entidad de la BD a DTO de respuesta
    public static UserResponseDto toResponse(UserEntity entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId().intValue());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        // Se obtiene la fecha de BaseModel
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        return dto;
    }

    // Método de apoyo si aún usas el modelo de dominio User
    public static UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }
}