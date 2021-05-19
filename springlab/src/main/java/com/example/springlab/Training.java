package com.example.springlab;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
public class Training {

	@Id
	@GeneratedValue
	private int id;
	private int empId;
	
	@Size(min=2, message="Description should have at least two characters")
	private String descr;
	
	@Past
	private Date trainingDate;
	
	
	protected Training() {
		super();
	}

	public Training(int id, int empId, String descr, Date trainingDate) {
		super();
		this.id = id;
		this.empId = empId;
		this.descr = descr;
		this.trainingDate = trainingDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Date getTrainingDate() {
		return trainingDate;
	}

	public void setTrainingDate(Date trainingDate) {
		this.trainingDate = trainingDate;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", empId=" + empId + ", descr=" + descr + ", trainingDate=" + trainingDate + "]";
	}
	
	
	
	
}
