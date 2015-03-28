package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Refundmaster.
 * @see com.propmgr.dao.Refundmaster
 * @author Arvind Sharma
 */
public class RefundmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(RefundmasterDAO.class);

	public void persist(Refundmaster transientInstance) {
		log.debug("persisting Refundmaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Refundmaster instance) {
		log.debug("attaching dirty Refundmaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Refundmaster instance) {
		log.debug("attaching clean Refundmaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Refundmaster persistentInstance) {
		log.debug("deleting Refundmaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Refundmaster merge(Refundmaster detachedInstance) {
		log.debug("merging Refundmaster instance");
		try {
			Refundmaster result = (Refundmaster) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Refundmaster findById(java.lang.Long id) {
		log.debug("getting Refundmaster instance with id: " + id);
		try {
			Refundmaster instance = (Refundmaster) getSession()
					.get("com.propmgr.dao.Refundmaster", id);
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

	public List findByExample(Refundmaster instance) {
		log.debug("finding Refundmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Refundmaster")
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
		return new Refundmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
