package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Statemaster.
 * @see com.propmgr.dao.Statemaster
 * @author Arvind Sharma
 */
public class StatemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(StatemasterDAO.class);

	public Statemaster findById(long id) {
		log.debug("getting Statemaster instance with id: " + id);
		try {
			Statemaster instance = (Statemaster) getSession().get("com.propmgr.dao.Statemaster", id);
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

	public List findByExample(Statemaster instance) {
		log.debug("finding Statemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Statemaster")
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
		return new Statemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
