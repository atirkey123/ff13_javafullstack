package com.examples.empapp.model;

/**
 * Models employee object
 */
public class Employee {

	private int id;
	private String name;
	private int age;
	private String department;
	private String designation;
	private String country;
	
	public Employee()
	{
		
	}
	
	public Employee(int id, String name, int age, String designation, String department, String country)
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.designation = designation;
		this.department = department;		
		this.country = country;
	}

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
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

	public void setAge(int age) {
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
		return String.format("Id:%d, Name:%s", this.getid(), this.getName());
	}
}
