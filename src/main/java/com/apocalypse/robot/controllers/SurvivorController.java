package com.apocalypse.robot.controllers;

import com.apocalypse.robot.entities.Survivor;
import com.apocalypse.robot.repositories.SurvivorRepository;
import com.apocalypse.robot.services.SurvivorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/survivors")
public class SurvivorController {
    private final SurvivorService survivorService;

    @Autowired
    public SurvivorController(SurvivorService survivorService) {
        this.survivorService = survivorService;
    }

    @Autowired
    private SurvivorRepository survivorRepository;

    @PostMapping("/register")
    public ResponseEntity<Survivor> registerSurvivor(@RequestBody Survivor survivor) {
        Survivor registeredSurvivor = survivorService.registerSurvivor(survivor);
        return new ResponseEntity<>(registeredSurvivor, HttpStatus.CREATED);
    }

    //my additional service code for reading Survivors from line 27 to 31
    @GetMapping("/{survivorId}")
    public ResponseEntity<Survivor> getSurvivorById(@PathVariable Long survivorId) {
        Survivor survivor = survivorService.getSurvivorById(survivorId);
        return new ResponseEntity<>(survivor, HttpStatus.OK);
    }

    //I Added a new endpoint in this SurvivorController from line 34 to 41 to handle survivor location updates.
    @PutMapping("/{id}/update-location")
    public ResponseEntity<Survivor> updateSurvivorLocation(
            @PathVariable Long id,
            @RequestParam double newLatitude,
            @RequestParam double newLongitude) {
        Survivor updatedSurvivor = survivorService.updateSurvivorLocation(id, newLatitude, newLongitude);
        return new ResponseEntity<>(updatedSurvivor, HttpStatus.OK);
    }

    //I Added a new endpoint in the SurvivorController from line 44 to 48 to handle reporting a survivor as infected.
    @PutMapping("/{id}/report-infection")
    public ResponseEntity<Survivor> reportInfection(@PathVariable Long id) {
        Survivor reportedSurvivor = survivorService.reportInfection(id);
        return new ResponseEntity<>(reportedSurvivor, HttpStatus.OK);
    }

    //endpoint to get the percentage of the infected
    @GetMapping("/infection/percentage")
    public ResponseEntity<Double> getPercentageOfInfectedSurvivors() {
        List<Survivor> allSurvivors = survivorRepository.findAll();
        if (allSurvivors.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        long infectedCount = allSurvivors.stream().filter(Survivor::isInfected).count();
        double percentage = (double) infectedCount / allSurvivors.size() * 100;

        return ResponseEntity.ok(percentage);
    }


    //endpoint to get the percentage of the infected
    @GetMapping("/noninfection/percentage")
    public ResponseEntity<Double> getPercentageOfNonInfectedSurvivors() {
        List<Survivor> allSurvivors = survivorRepository.findAll();
        if (allSurvivors.isEmpty()) {
            return ResponseEntity.ok(0.0);
        }

        long nonInfectedCount = allSurvivors.stream().filter(survivor -> !survivor.isInfected()).count();
        double percentage = (double) nonInfectedCount / allSurvivors.size() * 100;

        return ResponseEntity.ok(percentage);
    }


    //endpoint to get the List of infected survivors
    @GetMapping("/infected")
    public ResponseEntity<List<Survivor>> getListOfInfectedSurvivors() {
        List<Survivor> infectedSurvivors = survivorRepository.findByInfected(true);

        if (infectedSurvivors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(infectedSurvivors);
        }
    }


    //endpoint to get the List of non-infected survivors
    @GetMapping("/noninfected")
    public ResponseEntity<List<Survivor>> getListOfNonInfectedSurvivors() {
        List<Survivor> nonInfectedSurvivors = survivorRepository.findByInfected(false);

        if (nonInfectedSurvivors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok(nonInfectedSurvivors);
        }
    }

    //endpoint to get the List of Robots
    @GetMapping("/robots")
    public ResponseEntity<String> getListOfRobots() {
        // Assuming you are using RestTemplate for making HTTP requests
        RestTemplate restTemplate = new RestTemplate();
        String robotData = restTemplate.getForObject("https://robotstakeover20210903110417.azurewebsites.net/robotcpu", String.class);

        if (robotData != null && !robotData.isEmpty()) {
            return ResponseEntity.ok(robotData);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching robot data");
        }
    }

}
