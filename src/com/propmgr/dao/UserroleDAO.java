package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Userrole.
 * @see com.propmgr.dao.Userrole
 * @author Arvind Sharma
 */
public class UserroleDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UserroleDAO.class);

	public void persist(Userrole transientInstance) {
		log.debug("persisting Userrole instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Userrole instance) {
		log.debug("attaching dirty Userrole instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Userrole instance) {
		log.debug("attaching clean Userrole instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Userrole persistentInstance) {
		log.debug("deleting Userrole instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Userrole merge(Userrole detachedInstance) {
		log.debug("merging Userrole instance");
		try {
			Userrole result = (Userrole) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Userrole findById(long id) {
		log.debug("getting Userrole instance with id: " + id);
		try {
			Userrole instance = (Userrole) getSession()
					.get("com.propmgr.dao.Userrole", id);
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

	public List findByExample(Userrole instance) {
		log.debug("finding Userrole instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Userrole")
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
		return new Userrole();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
