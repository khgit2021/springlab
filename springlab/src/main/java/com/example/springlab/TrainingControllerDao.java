package com.example.springlab;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class TrainingControllerDao {
	
	@Autowired
	private TrainingServiceDao serviceDao;
	
	@GetMapping("/trainings")
	public List<Training> retrieveAllTrainings() {
		return serviceDao.findAll();
	}
	
	@GetMapping("/trainings/{id}")
	//public Training retrieveTraining(@PathVariable int id) {
	public EntityModel<Training> retrieveTraining(@PathVariable int id) {
		Optional<Training> training = Optional.of(serviceDao.findOne(id));
		if (training == null) {
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

	@PostMapping("/trainings")
	public ResponseEntity<Object> createTraining(@Validated @RequestBody Training training) {
		Training savedTraining = serviceDao.save(training);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(savedTraining.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@DeleteMapping("/trainings/{id}")
	public void deleteTraining(@PathVariable int id) {
		Training training = serviceDao.deleteById(id);
		if (training == null) {
			throw new TrainingNotFoundException("id-" + id);
		}
		//	return training;
	}

	
	
	
}
