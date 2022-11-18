package com.examples.empapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Models employee object
 */
@Entity
@Table
public class Employee {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private Integer age = null;
	@Column(name="department")
	private String department;
	@Column(name="designation")
	private String designation;
	@Column(name="country")
	private String country;
	
	public Employee()
	{}
	
	public Employee(int Id, String name, Integer age, String designation, String department, String country)
	{
		this.id = Id;
		this.name = name;
		this.age = age;
		this.designation = designation;
		this.department = department;		
		this.country = country;
	}

	public int getEmpId() {
		return id;
	}

	public void setEmpId(int empId) {
		this.id = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		return String.format("Id:%d, Name:%s, Age:%d, Designation:%s, Department:%s, Country:%s", this.getEmpId(), this.getName() , this.age, this.designation , this.department , this.country);
	}
}
