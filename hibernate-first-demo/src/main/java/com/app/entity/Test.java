package com.app.entity;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Test {

	public static SessionFactory sessionFactory=null;
	Scanner sc=new Scanner(System.in);
	
	static {
		Configuration configuration=new Configuration().configure();
		StandardServiceRegistryBuilder registry= new StandardServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry=registry.build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public void insert() {
		System.out.println("---------------------------------------");
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		Boolean flag=Boolean.FALSE;
		System.out.println("How many employee want to add");
		int noOfEmp=sc.nextInt();
		for(int i=0; i<noOfEmp; i++) {
			Employee emp=new Employee();
			System.out.println("Enter name of employee");
            emp.setName(sc.next());
            System.out.println("Enter mobile number");
            emp.setMobile(sc.next());
			session.save(emp);
		}
		tx.commit();
		flag=tx.wasCommitted();
		if(flag) {
			System.out.println("Inseerted successfully");
		}else {
			System.out.println("Failed...");
		}
		session.close();
	}
	
	
	public void selectEmployee() {
		System.out.println("-------------------------------------");
		Session session=sessionFactory.openSession();
		Criteria criteria=session.createCriteria(Employee.class);
		List<Employee> list=criteria.list();
		list.forEach(System.out::println);
	}
	
	
	public void updateEmployee() {
		System.out.println("-----------------------------------");
		selectEmployee();
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		System.out.println();
		System.out.println("Select employee id for update record");
		int id=sc.nextInt();
	    Employee emp= (Employee) session.get(Employee.class, id);
	    System.out.println("Enter name");
	    emp.setName(sc.next());
	    System.out.println("Enter mobile");
	    emp.setMobile(sc.next());
	    session.update(emp);
	    tx.commit();
	    tx.wasCommitted();
	    System.out.println("Successfully updated");
	}
	
	
	public void deleteEmployee() {
		System.out.println("--------------------------------------");
		selectEmployee();
		Session session=sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		System.out.println();
		System.out.println("Select employee id for delete records");
		int id=sc.nextInt();
		Employee emp=(Employee) session.get(Employee.class, id);
		session.delete(emp);
		tx.commit();
		tx.wasCommitted();
		System.out.println("Successfully deleted");
		selectEmployee();
	}
	
	
	public static void main(String[] args) {
            Test t=new Test();
            t.insert();
            t.selectEmployee();
            t.updateEmployee();
            t.deleteEmployee();

	}

}
