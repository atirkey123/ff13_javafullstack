package com.examples.empapp.dao;

import java.util.ArrayList;

import com.examples.empapp.model.Employee;

public interface EmployeeDao {
	public boolean insertHi(Employee emp);

	public boolean updateHi(Employee emp);

	public boolean deleteHi(int empId);
	
	public Employee viewEmpHi(int empId);
	
	public ArrayList<Employee> getAllEmpHi();
	
	public void statisticsHi() ;
}
