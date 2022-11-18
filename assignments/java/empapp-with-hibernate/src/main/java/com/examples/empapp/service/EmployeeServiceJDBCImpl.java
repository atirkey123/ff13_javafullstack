package com.examples.empapp.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.function.Predicate;

import com.examples.empapp.dao.EmployeeDao;
import com.examples.empapp.dao.JdbcDaoImpl;
import com.examples.empapp.exception.EmployeeException;
import com.examples.empapp.model.Employee;

public class EmployeeServiceJDBCImpl implements EmployeeService {
	private static EmployeeDao jdbcDao = new JdbcDaoImpl() ;
	@Override
	public boolean create(Employee emp) {
		// TODO Auto-generated method stub
		
		return jdbcDao.insertHi(emp);
	}

	@Override
	public boolean update(Employee emp) {
		// TODO Auto-generated method stub
		return jdbcDao.updateHi(emp);
	}

	@Override
	public boolean delete(int empId) {
		// TODO Auto-generated method stub
		return jdbcDao.deleteHi(empId);
	}

	@Override
	public Employee get(int empId) throws EmployeeException {
		// TODO Auto-generated method stub
		return jdbcDao.viewEmpHi(empId);
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return jdbcDao.getAllEmpHi();
	}

	@Override
	public void bulkImport() {
		System.out.format("%n%s - Import started %n", Thread.currentThread().getName());
		int counter = 0;
		// windows path - .\\input\\employee-input.txt
		
		try (Scanner in = new Scanner(new FileReader(".\\input\\employee-input.txt"))) {
			System.out.println("Implorting file...");
			while (in.hasNextLine()) {
				String emp = in.nextLine();
				System.out.println("Importing employee - " + emp);
				Employee employee = new Employee();
				StringTokenizer tokenizer = new StringTokenizer(emp, ",");

				// Emp ID
//				employee.setEmpId(Integer.parseInt(tokenizer.nextToken()));
				// Name
				employee.setName(tokenizer.nextToken());
				// Age
				employee.setAge(Integer.parseInt(tokenizer.nextToken()));
				// Designation
				employee.setDesignation(tokenizer.nextToken());
				// Department
				employee.setDepartment(tokenizer.nextToken());
				// Country
				employee.setCountry(tokenizer.nextToken());

//				employees.put(employee.getEmpId(), employee);
				//
				
				this.create(employee);
			
				counter++;
			}
			System.out.format("%s - %d Employees are imported successfully.", Thread.currentThread().getName(),
					counter);
		} catch (Exception e) {
			System.out.println("Error occured while importing employee data. " + e.getMessage());
		}
	}

	@Override
	public void bulkExport() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		System.out.format("%n%s - Export started %n", Thread.currentThread().getName());
		// windows path - .\\output\\employee-output.txt

		try (FileWriter out = new FileWriter(".\\output\\employee-output-jdbc.txt")) {
				jdbcDao.getAllEmpHi().stream().map(emp -> emp.getEmpId() + "," + emp.getName() + "," + emp.getAge() + ","
							+ emp.getDesignation() + "," + emp.getDepartment() + "," + emp.getCountry() + "\n")
					.forEach(rec -> {
						try {
							out.write(rec);
						} catch (IOException e) {
							System.out
									.println("Error occured while writing employee data into file. " + e.getMessage());
							e.printStackTrace();
						}
					});
			System.out.format("%d Employees are exported successfully.", jdbcDao.getAllEmpHi().size());
		} catch (IOException e) {
			System.out.println("Error occured while exporting employee data. " + e.getMessage());
		}
	}

	@Override
	public boolean validate(Employee emp, String msg, Predicate<Employee> condition,
			Function<String, Boolean> operation) {
		// TODO Auto-generated method stub
		EmployeeService serviceEmp = new EmployeeServiceColImpl();
		return serviceEmp.validate(emp, msg, condition, operation);
	}

	@Override
	public long getEmployeeCountAgeGreaterThan(int age) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getEmployeeIdsAgeGreaterThan(int age) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Long> getEmployeeCountByDepartment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Long> getEmployeeCountByDepartmentOdered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void statistics() {
		// TODO Auto-generated method stub
		jdbcDao.statisticsHi();
	}

}
