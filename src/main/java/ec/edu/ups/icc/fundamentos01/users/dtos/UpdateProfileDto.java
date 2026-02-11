package ec.edu.ups.icc.fundamentos01.users.dtos;

import java.util.List;

public record UpdateProfileDto(
    String name,
    String description,
    String specialty,
    String photoURL,
    String linkedin,
    String github,
    List<String> skills,
    List<String> availability
) {}