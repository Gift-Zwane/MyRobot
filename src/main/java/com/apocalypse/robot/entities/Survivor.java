package com.apocalypse.robot.entities;

import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
public class Survivor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    private String name;
    private int age;
    private String gender;
    private double latitude;
    private double longitude;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    //i added infected code from line 29 to 34
    private boolean infected;
    private int infectionReports;

    public Survivor() {
    }

    public Survivor(Long id, String name, int age, String gender, double latitude, double longitude, Inventory inventory, boolean infected, int infectionReports) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
        this.inventory = inventory;
        this.infected = infected;
        this.infectionReports = infectionReports;
    }

    //I Added a method from line 26 to 29 here in the Survivor entity to update the location.
    public void updateLocation(double newLatitude, double newLongitude) {
        this.latitude = newLatitude;
        this.longitude = newLongitude;
    }

    //i added infected code from line 53 to 59
    public void markAsInfected() {
        this.infected = true;
    }

    public void incrementInfectionReports() {
        this.infectionReports++;
    }

    // getters and setters


    public int getInfectionReports() {
        return infectionReports;
    }

    public void setInfectionReports(int infectionReports) {
        this.infectionReports = infectionReports;
    }

    public boolean isInfected() {
        return infected;
    }


    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }



    @Override
    public String toString() {
        return "Survivor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", inventory=" + inventory +
                '}';
    }





}
