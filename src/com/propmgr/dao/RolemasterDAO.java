package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Rolemaster.
 * @see com.propmgr.dao.Rolemaster
 * @author Arvind Sharma
 */
public class RolemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(RolemasterDAO.class);

	public Rolemaster findById(long id) {
		log.debug("getting Rolemaster instance with id: " + id);
		try {
			Rolemaster instance = (Rolemaster) getSession().get("com.propmgr.dao.Rolemaster", id);
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

	public List findByExample(Rolemaster instance) {
		log.debug("finding Rolemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Rolemaster")
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
		return new Rolemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
