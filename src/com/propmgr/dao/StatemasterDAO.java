package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Statemaster.
 * @see com.propmgr.dao.Statemaster
 * @author Arvind Sharma
 */
public class StatemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(StatemasterDAO.class);

	public void persist(Statemaster transientInstance) {
		log.debug("persisting Statemaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Statemaster instance) {
		log.debug("attaching dirty Statemaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Statemaster instance) {
		log.debug("attaching clean Statemaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Statemaster persistentInstance) {
		log.debug("deleting Statemaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Statemaster merge(Statemaster detachedInstance) {
		log.debug("merging Statemaster instance");
		try {
			Statemaster result = (Statemaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Statemaster findById(long id) {
		log.debug("getting Statemaster instance with id: " + id);
		try {
			Statemaster instance = (Statemaster) getSession().get("com.propmgr.dao.Statemaster", id);
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

	public List findByExample(Statemaster instance) {
		log.debug("finding Statemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Statemaster")
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
		return new Statemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
