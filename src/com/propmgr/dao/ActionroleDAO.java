package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Actionrole.
 * @see com.propmgr.dao.Actionrole
 * @author Arvind Sharma
 */
public class ActionroleDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ActionroleDAO.class);

	public void persist(Actionrole transientInstance) {
		log.debug("persisting Actionrole instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Actionrole instance) {
		log.debug("attaching dirty Actionrole instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Actionrole instance) {
		log.debug("attaching clean Actionrole instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Actionrole persistentInstance) {
		log.debug("deleting Actionrole instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Actionrole merge(Actionrole detachedInstance) {
		log.debug("merging Actionrole instance");
		try {
			Actionrole result = (Actionrole) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Actionrole findById(java.lang.Long id) {
		log.debug("getting Actionrole instance with id: " + id);
		try {
			Actionrole instance = (Actionrole) getSession()
					.get("com.propmgr.dao.Actionrole", id);
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

	public List findByExample(Actionrole instance) {
		log.debug("finding Actionrole instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Actionrole")
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
		return new Actionrole();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
