package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Citymaster.
 * @see com.propmgr.dao.Citymaster
 * @author Arvind Sharma
 */
public class CitymasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(CitymasterDAO.class);

	public Citymaster findById(long id) {
		log.debug("getting Citymaster instance with id: " + id);
		try {
			Citymaster instance = (Citymaster) getSession().get("com.propmgr.dao.Citymaster", id);
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

	public List findByExample(Citymaster instance) {
		log.debug("finding Citymaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Citymaster")
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
		return new Citymaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
