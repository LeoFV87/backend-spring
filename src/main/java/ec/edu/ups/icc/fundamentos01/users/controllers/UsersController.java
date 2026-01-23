package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.fundamentos01.products.dtos.ProductsResponseDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.CreateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.PartialUpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UpdateUserDto;
import ec.edu.ups.icc.fundamentos01.users.dtos.UserResponseDto;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {

   private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Object findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody CreateUserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public Object update(@PathVariable int id, @Valid @RequestBody UpdateUserDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public Object partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {
        return service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id) {
    service.delete(id); 
    return java.util.Map.of(
        "message", "Deleted successfully",
        "id", id
    );
   }

   @GetMapping({"/{userId}/products", "/{userId}/products-v2"})
    public Page<ProductsResponseDto> findUserProducts(
            @PathVariable Long userId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        
        return service.findProductsByUserIdWithFilters(userId, name, minPrice, maxPrice, categoryId, page, size, sort);
    }


}
