package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.fundamentos01.exception.domain.ConflictException;
import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import ec.edu.ups.icc.fundamentos01.users.mappers.UserMapper;
import ec.edu.ups.icc.fundamentos01.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto findMyProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByEmail(email)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Perfil no encontrado"));
    }

    @Override
    public UserResponseDto updateMyProfile(UpdateProfileDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity entity = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        UserMapper.updateEntity(entity, dto);
        return UserMapper.toResponse(userRepo.save(entity));
    }

    @Override
    public UserResponseDto changeRole(int id, String role) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        entity.setRole(role);
        return UserMapper.toResponse(userRepo.save(entity));
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
            .map(UserMapper::toResponse)
            .toList();
    }

    @Override
    public UserResponseDto findOne(int id) {
        return userRepo.findById((long) id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Usuario con ID " + id + " no encontrado"));
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new ConflictException("Email ya registrado");
        }

        UserEntity entity = UserMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        return UserMapper.toResponse(userRepo.save(entity));
    }

    @Override
    public UserResponseDto update(int id, UpdateUserDto dto) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        if (dto.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return UserMapper.toResponse(userRepo.save(entity));
    }

    @Override
    public UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());

        return UserMapper.toResponse(userRepo.save(entity));
    }

    @Override
    public void delete(int id) {
        UserEntity entity = userRepo.findById((long) id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        userRepo.delete(entity);
    }
}