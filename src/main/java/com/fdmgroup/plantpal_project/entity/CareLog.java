package com.fdmgroup.plantpal_project.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "care_logs")
public class CareLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String careType;
    private LocalDate careDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public CareLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType;
    }

    public LocalDate getCareDate() {
        return careDate;
    }

    public void setCareDate(LocalDate careDate) {
        this.careDate = careDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
    
}
