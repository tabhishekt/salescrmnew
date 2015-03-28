package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Amenity.
 * @see com.propmgr.dao.Amenity
 * @author Arvind Sharma
 */
public class AmenityDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(AmenityDAO.class);

	public void persist(Amenity transientInstance) {
		log.debug("persisting Amenity instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Amenity instance) {
		log.debug("attaching dirty Amenity instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Amenity instance) {
		log.debug("attaching clean Amenity instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Amenity persistentInstance) {
		log.debug("deleting Amenity instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Amenity merge(Amenity detachedInstance) {
		log.debug("merging Amenity instance");
		try {
			Amenity result = (Amenity) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Amenity findById(long id) {
		log.debug("getting Amenity instance with id: " + id);
		try {
			Amenity instance = (Amenity) getSession()
					.get("com.propmgr.dao.Amenity", id);
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

	public List findByExample(Amenity instance) {
		log.debug("finding Amenity instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Amenity")
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
		return new Amenity();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
