package ec.edu.ups.icc.fundamentos01.schedules.mappers;

import ec.edu.ups.icc.fundamentos01.schedules.dtos.ScheduleResponseDto;
import ec.edu.ups.icc.fundamentos01.schedules.entities.ScheduleEntity;

public class ScheduleMapper {
    
    public static ScheduleResponseDto toDto(ScheduleEntity entity) {
        return new ScheduleResponseDto(entity.getId(), entity.getTimeSlot(), entity.getProgrammerEmail());
    }
}