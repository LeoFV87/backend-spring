package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;

public interface UserService {
    List<UserResponseDto> findAll();
    UserResponseDto findOne(int id);
    UserResponseDto create(CreateUserDto dto);
    UserResponseDto findMyProfile();
    UserResponseDto updateMyProfile(UpdateProfileDto dto);
    UserResponseDto update(int id, UpdateUserDto dto);
    UserResponseDto partialUpdate(int id, PartialUpdateUserDto dto);
    UserResponseDto changeRole(int id, String role);
    void delete(int id);
}