package ec.edu.ups.icc.fundamentos01.advisories.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import ec.edu.ups.icc.fundamentos01.exception.domain.NotFoundException;
import ec.edu.ups.icc.fundamentos01.advisories.dtos.*;
import ec.edu.ups.icc.fundamentos01.advisories.entities.AdvisoryEntity;
import ec.edu.ups.icc.fundamentos01.advisories.mappers.AdvisoryMapper;
import ec.edu.ups.icc.fundamentos01.advisories.repository.AdvisoryRepository;

@Service
public class AdvisoryServiceImpl implements AdvisoryService {

    private final AdvisoryRepository repository;

    public AdvisoryServiceImpl(AdvisoryRepository repository) {
        this.repository = repository;
    }

    // Validación de Propiedad para asegurar que solo el cliente, programador asignado o admin puedan acceder/modificar
    private void validateOwnership(AdvisoryEntity advisory) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = auth.getName();
    
    boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    // Se usa el equalsIgnoreCase para comparar los correos de forma segura
    if (!isAdmin && !advisory.getClientEmail().equalsIgnoreCase(currentEmail) 
                 && !advisory.getProgrammerId().equalsIgnoreCase(currentEmail)) {
        throw new AccessDeniedException("No tienes permiso para acceder a este recurso");
        }
    }

    @Override
    public List<AdvisoryResponseDto> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();
        
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<AdvisoryEntity> entities;
        if (isAdmin) {
            entities = repository.findAll();
        } else {
            entities = repository.findAll().stream()
                .filter(a -> a.getClientEmail().equals(currentEmail) || a.getProgrammerId().equals(currentEmail))
                .toList();
        }

        return entities.stream()
                .map(AdvisoryMapper::toResponse)
                .toList();
    }

    @Override
    public AdvisoryResponseDto findOne(Long id) {
        AdvisoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asesoría no encontrada"));
        
        validateOwnership(entity);
        return AdvisoryMapper.toResponse(entity);
    }

    @Override
    public AdvisoryResponseDto create(CreateAdvisoryDto dto) {
        AdvisoryEntity entity = AdvisoryMapper.toEntity(dto);
        return AdvisoryMapper.toResponse(repository.save(entity));
    }

    @Override
    public AdvisoryResponseDto updateStatus(Long id, String status, String replyMessage) {
        AdvisoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Asesoría no encontrada"));
        
        validateOwnership(entity);
        
        entity.setStatus(status);
        if (replyMessage != null) {
            entity.setReplyMessage(replyMessage);
        }
        
        return AdvisoryMapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        AdvisoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se puede eliminar: Asesoría no encontrada"));
        
        validateOwnership(entity);
        repository.delete(entity);
    }

    @Override
    public List<AdvisoryResponseDto> findMyAdvisories() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = auth.getName();

    return repository.findAll().stream()
            .filter(a -> a.getClientEmail().equalsIgnoreCase(currentEmail))
            .map(AdvisoryMapper::toResponse)
            .toList();
    }

    @Override
    public List<AdvisoryResponseDto> findAssignedAdvisories() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String currentEmail = auth.getName();

    return repository.findAll().stream()
            .filter(a -> a.getProgrammerId().equalsIgnoreCase(currentEmail))
            .map(AdvisoryMapper::toResponse)
            .toList();
    }

    @Override
    public Map<String, Long> getAdminStats() {
    return Map.of(
        "total", repository.count(),
        "pending", repository.countByStatus("PENDING"),
        "accepted", repository.countByStatus("ACCEPTED"),
        "rejected", repository.countByStatus("REJECTED")
    );
    }




}