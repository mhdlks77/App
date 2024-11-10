package com.example.coen391app.Objects;

import java.io.Serializable;

public class Plant implements Serializable {

    private String name;
    private String type;
    private int age;
    private double soilMoistureLevel;
    private double temperature;
    private double uvLighting;

    public Plant(String name, String type) {
        this.name = name;
        this.type = type;
        this.age = 0;
        this.temperature = 0;
        this.soilMoistureLevel = 0;
        this.uvLighting = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSoilMoistureLevel() {
        return soilMoistureLevel;
    }

    public void setSoilMoistureLevel(double soilMoistureLevel) {
        this.soilMoistureLevel = soilMoistureLevel;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getUvLighting() {
        return uvLighting;
    }

    public void setUvLighting(double uvLighting) {
        this.uvLighting = uvLighting;
    }
}
