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
 * Home object for domain model class Enquirycomment.
 * @see com.propmgr.dao.Enquirycomment
 * @author Arvind Sharma
 */
public class EnquirycommentDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(EnquirycommentDAO.class);

	public void persist(Enquirycomment transientInstance) {
		log.debug("persisting Enquirycomment instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Enquirycomment instance) {
		log.debug("attaching dirty Enquirycomment instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Enquirycomment instance) {
		log.debug("attaching clean Enquirycomment instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Enquirycomment persistentInstance) {
		log.debug("deleting Enquirycomment instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Enquirycomment merge(Enquirycomment detachedInstance) {
		log.debug("merging Enquirycomment instance");
		try {
			Enquirycomment result = (Enquirycomment) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

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
