package com.lyl.test;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.springframework.stereotype.Service;



@Service
public class ApplicationContxtTest{

	
	
	@Test
	public void test01(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");  
		  
		SessionFactory sessionFactory = (SessionFactory) context .getBean("sessionFactory");  
			  
	    Session session = sessionFactory.openSession(); 
	    
	    System.out.println(session);

	}
	
	

}
