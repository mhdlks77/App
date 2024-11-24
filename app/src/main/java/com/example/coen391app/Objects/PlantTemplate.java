package com.example.coen391app.Objects;

import java.util.ArrayList;
import java.util.List;

public class PlantTemplate {
    String scientificName;
    String commonName;



    String description;
    double recommendedTemp;
    double recommendedSoilMoisture;
    double recommendedUvLight;
    List<PlantTemplate> plantTemplates = new ArrayList<>();

    public PlantTemplate(String scientificName,
                         String commonName,
                         String description,
                         double recommendedTemp,
                         double recommendedSoilMoisture,
                         double recommendedUvLight){
        this.scientificName = scientificName;
        this.commonName = commonName;
        this.description = description;
        this.recommendedTemp = recommendedTemp;
        this.recommendedSoilMoisture = recommendedSoilMoisture;
        this.recommendedUvLight = recommendedUvLight;
    }

    public PlantTemplate() {

    }


    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public double getRecommendedTemp() {
        return recommendedTemp;
    }

    public void setRecommendedTemp(double recommendedTemp) {
        this.recommendedTemp = recommendedTemp;
    }

    public double getRecommendedSoilMoisture() {
        return recommendedSoilMoisture;
    }

    public void setRecommendedSoilMoisture(double recommendedSoilMoisture) {
        this.recommendedSoilMoisture = recommendedSoilMoisture;
    }

    public double getRecommendedUvLight() {
        return recommendedUvLight;
    }

    public void setRecommendedUvLight(double recommendedUvLight) {
        this.recommendedUvLight = recommendedUvLight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
