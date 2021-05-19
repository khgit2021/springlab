package com.example.springlab;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TrainingServiceDao {
	
	private static List<Training> trainings = new ArrayList<>();
	
	private static int trainingsCount = 4;
	
	static {
		trainings.add(new Training(101, 101, "Class 101", new Date()));
		trainings.add(new Training(102, 102, "Class 102", new Date()));
		trainings.add(new Training(103, 103, "Class 101", new Date()));
		trainings.add(new Training(104, 103, "Class 102", new Date()));
	}

	public List<Training> findAll() {
		return trainings;
	}
	
	public Training save(Training training) {
		if (training.getId() == 0) {
			training.setId(++trainingsCount);
		}
		trainings.add(training);
		return training;
	}
	
	public Training findOne(int id) {
		for (Training training:trainings) {
			if (training.getId() == id) {
				return training;
			}
		}
		return null;
	}
	
	public Training deleteById(int id) {
		Iterator<Training> iterator = trainings.iterator();
		while (iterator.hasNext()) {
			Training training = iterator.next();
			if (training.getId() == id) {
				iterator.remove();
				return training;
			}
		}
		return null;
	}
	
}
