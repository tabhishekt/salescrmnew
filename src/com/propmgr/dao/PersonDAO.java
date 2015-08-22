package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Person.
 * @see com.propmgr.dao.Person
 * @author Arvind Sharma
 */
public class PersonDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PersonDAO.class);

	public Person findById(long id) {
		log.debug("getting Person instance with id: " + id);
		try {
			Person instance = (Person) getSession().get(
					"com.propmgr.dao.Person", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Person instance) {
		log.debug("finding Person instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Person")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Person> findByNameAndPhone(String firstName, String middleName, String lastName, String phoneNumber, String mobileNumber) {
		Session hbmSession = getSession();
		List<Person> resultList = null;
		
		try {
			String queryString = "from Person u where lower(u.firstname) = lower('" + firstName + "')" +
					" and lower(u.middlename) = lower('" + middleName + "') and lower(u.lastname) = lower('" + lastName + "')";
			
			if (phoneNumber != null && phoneNumber.length() > 0) {
				queryString += " and u.contactinfo.phonenumber = '" + phoneNumber + "'";
			} else { 
				if (mobileNumber != null && mobileNumber.length() > 0) {
					queryString += " and u.contactinfo.mobilenumber = '" + mobileNumber + "'";
				} else {
					return null;
				}
			}
			
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			return resultList;
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	@Override
	protected Object getPojoObj() {
		return new Person();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
