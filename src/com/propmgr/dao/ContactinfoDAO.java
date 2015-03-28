package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Contactinfo.
 * @see com.propmgr.dao.Contactinfo
 * @author Arvind Sharma
 */
public class ContactinfoDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ContactinfoDAO.class);

	public void persist(Contactinfo transientInstance) {
		log.debug("persisting Contactinfo instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Contactinfo instance) {
		log.debug("attaching dirty Contactinfo instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Contactinfo instance) {
		log.debug("attaching clean Contactinfo instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Contactinfo persistentInstance) {
		log.debug("deleting Contactinfo instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Contactinfo merge(Contactinfo detachedInstance) {
		log.debug("merging Contactinfo instance");
		try {
			Contactinfo result = (Contactinfo) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Contactinfo findById(long id) {
		log.debug("getting Contactinfo instance with id: " + id);
		try {
			Contactinfo instance = (Contactinfo) getSession().get("com.propmgr.dao.Contactinfo", id);
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

	public List findByExample(Contactinfo instance) {
		log.debug("finding Contactinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Contactinfo")
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
		return new Contactinfo();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
