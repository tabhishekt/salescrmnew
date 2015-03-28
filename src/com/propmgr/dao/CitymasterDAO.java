package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Citymaster.
 * @see com.propmgr.dao.Citymaster
 * @author Arvind Sharma
 */
public class CitymasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(CitymasterDAO.class);


	public void persist(Citymaster transientInstance) {
		log.debug("persisting Citymaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Citymaster instance) {
		log.debug("attaching dirty Citymaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Citymaster instance) {
		log.debug("attaching clean Citymaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Citymaster persistentInstance) {
		log.debug("deleting Citymaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Citymaster merge(Citymaster detachedInstance) {
		log.debug("merging Citymaster instance");
		try {
			Citymaster result = (Citymaster) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Citymaster findById(long id) {
		log.debug("getting Citymaster instance with id: " + id);
		try {
			Citymaster instance = (Citymaster) getSession().get("com.propmgr.dao.Citymaster", id);
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

	public List findByExample(Citymaster instance) {
		log.debug("finding Citymaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Citymaster")
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
		return new Citymaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
