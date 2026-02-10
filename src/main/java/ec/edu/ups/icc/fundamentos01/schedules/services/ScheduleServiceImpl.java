package ec.edu.ups.icc.fundamentos01.schedules.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.fundamentos01.schedules.entities.ScheduleEntity;
import ec.edu.ups.icc.fundamentos01.schedules.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    
    private final ScheduleRepository repository;

    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> findMySlots() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByProgrammerEmailIgnoreCase(email).stream()
                .map(ScheduleEntity::getTimeSlot)
                .toList();
    }

    @Override
    public void addSlot(String slot) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ScheduleEntity entity = new ScheduleEntity();
        entity.setProgrammerEmail(email);
        entity.setTimeSlot(slot);
        repository.save(entity);
    }

    @Override
    public void removeSlot(String slot) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        repository.findByProgrammerEmailAndTimeSlot(email, slot)
                .ifPresent(repository::delete);
    }
}