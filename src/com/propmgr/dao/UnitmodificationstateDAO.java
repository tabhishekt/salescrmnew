package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitmodificationstate.
 * @see com.propmgr.dao.Unitmodificationstate
 * @author Arvind Sharma
 */
public class UnitmodificationstateDAO extends SuperDAO {

	private static final Log log = LogFactory
			.getLog(UnitmodificationstateDAO.class);

	
	public void persist(Unitmodificationstate transientInstance) {
		log.debug("persisting Unitmodificationstate instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Unitmodificationstate instance) {
		log.debug("attaching dirty Unitmodificationstate instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Unitmodificationstate instance) {
		log.debug("attaching clean Unitmodificationstate instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Unitmodificationstate persistentInstance) {
		log.debug("deleting Unitmodificationstate instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Unitmodificationstate merge(Unitmodificationstate detachedInstance) {
		log.debug("merging Unitmodificationstate instance");
		try {
			Unitmodificationstate result = (Unitmodificationstate) 
					getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Unitmodificationstate findById(java.lang.Long id) {
		log.debug("getting Unitmodificationstate instance with id: " + id);
		try {
			Unitmodificationstate instance = (Unitmodificationstate) 
					getSession().get("com.propmgr.dao.Unitmodificationstate", id);
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

	public List findByExample(Unitmodificationstate instance) {
		log.debug("finding Unitmodificationstate instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitmodificationstate")
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
		return new Unitmodificationstate();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
