package com.example.coen391app.Objects;

import java.util.ArrayList;
import java.util.List;

public class PlantTemplateProvider {

    public static List<PlantTemplate> getPlantTmeplates() {
        List<PlantTemplate> plantTemplates = new ArrayList<>();
        plantTemplates.add(new PlantTemplate(
                "Monstera Deliciosa",
                "Swiss Cheese Plant",
                "A striking tropical plant with large, glossy, heart-shaped leaves that develop natural splits and holes as they mature, making it a favorite for adding a bold, lush vibe to interiors.",
                23,
                50,
                300));
        plantTemplates.add(new PlantTemplate(
                "Epipremnum aureum",
                "Pothos",
                "A hardy, trailing vine with vibrant, heart-shaped leaves that come in various shades of green and gold, known for its air-purifying qualities and easy-care nature, perfect for beginners.",
                21,
                40,
                300));
        plantTemplates.add(new PlantTemplate(
                "Sansevieria trifasciata",
                "Snake Plant",
                "A robust and architectural plant with upright, sword-like leaves in green with yellow or silver patterns, celebrated for its ability to thrive in low light and improve air quality.",
                22.5,
                25,
                300));
        plantTemplates.add(new PlantTemplate(
                "Spathiphyllum",
                "Peace Lily",
                "An elegant plant with dark green leaves and distinctive white blooms that resemble a flag of peace, known for its low maintenance and excellent air-purifying properties.",
                25,
                60,
                300));
        plantTemplates.add(new PlantTemplate(
                "Ficus lyrata",
                "Fiddle Leaf Fig",
                "A trendy plant with large, violin-shaped, glossy green leaves, perfect for making a bold statement in modern homes, though it requires bright, indirect light and consistent care.",
                22.5,
                50,
                300));
        return plantTemplates;
    }
}
