package com.examples.empapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.examples.empapp.model.Employee;
import com.examples.empapp.utils.ConnectionHelperJdbc;

public class JdbcDaoImpl implements EmployeeDao {
	private static Connection con = ConnectionHelperJdbc.getJdbcConnection();
	@Override
	public boolean insertHi(Employee emp) {

		try {
			PreparedStatement pstmt = con.prepareStatement("INSERT INTO employees (name, age, designation, department, country) VALUES (?,?,?,?,?);  ");
				
				pstmt.setString(1, emp.getName());
			    pstmt.setInt(2, emp.getAge());
			    pstmt.setString(3, emp.getDesignation());
			    pstmt.setString(4, emp.getDepartment());
			    pstmt.setString(5, emp.getCountry());
			try {
				int i = pstmt.executeUpdate();
				System.out.println("Employee Inserted to Database");
				pstmt.close();
			}catch(Exception e){
				System.out.println("Error while inserting");
				e.printStackTrace();
			}
			
			return true ;
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateHi(Employee emp) {
		try {
			Statement stmt = con.createStatement();
			String query = "UPDATE employees SET name = '" + emp.getName() + "', age = '" + emp.getAge() + "', department = '" + emp.getDepartment() + "', designation = '" + emp.getDesignation() + "', country = '" + emp.getCountry() + "' WHERE empId = " + emp.getEmpId() + ";";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error Updating Employee");
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteHi(int empId) {
		String q = "DELETE FROM employees WHERE empId ="+empId + ";" ; 
		try {
			Statement stmt = con.createStatement() ;
			stmt.execute(q);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return false;
	}

	@Override
	public Employee viewEmpHi(int empId) {
		try {
			PreparedStatement pstmt = con.prepareStatement( "SELECT * FROM employees WHERE empId = ? ;");
			pstmt.setInt(1, empId);
			ResultSet rs = pstmt.executeQuery();
			Employee emp = null ;
			if(rs.next()) {
				int id = rs.getInt("empId");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String desgi = rs.getString("designation");
				String depart = rs.getString("department");
				String country = rs.getString("country");
				emp = new Employee(id , name , age,desgi,depart,country);
				
			}
			rs.close();
			pstmt.close();
			return emp ;
			
		} catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Employee> getAllEmpHi() {
		List<Employee> employees = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM employees") ;
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("empId");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String desgi = rs.getString("designation");
				String depart = rs.getString("department");
				String country = rs.getString("country");
				Employee emp = new Employee(id , name , age,desgi,depart,country);
				employees.add(emp);
			}
			rs.close();
			pstmt.close();
			return (ArrayList<Employee>) employees ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	private int getEmployeeCountAgeGreaterThan(){
		
		String q = "SELECT COUNT(*) FROM employees where age >= 25 ;" ;
		
		int count ;
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(q);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				 count = rs.getInt(1);
				return count ;
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return 0 ;
	}
	
	private ArrayList<Integer> getEmployeeIdsAgeGreaterThan(){
		ArrayList<Integer> array = new ArrayList<>();
		String q = "SELECT empId FROM employees where age >= 25 ;" ;
		
		try {
			PreparedStatement pstmt = con.prepareStatement(q);
			ResultSet rs = pstmt.executeQuery() ; 
			
			while(rs.next()) {
				array.add(rs.getInt("empId"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return array ;
	}
	
	private Map<String,Integer> getEmployeeCountByDepartment(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		String q = "SELECT department,COUNT(*) as count FROM employees GROUP BY department ;" ;
		
		try {
			PreparedStatement pstmt = con.prepareStatement(q);
			ResultSet rs = pstmt.executeQuery() ; 
			while(rs.next()) {
				String department = rs.getString("department");
				int count = rs.getInt("count");
				
				map.put(department, count);
			}
			rs.close();
		}catch(SQLException e ) {
			e.printStackTrace();
		}
		
		return map ;
		
		
		
	}
	private Map<String,Integer> getEmployeeCountByDepartmentOdered() {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		String q = "SELECT department,COUNT(*) as count FROM employees GROUP BY department ORDER BY department ;" ;
		try {
			PreparedStatement pstmt = con.prepareStatement(q);
			ResultSet rs = pstmt.executeQuery() ; 
			while(rs.next()) {
				String department = rs.getString("department");
				int count = rs.getInt("count");
				map.put(department, count);
				
			}
			rs.close();
		}catch(SQLException e ){
			e.printStackTrace();
		}
		
		return map ;
	}
	
	@Override
	public void statisticsHi() {
		System.out.println("No of employees older than twenty five years: "
				+ getEmployeeCountAgeGreaterThan());
		System.out.println("List employee IDs older than twenty five years: " + getEmployeeIdsAgeGreaterThan());
		System.out.println("Employee count by Department: " + getEmployeeCountByDepartment());
		System.out.println("Employee count by Department ordered: " + getEmployeeCountByDepartmentOdered());
		
	}

}
