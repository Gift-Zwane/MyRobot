package com.apocalypse.robot.services;

import com.apocalypse.robot.entities.Survivor;
import com.apocalypse.robot.repositories.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurvivorService {
    private final SurvivorRepository survivorRepository;

    @Autowired
    public SurvivorService(SurvivorRepository survivorRepository) {
        this.survivorRepository = survivorRepository;
    }

    public Survivor registerSurvivor(Survivor survivor) {
        // Perform any additional validation or business logic
        return survivorRepository.save(survivor);
    }


    //my additional service code for reading Survivors from line 24 to 27
    public Survivor getSurvivorById(Long survivorId) {
        return survivorRepository.findById(survivorId)
                .orElseThrow(() -> new RuntimeException("Survivor not found with id: " + survivorId));
    }

    //I added a method from line 30 to 36 for updating survivor location.
    public Survivor updateSurvivorLocation(Long survivorId, double newLatitude, double newLongitude) {
        Survivor survivor = survivorRepository.findById(survivorId)
                .orElseThrow(() -> new IllegalArgumentException("Survivor not found with id: " + survivorId));

        survivor.updateLocation(newLatitude, newLongitude);
        return survivorRepository.save(survivor);
    }



    //Modify the SurvivorService to include the reportInfection method with the correct method name.
    public Survivor reportInfection(Long survivorId) {
        Survivor survivor = survivorRepository.findById(survivorId)
                .orElseThrow(() -> new IllegalArgumentException("Survivor not found with id: " + survivorId));

        // Check if survivor is already infected
        if (!survivor.isInfected()) {
            survivor.incrementInfectionReports();

            // If at least three reports, mark as infected
            if (survivor.getInfectionReports() >= 3) {
                survivor.markAsInfected();
            }

            return survivorRepository.save(survivor);
        } else {
           // throw new IllegalStateException("Survivor is already infected.");
            return survivorRepository.save(survivor);
        }
    }

}
