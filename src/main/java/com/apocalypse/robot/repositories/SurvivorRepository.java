package com.apocalypse.robot.repositories;

import com.apocalypse.robot.entities.Survivor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurvivorRepository extends JpaRepository<Survivor, Long> {
    List<Survivor> findByInfected(boolean infected);
}
