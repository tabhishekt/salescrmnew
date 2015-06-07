package com.propmgr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
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

	public void persist(Customermaster transientInstance) {
		log.debug("persisting Customermaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Customermaster instance) {
		log.debug("attaching dirty Customermaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Customermaster instance) {
		log.debug("attaching clean Customermaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Customermaster persistentInstance) {
		log.debug("deleting Customermaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Customermaster merge(Customermaster detachedInstance) {
		log.debug("merging Customermaster instance");
		try {
			Customermaster result = (Customermaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

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
			String queryString = "from Customermaster u where lower(u.person.firstname) = lower('" + firstName + "')" +
					" and lower(u.person.middlename) = lower('" + middleName + "') and lower(u.person.lastname) = lower('" + lastName + "')";
			
			if (emailID != null && emailID.length() > 0) {
				queryString += " and u.person.contactinfo.emailid = '" + emailID + "'";
			}
			
			if (phoneNumber != null && phoneNumber.length() > 0) {
				queryString += " and u.person.contactinfo.phonenumber = '" + phoneNumber + "'";
			} else { 
				if (mobileNumber != null && mobileNumber.length() > 0) {
					queryString += " and u.person.contactinfo.mobilenumber = '" + mobileNumber + "'";
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
