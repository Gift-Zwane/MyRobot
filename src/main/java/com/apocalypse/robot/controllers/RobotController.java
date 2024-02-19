package com.apocalypse.robot.controllers;

import com.apocalypse.robot.entities.Robot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
@RequestMapping("/")
public class RobotController {

    @Value("${external.api.url}")
    private String apiUrl;

    @GetMapping("/robot")
    public ResponseEntity<String> getRobotData() {
        WebClient webClient = WebClient.create();

        String result = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // blocking call, in a real application consider using reactive programming

        System.out.println("----------------");
        System.out.println(result);
        System.out.println("-----------");

        // Return the JSON in the HTTP response
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(result);
    }
    @GetMapping("/flying")
    public ResponseEntity<List<Robot>> getFlyingRobots() {
        WebClient webClient = WebClient.create();

        List<Robot> robots = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(Robot.class)
                .doOnNext(robot -> System.out.println("Received Robot: " + robot))
                .filter(robot -> "Flying".equals(robot.getCategory().name()))
                .collectList()
                .block();// call, in a real application consider using reactive programming

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(robots);
    }
    @GetMapping("/land")
    public ResponseEntity<List<Robot>> getLandRobots() {
        WebClient webClient = WebClient.create();

        List<Robot> landRobots = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(Robot.class)
                .filter(robot -> Robot.Category.Land.equals(robot.getCategory()))
                .collectList()
                .block(); // blocking call, in a real application consider using reactive programming

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(landRobots);
    }




}




