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
 * Home object for domain model class Enquirycomment.
 * @see com.propmgr.dao.Enquirycomment
 * @author Arvind Sharma
 */
public class EnquirycommentDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(EnquirycommentDAO.class);

	public Enquirycomment findById(long id) {
		log.debug("getting Enquirycomment instance with id: " + id);
		try {
			Enquirycomment instance = (Enquirycomment) getSession().get("com.propmgr.dao.Enquirycomment",
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

	public List findByExample(Enquirycomment instance) {
		log.debug("finding Enquirycomment instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Enquirycomment")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Enquirycomment> findByEnquiry(long enquiryId) {
		Session hbmSession = getSession();
		List<Enquirycomment> resultList = null;
		try {
			String queryString = "from Enquirycomment u where u.enquiry.enquiryid = " + enquiryId;
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
		return new Enquirycomment();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
