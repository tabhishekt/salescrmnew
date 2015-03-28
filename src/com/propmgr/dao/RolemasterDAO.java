package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Rolemaster.
 * @see com.propmgr.dao.Rolemaster
 * @author Arvind Sharma
 */
public class RolemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(RolemasterDAO.class);
	
	public void persist(Rolemaster transientInstance) {
		log.debug("persisting Rolemaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Rolemaster instance) {
		log.debug("attaching dirty Rolemaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Rolemaster instance) {
		log.debug("attaching clean Rolemaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Rolemaster persistentInstance) {
		log.debug("deleting Rolemaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Rolemaster merge(Rolemaster detachedInstance) {
		log.debug("merging Rolemaster instance");
		try {
			Rolemaster result = (Rolemaster) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Rolemaster findById(long id) {
		log.debug("getting Rolemaster instance with id: " + id);
		try {
			Rolemaster instance = (Rolemaster) getSession().get("com.propmgr.dao.Rolemaster", id);
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

	public List findByExample(Rolemaster instance) {
		log.debug("finding Rolemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Rolemaster")
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
		return new Rolemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
