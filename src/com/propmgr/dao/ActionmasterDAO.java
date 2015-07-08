package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Actionmaster.
 * @see com.propmgr.dao.Actionmaster
 * @author Arvind Sharma
 */
public class ActionmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ActionmasterDAO.class);

	public Actionmaster findById(java.lang.Long id) {
		log.debug("getting Actionmaster instance with id: " + id);
		try {
			Actionmaster instance = (Actionmaster) getSession()
					.get("com.propmgr.dao.Actionmaster", id);
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

	public List findByExample(Actionmaster instance) {
		log.debug("finding Actionmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Actionmaster")
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
		// TODO Auto-generated method stub
		return new Actionmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
