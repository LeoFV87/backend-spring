package ec.edu.ups.icc.fundamentos01.users.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;

@Service
public interface UserService {

    List<UserResponseDto> findAll();

    Object findOne(int id);

    UserResponseDto create(CreateUserDto dto);

    Object update(int id, UpdateUserDto dto);

    void delete(int id);

    Object partialUpdate(int id, PartialUpdateUserDto dto);

    Page<ProductsResponseDto> findProductsByUserIdWithFilters(
        Long userId, String name, Double minPrice, Double maxPrice, Long categoryId,
        int page, int size, String[] sort
    );

}