package com.pratik.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pratik.domain.PhoneNumber;
import com.pratik.domain.User;
import com.pratik.utility.HibernateUtil;

public class OTM_DAOImpl implements OTM_DAO {
 
	@Override
	public void SaveDataUsingParents() {
		Session ses=null;
		User user=null;
		PhoneNumber ph1=null,ph2=null;
		Map<String,PhoneNumber> childs=null;
		//get Session
		ses=HibernateUtil.getSession();
		
		//parent obj
		 user=new User();
		 //user.setUserId(1001);
		 user.setFirstName("rohit");
		//child objs
		 ph1=new PhoneNumber();
		ph1.setPhone(999888);
		ph1.setNumberType("office");
		ph1.setOperator("Idea");

		 ph2=new PhoneNumber();
		ph2.setPhone(888999);
		ph2.setNumberType("home");
		ph2.setOperator("Airtel");

		childs=new HashMap();//<PhoneNumber>();
		childs.put("Office",ph1); childs.put("Personal",ph2);
		//set childs to parent
		user.setPhones(childs);

		//save objs (parent to child)
		Transaction tx=null;
		try{
		 tx=ses.beginTransaction();
		   ses.persist(user);
		 tx.commit();
		 System.out.println("Objects are saved....");
		  }//try
		  catch(Exception e){
		    tx.rollback();
		    }
	}//method

	@Override
	public void loadDataUsingParent() {
		Session ses=null;
		Query query=null;
		List<User> list=null;
		List<Map<String,PhoneNumber>> childs=null;
		
		//get Session
		ses=HibernateUtil.getSession();
		//create Query obj
		query=ses.createQuery("from User");
		//execute Query
		list=query.list();
		//process the Results
		for(User user:list){
			System.out.println("parent---->"+user);
			//get all childs of each parent
			childs=(List<Map<String, PhoneNumber>>) user.getPhones();
			//System.out.println(childs.size());
			for(Map<String, PhoneNumber> ph:childs){
				System.out.println("child--->"+ph);
			}//for
		
			}//for
	}//method
	
	
	@Override
	public void deleteOneChildFromCollectionOfChildsInAParent() {
		Session ses=null;
		User user=null;
		Map<String,PhoneNumber> childs=null;
		PhoneNumber ph=null;
		Transaction tx=null;
		//get Session
		ses=HibernateUtil.getSession();
		//Load parent
		user=(User)ses.get(User.class,2);
		//get Childs from a Parent
		childs=user.getPhones();
		//Load child obj from DB that u want to delete
		//ph=(PhoneNumber)ses.get(PhoneNumber.class,(long)888999);
		//delete child from collection of childs
		try{
			tx=ses.beginTransaction();
			    childs.remove("personal");
			 tx.commit();
			 System.out.println("Removing child obj from collection of childs");
		}//try
		catch(Exception e){
			tx.rollback();
		}
	}//method
	
	
}//class
