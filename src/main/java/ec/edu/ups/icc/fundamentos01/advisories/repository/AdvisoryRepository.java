package ec.edu.ups.icc.fundamentos01.advisories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.fundamentos01.advisories.entities.AdvisoryEntity;

@Repository

public interface AdvisoryRepository extends JpaRepository<AdvisoryEntity, Long> {
    
}