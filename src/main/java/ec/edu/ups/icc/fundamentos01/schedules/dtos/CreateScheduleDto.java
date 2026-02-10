package ec.edu.ups.icc.fundamentos01.schedules.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateScheduleDto(
    @NotBlank(message = "El horario no puede estar vacío")
    String timeSlot
) {}