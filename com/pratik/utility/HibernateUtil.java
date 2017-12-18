package com.pratik.utility;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory factory=null;
	private static ThreadLocal<Session>threadLocal=
		                         new ThreadLocal<Session>();	
	
	static{
		factory=new  Configuration().configure("/com/pratik/cfgs/hibernate.cfg.xml").buildSessionFactory();
	}
	
	public static Session getSession(){
		Session session=null;
		if(threadLocal.get()==null){
		  session=factory.openSession();
		  threadLocal.set(session);
		}
		else{
			session=threadLocal.get();
		}
		return session;
		
	}//method
	
	public static void closeSession(){
		if(threadLocal.get()!=null){
			threadLocal.get().close();
			threadLocal.remove();
		}
	}//method
	
	public static void closeSessionFactory(){
		factory.close();
	}
	

}
