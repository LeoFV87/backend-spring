package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
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
        return userRepo.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        // Lanzamos NotFoundException para generar un error 404 limpio
        return userRepo.findById((long) id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        // Usamos ConflictException para errores de duplicidad (Email)
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("El email '" + dto.getEmail() + "' ya está registrado");
        }

        User user = User.fromDto(dto);
        UserEntity saved = userRepo.save(user.toEntity());
        return User.fromEntity(saved).toResponseDto();
    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Usuario no encontrado"));

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
                .orElseThrow(() -> new NotFoundException("No se puede actualizar: Usuario no encontrado"));

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
                            // Error semántico 404 en lugar de error interno
                            throw new NotFoundException("No se puede eliminar: Usuario no encontrado");
                        });
    }
}