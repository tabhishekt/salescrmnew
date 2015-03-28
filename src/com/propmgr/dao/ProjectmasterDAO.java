package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Projectmaster.
 * @see com.propmgr.dao.Projectmaster
 * @author Arvind Sharma
 */
public class ProjectmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ProjectmasterDAO.class);

	public void persist(Projectmaster transientInstance) {
		log.debug("persisting Projectmaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Projectmaster instance) {
		log.debug("attaching dirty Projectmaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Projectmaster instance) {
		log.debug("attaching clean Projectmaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Projectmaster persistentInstance) {
		log.debug("deleting Projectmaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Projectmaster merge(Projectmaster detachedInstance) {
		log.debug("merging Projectmaster instance");
		try {
			Projectmaster result = (Projectmaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Projectmaster findById(long id) {
		log.debug("getting Projectmaster instance with id: " + id);
		try {
			Projectmaster instance = (Projectmaster) getSession().get("com.propmgr.dao.Projectmaster", id);
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

	public List findByExample(Projectmaster instance) {
		log.debug("finding Projectmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Projectmaster")
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
		return new Projectmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
