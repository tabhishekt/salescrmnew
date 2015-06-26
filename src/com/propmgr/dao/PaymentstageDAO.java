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
 * Home object for domain model class Paymentstate.
 * @see com.propmgr.dao.Paymentstate
 * @author Arvind Sharma
 */
public class PaymentstageDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentstageDAO.class);

	public void persist(Paymentstage transientInstance) {
		log.debug("persisting Paymentstate instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Paymentstage instance) {
		log.debug("attaching dirty Paymentstage instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paymentstage instance) {
		log.debug("attaching clean Paymentstage instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Paymentstage persistentInstance) {
		log.debug("deleting Paymentstage instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paymentstage merge(Paymentstage detachedInstance) {
		log.debug("merging Paymentstage instance");
		try {
			Paymentstage result = (Paymentstage) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Paymentstage findById(java.lang.Long id) {
		log.debug("getting Paymentstage instance with id: " + id);
		try {
			Paymentstage instance = (Paymentstage) getSession()
					.get("com.propmgr.dao.Paymentstage", id);
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

	public List findByExample(Paymentstage instance) {
		log.debug("finding Paymentstage instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymentstage")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Paymentstage> findByPayment(long paymentId) {
		Session hbmSession = getSession();
		List<Paymentstage> resultList = null;
		try {
			String queryString = "from Paymentstage u where u.paymentmaster.paymentid = " + paymentId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Paymentstage>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Paymentstate();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
