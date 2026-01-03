package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.models.User;
import ec.edu.ups.icc.fundamentos01.users.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo; 

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponseDto> findAll() {
        // Obtenemos de la BD y mapeamos a DTO
        return userRepo.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        // Usamos el modelo de dominio para la lógica y convertimos a Entidad
        User user = User.fromDto(dto);
        UserEntity entity = user.toEntity();

        UserEntity saved = userRepo.save(entity); // Guardado real en Postgres
        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        // Actualizamos los campos usando los nuevos Getters del DTO
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null) {
            entity.setPassword(dto.getPassword());
        }

        UserEntity updated = userRepo.save(entity);
        return UserMapper.toResponse(updated);
    }

    @Override
    public UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (dto.getName() != null)
            entity.setName(dto.getName());
        if (dto.getEmail() != null)
            entity.setEmail(dto.getEmail());

        UserEntity updated = userRepo.save(entity);
        return UserMapper.toResponse(updated);
    }

    @Override
    public void delete(int id) {
        userRepo.findById((long) id)
                .ifPresentOrElse(
                        userRepo::delete, 
                        () -> {
                            throw new IllegalStateException("Usuario no encontrado"); // Error si no existe
                        });
    }
}