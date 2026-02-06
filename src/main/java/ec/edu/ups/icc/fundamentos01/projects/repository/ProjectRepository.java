package ec.edu.ups.icc.fundamentos01.projects.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.fundamentos01.projects.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    // Aquí podrías agregar métodos personalizados después, como:
    List<ProjectEntity> findByProgrammerId(String programmerId);
}