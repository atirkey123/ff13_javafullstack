package com.examples.empapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.examples.empapp.model.Employee;

public interface EmployeeDao {
	
	public boolean insert(Employee emp);

	public boolean update(Employee emp);

	public boolean delete(int id);
	
	public Employee viewEmp(int id);
	
	public ArrayList<Employee> getAllEmp();
	
	public void statistics() ;

	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation);
	
	public void export() ;
	public void exit();
}