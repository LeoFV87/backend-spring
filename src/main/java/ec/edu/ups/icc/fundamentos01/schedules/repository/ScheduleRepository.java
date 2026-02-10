package ec.edu.ups.icc.fundamentos01.schedules.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ups.icc.fundamentos01.schedules.entities.ScheduleEntity;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByProgrammerEmailIgnoreCase(String email);
    Optional<ScheduleEntity> findByProgrammerEmailAndTimeSlot(String email, String timeSlot);
    
}