package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Actionmaster.
 * @see com.propmgr.dao.Actionmaster
 * @author Arvind Sharma
 */
public class ActionmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ActionmasterDAO.class);

	public void persist(Actionmaster transientInstance) {
		log.debug("persisting Actionmaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Actionmaster instance) {
		log.debug("attaching dirty Actionmaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Actionmaster instance) {
		log.debug("attaching clean Actionmaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Actionmaster persistentInstance) {
		log.debug("deleting Actionmaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Actionmaster merge(Actionmaster detachedInstance) {
		log.debug("merging Actionmaster instance");
		try {
			Actionmaster result = (Actionmaster) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Actionmaster findById(java.lang.Long id) {
		log.debug("getting Actionmaster instance with id: " + id);
		try {
			Actionmaster instance = (Actionmaster) getSession()
					.get("com.propmgr.dao.Actionmaster", id);
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

	public List findByExample(Actionmaster instance) {
		log.debug("finding Actionmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Actionmaster")
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
		// TODO Auto-generated method stub
		return new Actionmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
