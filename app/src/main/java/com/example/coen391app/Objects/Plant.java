package com.example.coen391app.Objects;

import java.io.Serializable;

public class Plant extends PlantTemplate implements Serializable {

    private String nickname;
    private PlantTemplate template;
    private int age;
    private double soilMoistureLevel;
    private double temperature;
    private double uvLighting;
    private boolean default_plant;

    public Plant(String nickname, PlantTemplate template) {
        super();
        this.template = template;
        this.nickname = nickname;
        this.scientificName = this.template.getScientificName();
        this.commonName = this.template.getCommonName();
        this.description = this.template.getDescription();
        this.age = 0;
        this.temperature = 0;
        this.soilMoistureLevel = 0;
        this.uvLighting = 0;
        this.recommendedTemp = this.template.getRecommendedTemp();
        this.recommendedSoilMoisture = this.template.getRecommendedSoilMoisture();
        this.recommendedUvLight = this.template.getRecommendedUvLight();
        this.default_plant = false;
    }

    public Plant(){

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PlantTemplate getTemplate() {
        return template;
    }

    public void setTemplate(PlantTemplate template) {
        this.template = template;
    }

    public boolean isDefault_plant() {
        return default_plant;
    }

    public void setDefault_plant(boolean default_plant) {
        this.default_plant = default_plant;
    }
}
