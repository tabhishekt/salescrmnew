package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Paymenttype.
 * @see com.propmgr.dao.Paymenttype
 * @author Arvind Sharma
 */
public class PaymenttypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymenttypeDAO.class);

	public void persist(Paymenttype transientInstance) {
		log.debug("persisting Paymenttype instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Paymenttype instance) {
		log.debug("attaching dirty Paymenttype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paymenttype instance) {
		log.debug("attaching clean Paymenttype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Paymenttype persistentInstance) {
		log.debug("deleting Paymenttype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paymenttype merge(Paymenttype detachedInstance) {
		log.debug("merging Paymenttype instance");
		try {
			Paymenttype result = (Paymenttype) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Paymenttype findById(long id) {
		log.debug("getting Paymenttype instance with id: " + id);
		try {
			Paymenttype instance = (Paymenttype) getSession().get("com.propmgr.dao.Paymenttype", id);
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

	public List findByExample(Paymenttype instance) {
		log.debug("finding Paymenttype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymenttype")
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
		return new Paymenttype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
