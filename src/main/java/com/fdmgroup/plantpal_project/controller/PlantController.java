package com.fdmgroup.plantpal_project.controller;

import com.fdmgroup.plantpal_project.entity.Plant;
import com.fdmgroup.plantpal_project.service.PlantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling all Plant-related HTTP requests. Provides CRUD
 * operations for the Plant entity.
 */
@RestController
@RequestMapping("/api/plants")
public class PlantController {

	@Autowired
	private PlantService plantService;

	/**
	 * Retrieves all plants
	 * 
	 * @return List of all plants with 200 OK status
	 */
	@GetMapping
	public List<Plant> getAllPlants() {
		return plantService.findAllPlants();
	}

	/**
	 * Retrieves a specific plant by ID
	 * 
	 * @return Plant entity with 200 OK if found, 404 NOT FOUND if not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Plant> getPlantById(@PathVariable Long id) {
		Optional<Plant> plant = plantService.findPlantById(id);
		return plant.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Creates a new plant
	 * 
	 * @return Created Plant entity with 201 CREATED status
	 */
	@PostMapping
	public ResponseEntity<Plant> createPlant(@Valid @RequestBody Plant plant) {
		Plant savedPlant = plantService.savePlant(plant);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPlant);
	}

	/**
	 * Uploads an image for a specific plant
	 * @param id The plant ID to attach the image to
	 * @param file The image file to upload
	 * @return Success message with 200 OK if uploaded, 500 INTERNAL SERVER ERROR if failed, 404 NOT FOUND if plant doesn't exist
	 */
	@PostMapping("/{id}/image")
	public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		Optional<Plant> plant = plantService.findPlantById(id);
		if (plant.isPresent()) {
			try {
				Plant existingPlant = plant.get();
				existingPlant.setImageData(file.getBytes());
				existingPlant.setImageName(file.getOriginalFilename());
				existingPlant.setImageType(file.getContentType());
				plantService.updatePlant(existingPlant);
				return ResponseEntity.ok("Image uploaded successfully");

			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
			}
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Retrieves the image for a specific plant
	 * @param id The plant ID to get the image from
	 * @return Image bytes with correct content type and 200 OK if found, 404 NOT FOUND if plant or image doesn't exist
	 */
	@GetMapping("/{id}/image")
	public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
		Optional<Plant> plant = plantService.findPlantById(id);
		if (plant.isPresent() && plant.get().getImageData() != null) {
			Plant existingPlant = plant.get();
			return ResponseEntity.ok().contentType(MediaType.valueOf(existingPlant.getImageType()))
					.body(existingPlant.getImageData());
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Updates an existing plant
	 * 
	 * @return Updated Plant entity with 200 OK if found, 404 NOT FOUND if not found
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Plant> updatePlant(@PathVariable Long id, @Valid @RequestBody Plant plantDetails) {
		Optional<Plant> plant = plantService.findPlantById(id);
		if (plant.isPresent()) {
			Plant existingPlant = plant.get();
			existingPlant.setNickname(plantDetails.getNickname());
			existingPlant.setSpecies(plantDetails.getSpecies());
			existingPlant.setLocation(plantDetails.getLocation());
			return ResponseEntity.ok(plantService.updatePlant(existingPlant));
		}
		return ResponseEntity.notFound().build();
	}

	/**
	 * Deletes a plant by ID
	 * 
	 * @return 200 OK if deleted, 404 NOT FOUND if plant doesn't exist
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
		Optional<Plant> plant = plantService.findPlantById(id);
		if (plant.isPresent()) {
			plantService.deletePlantById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}