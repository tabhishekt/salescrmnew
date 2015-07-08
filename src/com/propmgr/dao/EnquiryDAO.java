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
 * Home object for domain model class Enquiry.
 * @see com.propmgr.dao.Enquiry
 * @author Arvind Sharma
 */
public class EnquiryDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(EnquiryDAO.class);

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
