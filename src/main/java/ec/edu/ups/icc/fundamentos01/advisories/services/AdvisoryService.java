package ec.edu.ups.icc.fundamentos01.advisories.services;

import java.util.List;
import ec.edu.ups.icc.fundamentos01.advisories.dtos.*;

public interface AdvisoryService {
    
    List<AdvisoryResponseDto> findAll();
    AdvisoryResponseDto findOne(Long id);
    AdvisoryResponseDto create(CreateAdvisoryDto dto);
    AdvisoryResponseDto updateStatus(Long id, String status, String replyMessage);
    void delete(Long id);
}