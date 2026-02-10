package ec.edu.ups.icc.fundamentos01.users.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.icc.fundamentos01.advisories.services.AdvisoryService;
import ec.edu.ups.icc.fundamentos01.users.dtos.*;
import ec.edu.ups.icc.fundamentos01.users.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService service;
  
    private final AdvisoryService advisoryService;

    public UsersController(UserService service, AdvisoryService advisoryService) {
        this.service = service;
        this.advisoryService = advisoryService;
    }

  
    @GetMapping("/profile")
    public UserResponseDto getMyProfile() {
        return service.findMyProfile();
    }

  
    @PutMapping("/profile")
    public UserResponseDto updateMyProfile(@Valid @RequestBody UpdateProfileDto dto) {
        return service.updateMyProfile(dto);
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto findOne(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody CreateUserDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable int id, @Valid @RequestBody UpdateUserDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}")
    public UserResponseDto partialUpdate(@PathVariable int id, @RequestBody PartialUpdateUserDto dto) {
        return service.partialUpdate(id, dto);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable int id) {
        service.delete(id); 
        return Map.of("message", "Deleted successfully", "id", id);
    }

    @PatchMapping("/{id}/role")
    public UserResponseDto changeRole(@PathVariable int id, @RequestParam String role) {
        return service.changeRole(id, role);
    }

    // ENDPOINT PARA LISTAR PROGRAMADORES
    @GetMapping("/role/{role}")
    public List<UserResponseDto> getByRole(@PathVariable String role) {
        return service.findByRole(role);
    }

    // ENDPOINT PARA LOS HORARIOS
    @GetMapping("/{id}/availability")
    public List<String> getAvailability(@PathVariable Long id) {
        return service.getAvailability(id);
    }

    @GetMapping("/stats/advisories")
    public ResponseEntity<Map<String, Long>> getAdvisoryStats() {
        // Llamamos al servicio para obtener los conteos
        return ResponseEntity.ok(advisoryService.getAdvisoryStats());
    }

}