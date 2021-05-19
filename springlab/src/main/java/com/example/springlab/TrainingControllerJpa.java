package com.example.springlab;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class TrainingControllerJpa {
	
	@Autowired
	private TrainingServiceJpa serviceJpa;
	
	@GetMapping("/jpa/trainings")
	public List<Training> retrieveAllTrainings() {
		return serviceJpa.findAll();
	}
	
	@GetMapping("/jpa/trainings/{id}")
	//public Training retrieveTraining(@PathVariable int id) {
	public EntityModel<Training> retrieveTraining(@PathVariable int id) {
		Optional<Training> training = serviceJpa.findById(id);
		if (!training.isPresent()) {
			throw new TrainingNotFoundException("id-" + id);
		}
		
		//Resource was replaced by EntityModel
        //ControllerLinkBuilder by WebMvcLinkBuilder
        EntityModel<Training> resource = EntityModel.of(training.get());
        WebMvcLinkBuilder linkto = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllTrainings());
        resource.add(linkto.withRel("all-trainings"));
		//return training;
		return resource;
	}

	@PostMapping("/jpa/trainings")
	public ResponseEntity<Object> createTraining(@Validated @RequestBody Training training) {
		Training savedTraining = serviceJpa.save(training);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedTraining.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping("/jpa/trainings/{id}")
	public void deleteTraining(@PathVariable int id) {
		serviceJpa.deleteById(id);
	}

	@PutMapping("/jpa/trainings/{id}")
	public ResponseEntity<Training> updateTraining(@PathVariable int id, @Validated @RequestBody Training updateTraining) {
		Optional<Training> training = serviceJpa.findById(id);
		if (!training.isPresent()) {
			throw new TrainingNotFoundException("id-" + id);
		}
		Training finalTraining = training.get();
		finalTraining.setDescr(updateTraining.getDescr());
		finalTraining.setEmpId(updateTraining.getEmpId());
		finalTraining.setTrainingDate(updateTraining.getTrainingDate());
		final Training updatedTraining = serviceJpa.save(finalTraining);
		return ResponseEntity.ok(updatedTraining);
		
	}
	
	/*
	 * @PatchMapping("/jpa/patch") public @ResponseBody ResponseEntity<String>
	 * patch() { return new ResponseEntity<String>("PATCH Response", HttpStatus.OK);
	 * }
	 */
}
