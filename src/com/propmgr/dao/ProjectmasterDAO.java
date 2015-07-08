package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Projectmaster.
 * @see com.propmgr.dao.Projectmaster
 * @author Arvind Sharma
 */
public class ProjectmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ProjectmasterDAO.class);

	public Projectmaster findById(long id) {
		log.debug("getting Projectmaster instance with id: " + id);
		try {
			Projectmaster instance = (Projectmaster) getSession().get("com.propmgr.dao.Projectmaster", id);
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

	public List findByExample(Projectmaster instance) {
		log.debug("finding Projectmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Projectmaster")
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
		return new Projectmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
