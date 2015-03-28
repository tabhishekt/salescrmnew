package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unittype.
 * @see com.propmgr.dao.Unittype
 * @author Arvind Sharma
 */
public class UnittypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnittypeDAO.class);

	public void persist(Unittype transientInstance) {
		log.debug("persisting Unittype instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Unittype instance) {
		log.debug("attaching dirty Unittype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Unittype instance) {
		log.debug("attaching clean Unittype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Unittype persistentInstance) {
		log.debug("deleting Unittype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Unittype merge(Unittype detachedInstance) {
		log.debug("merging Unittype instance");
		try {
			Unittype result = (Unittype) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Unittype findById(long id) {
		log.debug("getting Unittype instance with id: " + id);
		try {
			Unittype instance = (Unittype) getSession()
					.get("com.propmgr.dao.Unittype", id);
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

	public List findByExample(Unittype instance) {
		log.debug("finding Unittype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unittype")
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
		return new Unittype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
