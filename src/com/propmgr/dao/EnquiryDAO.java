package com.propmgr.dao;

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
 * Home object for domain model class Enquiry.
 * @see com.propmgr.dao.Enquiry
 * @author Arvind Sharma
 */
public class EnquiryDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(EnquiryDAO.class);

	public void persist(Enquiry transientInstance) {
		log.debug("persisting Enquiry instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Enquiry instance) {
		log.debug("attaching dirty Enquiry instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Enquiry instance) {
		log.debug("attaching clean Enquiry instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Enquiry persistentInstance) {
		log.debug("deleting Enquiry instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Enquiry merge(Enquiry detachedInstance) {
		log.debug("merging Enquiry instance");
		try {
			Enquiry result = (Enquiry) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Enquiry findById(long id) {
		log.debug("getting Enquiry instance with id: " + id);
		try {
			Enquiry instance = (Enquiry) getSession()
					.get("com.propmgr.dao.Enquiry", id);
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

	public List findByExample(Enquiry instance) {
		log.debug("finding Enquiry instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Enquiry")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Enquiry> findByCustomer(long customerId) {
		Session hbmSession = getSession();
		List<Enquiry> resultList = null;
		try {
			String queryString = "from Enquiry u where u.customermaster.customerid = " + customerId;
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
		return new Enquiry();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
