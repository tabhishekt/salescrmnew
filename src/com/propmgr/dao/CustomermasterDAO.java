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
 * Home object for domain model class Customermaster.
 * @see com.propmgr.dao.Customermaster
 * @author Arvind Sharma
 */
public class CustomermasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(CustomermasterDAO.class);

	public Customermaster findById(long id) {
		log.debug("getting Customermaster instance with id: " + id);
		try {
			Customermaster instance = (Customermaster) getSession().get("com.propmgr.dao.Customermaster",
							id);
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

	public List findByExample(Customermaster instance) {
		log.debug("finding Customermaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Customermaster")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public boolean isDuplicateCustomer(String firstName, String middleName, String lastName, String emailID, String phoneNumber, String mobileNumber) {
		Session hbmSession = getSession();
		List<Customermaster> resultList = null;
		
		try {
			String queryString = "from Customermaster u where lower(u.personByPersondetail.firstname) = lower('" + firstName + "')" +
					" and lower(u.personByPersondetail.middlename) = lower('" + middleName + "') and lower(u.personByPersondetail.lastname) = lower('" + lastName + "')";
			
			if (emailID != null && emailID.length() > 0) {
				queryString += " and u.personByPersondetail.contactinfo.emailid = '" + emailID + "'";
			}
			
			if (phoneNumber != null && phoneNumber.length() > 0) {
				queryString += " and u.personByPersondetail.contactinfo.phonenumber = '" + phoneNumber + "'";
			} else { 
				if (mobileNumber != null && mobileNumber.length() > 0) {
					queryString += " and u.personByPersondetail.contactinfo.mobilenumber = '" + mobileNumber + "'";
				}
			}
			
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return false;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Customermaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
