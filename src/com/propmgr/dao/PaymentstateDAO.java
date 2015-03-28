package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Paymentstate.
 * @see com.propmgr.dao.Paymentstate
 * @author Arvind Sharma
 */
public class PaymentstateDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentstateDAO.class);

	public void persist(Paymentstate transientInstance) {
		log.debug("persisting Paymentstate instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Paymentstate instance) {
		log.debug("attaching dirty Paymentstate instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paymentstate instance) {
		log.debug("attaching clean Paymentstate instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Paymentstate persistentInstance) {
		log.debug("deleting Paymentstate instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paymentstate merge(Paymentstate detachedInstance) {
		log.debug("merging Paymentstate instance");
		try {
			Paymentstate result = (Paymentstate) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Paymentstate findById(java.lang.Long id) {
		log.debug("getting Paymentstate instance with id: " + id);
		try {
			Paymentstate instance = (Paymentstate) getSession()
					.get("com.propmgr.dao.Paymentstate", id);
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

	public List findByExample(Paymentstate instance) {
		log.debug("finding Paymentstate instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymentstate")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
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
