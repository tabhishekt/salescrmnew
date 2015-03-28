package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Sourcemaster.
 * @see com.propmgr.dao.Sourcemaster
 * @author Arvind Sharma
 */
public class SourcemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(SourcemasterDAO.class);

	public void persist(Sourcemaster transientInstance) {
		log.debug("persisting Sourcemaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Sourcemaster instance) {
		log.debug("attaching dirty Sourcemaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Sourcemaster instance) {
		log.debug("attaching clean Sourcemaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Sourcemaster persistentInstance) {
		log.debug("deleting Sourcemaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Sourcemaster merge(Sourcemaster detachedInstance) {
		log.debug("merging Sourcemaster instance");
		try {
			Sourcemaster result = (Sourcemaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Sourcemaster findById(long id) {
		log.debug("getting Sourcemaster instance with id: " + id);
		try {
			Sourcemaster instance = (Sourcemaster) getSession().get("com.propmgr.dao.Sourcemaster", id);
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

	public List findByExample(Sourcemaster instance) {
		log.debug("finding Sourcemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Sourcemaster")
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
		return new Sourcemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
