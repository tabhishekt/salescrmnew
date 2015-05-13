package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Parkingtype.
 * @see com.propmgr.dao.Parkingtype
 * @author Arvind Sharma
 */
public class ParkingtypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ParkingtypeDAO.class);

	public void persist(Parkingtype transientInstance) {
		log.debug("persisting Parkingtype instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Parkingtype instance) {
		log.debug("attaching dirty Parkingtype instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Parkingtype instance) {
		log.debug("attaching clean Parkingtype instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Parkingtype persistentInstance) {
		log.debug("deleting Parkingtype instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Parkingtype merge(Parkingtype detachedInstance) {
		log.debug("merging Parkingtype instance");
		try {
			Parkingtype result = (Parkingtype) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Parkingtype findById(java.lang.Long id) {
		log.debug("getting Parkingtype instance with id: " + id);
		try {
			Parkingtype instance = (Parkingtype) getSession().get("com.propmgr.dao.Parkingtype", id);
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

	public List findByExample(Parkingtype instance) {
		log.debug("finding Parkingtype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Parkingtype")
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
		return new Parkingtype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
