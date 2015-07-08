package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Userrole.
 * @see com.propmgr.dao.Userrole
 * @author Arvind Sharma
 */
public class UserroleDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UserroleDAO.class);

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
