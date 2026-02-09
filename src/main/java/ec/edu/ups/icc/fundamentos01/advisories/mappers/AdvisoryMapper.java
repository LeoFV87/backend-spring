package ec.edu.ups.icc.fundamentos01.advisories.mappers;

import ec.edu.ups.icc.fundamentos01.advisories.dtos.*;
import ec.edu.ups.icc.fundamentos01.advisories.entities.AdvisoryEntity;

public class AdvisoryMapper {

    public static AdvisoryResponseDto toResponse(AdvisoryEntity entity) {
        AdvisoryResponseDto dto = new AdvisoryResponseDto();
        dto.setId(entity.getId());
        dto.setClientEmail(entity.getClientEmail());
        dto.setClientName(entity.getClientName());
        dto.setTopic(entity.getTopic());
        dto.setStatus(entity.getStatus());
        dto.setTimeSlot(entity.getTimeSlot());
        dto.setReplyMessage(entity.getReplyMessage());
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null);
        return dto;
    }

    public static AdvisoryEntity toEntity(CreateAdvisoryDto dto) {
        AdvisoryEntity entity = new AdvisoryEntity();
        entity.setClientEmail(dto.getClientEmail());
        entity.setClientId(dto.getClientId());
        entity.setClientName(dto.getClientName());
        entity.setMessage(dto.getMessage());
        entity.setProgrammerId(dto.getProgrammerId());
        entity.setProgrammerName(dto.getProgrammerName());
        entity.setReplyMessage(dto.getReplyMessage());
        entity.setStatus(dto.getStatus());
        entity.setTimeSlot(dto.getTimeSlot());
        entity.setTopic(dto.getTopic());
        return entity;
    }
}