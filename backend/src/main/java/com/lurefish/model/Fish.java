package com.lurefish.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "fish")
@NoArgsConstructor
@AllArgsConstructor
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "scientific_name", nullable = false)
    private String scientificName;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String habitat;

    @Column(nullable = false)
    private String season;

    @Column(name = "water_layer", nullable = false)
    private String waterLayer;

    @Column(nullable = false)
    private String difficulty;

    @Column(nullable = false)
    private String tackle;

    @ElementCollection
    @CollectionTable(name = "fish_lures", joinColumns = @JoinColumn(name = "fish_id"))
    @Column(name = "lure")
    private List<String> lures;

    @ElementCollection
    @CollectionTable(name = "fish_techniques", joinColumns = @JoinColumn(name = "fish_id"))
    @Column(name = "technique", length = 500)
    private List<String> techniques;

    @ElementCollection
    @CollectionTable(name = "fish_tips", joinColumns = @JoinColumn(name = "fish_id"))
    @Column(name = "tip", length = 500)
    private List<String> tips;

    @Column(name = "size_range", nullable = false)
    private String sizeRange;

    @Column(name = "weight_range", nullable = false)
    private String weightRange;

    @Column(name = "image_url")
    private String imageUrl;

    // Getters and Setters
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

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getWaterLayer() {
        return waterLayer;
    }

    public void setWaterLayer(String waterLayer) {
        this.waterLayer = waterLayer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTackle() {
        return tackle;
    }

    public void setTackle(String tackle) {
        this.tackle = tackle;
    }

    public List<String> getLures() {
        return lures;
    }

    public void setLures(List<String> lures) {
        this.lures = lures;
    }

    public List<String> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<String> techniques) {
        this.techniques = techniques;
    }

    public List<String> getTips() {
        return tips;
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    public String getSizeRange() {
        return sizeRange;
    }

    public void setSizeRange(String sizeRange) {
        this.sizeRange = sizeRange;
    }

    public String getWeightRange() {
        return weightRange;
    }

    public void setWeightRange(String weightRange) {
        this.weightRange = weightRange;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}