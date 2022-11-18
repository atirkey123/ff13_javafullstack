package com.examples.empapp.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.examples.empapp.model.Employee;
import com.examples.empapp.utils.ConnectionHelperHibernate;

public class HibernateDaoImpl implements EmployeeDao{
	static SessionFactory factory = ConnectionHelperHibernate.getSession();
	

	@Override
	public boolean insertHi(Employee emp) {
		Transaction tnx = null ;
		Integer id = -1 ;
		Session session = null ;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();

			// Insert data into table by supplying the persistent object
			 id = (Integer) session.save(emp);

			System.out.println("\nEmployee inserted successfully wiht ID --" + id);

			tnx.commit();
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
		return false;
		
	}

	@Override
	public boolean updateHi(Employee emp) {
		// TODO Auto-generated method stub
		Transaction tnx = null ;
		Session session = null ;

			try {
				session = factory.openSession();
				tnx = session.beginTransaction();
				session.update(emp);
			  
				tnx.commit();
				return true ;
			} catch (HibernateException he) {
				tnx.rollback();
				he.printStackTrace();
			}finally {
				session.close();
			}

		return false;
		
	}

	@Override
	public boolean deleteHi(int empId) {
		// TODO Auto-generated method stub
		Transaction tnx = null ;
		Session session = null ;

			try {
				session = factory.openSession();
				tnx = session.beginTransaction();
				Employee emp = viewEmpHi(empId);
				session.delete(emp);
			  
				tnx.commit();
				return true ;
			} catch (HibernateException he) {
				tnx.rollback();
				he.printStackTrace();
			}finally {
				session.close();
			}

		return false;
	}

	@Override
	public Employee viewEmpHi(int empId) {
		Transaction tnx = null ;
				Session session = null ;
		
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			
			
			 Employee emp=session.get(Employee.class, empId);
		
	      
			tnx.commit();
			return emp ;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
		return null ;
	    }
	

	@Override
	public ArrayList<Employee> getAllEmpHi() {
		// TODO Auto-generated method stub
		Transaction tnx = null ;
		Session session = null ;

			try {
				session = factory.openSession();
				tnx = session.beginTransaction();
				
				ArrayList<Employee> empall = (ArrayList<Employee>) session.createQuery("From Employee").list();
			
			  
				tnx.commit();
				return empall ;
			} catch (HibernateException he) {
				tnx.rollback();
				he.printStackTrace();
			}finally {
				session.close();
			}
			return null ;
		
	}

	private ArrayList<Employee> getEmployeeCountAgeGreaterThan(){
		ArrayList<Employee> list = new ArrayList<>() ;
		
		Transaction tnx = null ;
		Session session = null ;

			try {
				session = factory.openSession();
				tnx = session.beginTransaction();
				list = (ArrayList<Employee>) session.createNativeQuery("SELECT * FROM Employee Where age > 25", Employee.class).list();
				
			  
				tnx.commit();
				return list ;
			} catch (HibernateException he) {
				tnx.rollback();
				he.printStackTrace();
			}finally {
				session.close();
			}
			return null ;
		
		
		
	}
	private List<Integer> getEmployeeIdsAgeGreaterThan(){
		List<Integer> list = new ArrayList<>() ;
		
		Transaction tnx = null ;
		Session session = null ;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			ArrayList<Employee> empList = getEmployeeCountAgeGreaterThan() ;
			list = empList.stream().map( emp -> emp.getEmpId()).toList();
		  
			tnx.commit();
			return list ;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
		return null ;
	}
	
	public Map<Object,Object> getEmployeeCountByDepartment(){
		Map<Object,Object> map = new HashMap<Object,Object>();
		Transaction tnx = null ;
		Session session = null ;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			String hql = "SELECT department, COUNT(*) from Employee GROUP BY department";
			List<Object> list = session.createQuery(hql).list();
			for(int i=0; i<list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				map.put(row[0],row[1]);
			}
			return map ;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
	
		return null ;
	}
	public Map<Object,Object> getEmployeeCountByDepartmentOdered(){
		
		Map<Object,Object> map = new LinkedHashMap<Object,Object>();
		Transaction tnx = null ;
		Session session = null ;
		try {
			session = factory.openSession();
			tnx = session.beginTransaction();
			String hql = "SELECT department, COUNT(*) from Employee GROUP BY department ORDER BY COUNT(*)";
			List<?> list = session.createQuery(hql).list();
			for(int i=0; i<list.size(); i++) {
				Object[] row = (Object[]) list.get(i);
				map.put(row[0],row[1]);
			}
			return map ;
		} catch (HibernateException he) {
			tnx.rollback();
			he.printStackTrace();
		}finally {
			session.close();
		}
	
		return null ;
		
	}

	@Override
	public void statisticsHi() {
		// TODO Auto-generated method stub
		System.out.println("No of employees older than thirty years: "
		+ getEmployeeCountAgeGreaterThan().size());
System.out.println("List employee IDs older than thirty years: " + getEmployeeIdsAgeGreaterThan());
System.out.println("Employee count by Department: " + getEmployeeCountByDepartment());
System.out.println("Employee count by Department ordered: " + getEmployeeCountByDepartmentOdered());
		
	}


	

}
