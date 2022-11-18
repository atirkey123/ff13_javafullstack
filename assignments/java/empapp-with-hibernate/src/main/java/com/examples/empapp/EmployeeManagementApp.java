package com.examples.empapp;

import com.examples.empapp.dao.EmployeeDao;
import com.examples.empapp.dao.HibernateDaoImpl;
import com.examples.empapp.exception.EmployeeException;
import com.examples.empapp.model.Employee;
import com.examples.empapp.service.EmployeeService;
import com.examples.empapp.service.EmployeeServiceColImpl;
import com.examples.empapp.service.EmployeeServiceHiberImpl;
import com.examples.empapp.service.EmployeeServiceJDBCImpl;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.hibernate.cfg.Configuration;

public class EmployeeManagementApp {

	private static Scanner in;
	private static EmployeeService empService;
	private static EmployeeService hibService ;
	private static EmployeeService jdbcService ;
	public static void main(String[] args) {
		hibService = new EmployeeServiceHiberImpl() ;
		in = new Scanner(System.in);
		empService = new EmployeeServiceColImpl();
		jdbcService = new EmployeeServiceJDBCImpl();
		ExecutorService executor = Executors.newCachedThreadPool();

		System.out.print("Welcome to Employee Management App!");

		while (true) {

			System.out.println("\n");
			System.out.println("1. Add Employee");
			System.out.println("2. View Employee");
			System.out.println("3. Update Employee");
			System.out.println("4. Delete Employee");
			System.out.println("5. View All Employees");
			System.out.println("6. Print Statistics");
			System.out.println("7. Import");
			System.out.println("8. Export");
			System.out.println("9. Exit");

			System.out.print("Enter the option: ");
			int option = 0;
			// Get option from user
			try {
				option = Integer.parseInt(in.next());
			} catch (NumberFormatException e) {
				System.out.println("Invalid option. Please enter valid option.");
				continue;
			}
			int empId;
			try {
				switch (option) {
				case 1:
				//addEmplopyee() ; // Using Collection
				 addEmployeeHi(); // Using Hibernate 
				//addEmployeeJdbc(); // Using Jdbc
				//System.out.println("Employee has been added successfully!");
					break;
				case 2:
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					Employee emp1 = null;
					try {
						 emp1 = hibService.get(empId); // Using Hibernate 
						// emp1 = empService.get(empId) ; // Using Collections
//						emp1 = jdbcService.get(empId); // Using Jdbc
					} catch (EmployeeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					printHeader();
					printDetail(emp1);
					break;
				case 3:
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					Employee empForUpdate = null;
					try {
						 empForUpdate = hibService.get(empId); // Using Hibernate 
						// empForUpdate = empService.get(empId); // Using Collection
//						empForUpdate = jdbcService.get(empId);
					} catch (EmployeeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					captureEmpDetail(empForUpdate);
					//empService.update(empForUpdate); // Using Collection 
					hibService.update(empForUpdate); // Using Hibernate 
//					jdbcService.update(empForUpdate);
					System.out.println("Employee has been updated successfully!");
					break;
				case 4:
					System.out.print("Please enter employee id: ");
					empId = in.nextInt();
					 hibService.delete(empId); // Using Hibernate
					// empService.delete(empId); // Using Collection
//					jdbcService.delete(empId); //Using Jdbc
					
					System.out.println("Employee has been deleted successfully!");
					break;
				case 5:
					List<Employee> employees = hibService.getAll(); // Using Hibernate
					// List<Employee> employees = empService.getAll(); // Using Collections
//					List<Employee> employees = jdbcService.getAll();
					printHeader();
					for (Employee employee : employees) {
						printDetail(employee);
					}
					break;
				case 6:
//					printStatistics();  //Using Collection
					 hibService.statistics(); // Using Hibernate Service 
//					jdbcService.statistics(); // Using Jdbc
					break;
				case 7:
					Callable<Boolean> importThread = new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							System.out.println(Thread.currentThread() + " Waiting for 5s to start import process.");
//							Thread.sleep(5000);
							//empService.bulkImport(); // Using Collection	
								hibService.bulkImport();	// Using Hibernate
//							jdbcService.bulkImport(); // Using Jdbc
							return true;
						}
					};
					
					Future<Boolean> importFuture = executor.submit(importThread);
					System.out.println(Thread.currentThread().getName() + " Import process triggered");				
					
					break;
				case 8:
					
					Callable<Boolean> exportThread = new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							System.out.println(Thread.currentThread() + " Waiting for 5s to start export process.");
							Thread.sleep(5000);
							//empService.bulkExport();	// Using Collections
							 hibService.bulkExport(); 	// Using Hibernate
//							jdbcService.bulkExport(); // Using JDBC
							return true;
						}
					};
					
					Future<Boolean> exportFuture = executor.submit(exportThread);
					System.out.println(Thread.currentThread().getName() + " Export process triggered");						
					
					
					break;					
				case 9:
					System.out.println("Thank you!!!");
					executor.shutdown();
					in.close();
					System.exit(0);
					break;

				default:
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter valid input.");
			}
		}

	}

	private static void printStatistics() {

		System.out.println("No of employees older than thirty years: "
				+ empService.getEmployeeCountAgeGreaterThan(30));
		System.out.println("List employee IDs older than thirty years: " + empService.getEmployeeIdsAgeGreaterThan(30));
		System.out.println("Employee count by Department: " + empService.getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + empService.getEmployeeCountByDepartmentOdered());
	}

	private static void printHeader() {
		System.out.format("\n%5s %15s %5s %15s %15s %15s", "EmpID", "Name", "Age", "Designation", "Department",
				"Country");
	}

	private static void printDetail(Employee emp) {
		if (emp == null) {
			return;
		}

		System.out.format("\n%5d %15s %5d %15s %15s %15s", emp.getEmpId(), emp.getName(), emp.getAge(),
				emp.getDesignation(), emp.getDepartment(), emp.getCountry());
	}

	private static void addEmployee() throws NumberFormatException {
		Employee employee = new Employee();

		captureEmpDetail(employee);

		empService.create(employee);
	}
	private static void addEmployeeHi(){
		Employee employee = new Employee() ; 
		captureEmpDetail(employee);
		
		hibService.create(employee);
	}
	private static void addEmployeeJdbc(){
		Employee employee = new Employee() ; 
		captureEmpDetail(employee);
		
		jdbcService.create(employee);
	}

	private static void captureEmpDetail(Employee employee) throws NumberFormatException {
		System.out.print("Enter employee Name: ");
		employee.setName(in.next());
		
		try {
			boolean val = true;
			do {
				System.out.print("Enter employee Age: ");
				String errorMsg = "Invalid Age. Age should be between 18 to 60.";
				employee.setAge(Integer.parseInt(in.next()));
				val = empService.validate(employee, errorMsg, e -> e.getAge() >= 18 && e.getAge() <= 60, m -> {
					System.out.println(m);
					return false;
				});
			} while (!val);
		} catch (NumberFormatException e) {
			throw e;
		}

		System.out.print("Enter employee Designation: ");
		employee.setDesignation(in.next());

		System.out.print("Enter employee Department: ");
		employee.setDepartment(in.next());

		System.out.print("Enter employee Country: ");
		employee.setCountry(in.next());
	}

}
