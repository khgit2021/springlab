package com.example.springlab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//This is a repository for database

@Repository
public interface TrainingServiceJpa extends JpaRepository<Training, Integer> {

}
