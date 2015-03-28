package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitamenity.
 * @see com.propmgr.dao.Unitamenity
 * @author Arvind Sharma
 */
public class UnitamenityDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitamenityDAO.class);

	public void persist(Unitamenity transientInstance) {
		log.debug("persisting Unitamenity instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Unitamenity instance) {
		log.debug("attaching dirty Unitamenity instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Unitamenity instance) {
		log.debug("attaching clean Unitamenity instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Unitamenity persistentInstance) {
		log.debug("deleting Unitamenity instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Unitamenity merge(Unitamenity detachedInstance) {
		log.debug("merging Unitamenity instance");
		try {
			Unitamenity result = (Unitamenity) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Unitamenity findById(long id) {
		log.debug("getting Unitamenity instance with id: " + id);
		try {
			Unitamenity instance = (Unitamenity) getSession().get("com.propmgr.dao.Unitamenity", id);
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

	public List findByExample(Unitamenity instance) {
		log.debug("finding Unitamenity instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitamenity")
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
		return new Unitamenity();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
