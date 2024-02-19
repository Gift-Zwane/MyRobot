package com.apocalypse.robot.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int water;
    private int food;
    private int medication;
    private int ammunition;

    // getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getMedication() {
        return medication;
    }

    public void setMedication(int medication) {
        this.medication = medication;
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public Inventory() {
    }

    public Inventory(Long id, int water, int food, int medication, int ammunition) {
        this.id = id;
        this.water = water;
        this.food = food;
        this.medication = medication;
        this.ammunition = ammunition;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", water=" + water +
                ", food=" + food +
                ", medication=" + medication +
                ", ammunition=" + ammunition +
                '}';
    }
}
