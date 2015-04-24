package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Bankaccounttype.
 * @see com.propmgr.dao.Bankaccounttype
 * @author Arvind Sharma
 */
public class BankaccounttypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(BankaccounttypeDAO.class);

	public void persist(Bankaccounttype transientInstance) {
		log.debug("persisting Bankaccounttype instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Bankaccounttype instance) {
		log.debug("attaching dirty Bankaccounttype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Bankaccounttype instance) {
		log.debug("attaching clean Bankaccounttype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Bankaccounttype persistentInstance) {
		log.debug("deleting Bankaccounttype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Bankaccounttype merge(Bankaccounttype detachedInstance) {
		log.debug("merging Bankaccounttype instance");
		try {
			Bankaccounttype result = (Bankaccounttype) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Bankaccounttype findById(java.lang.Long id) {
		log.debug("getting Bankaccounttype instance with id: " + id);
		try {
			Bankaccounttype instance = (Bankaccounttype) getSession().get("com.propmgr.dao.Bankaccounttype",
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

	public List findByExample(Bankaccounttype instance) {
		log.debug("finding Bankaccounttype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Bankaccounttype")
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
		return new Bankaccounttype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
