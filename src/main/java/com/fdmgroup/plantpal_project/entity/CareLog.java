package com.fdmgroup.plantpal_project.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "care_logs")
public class CareLog {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Care type is required")
    @Size(max = 50, message = "Care type must be less than 50 characters")
    private String careType;
    
    @NotNull(message = "Care date is required")
    private LocalDate careDate;
    
    @Size(max = 500, message = "Notes must be less than 500 characters")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    @NotNull(message = "Plant is required")
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
