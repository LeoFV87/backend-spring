package ec.edu.ups.icc.fundamentos01.schedules.dtos;

public record ScheduleResponseDto(
    Long id,
    String timeSlot,
    String programmerEmail
) {}