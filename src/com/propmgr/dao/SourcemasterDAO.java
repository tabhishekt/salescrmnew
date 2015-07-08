package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Sourcemaster.
 * @see com.propmgr.dao.Sourcemaster
 * @author Arvind Sharma
 */
public class SourcemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(SourcemasterDAO.class);

	public Sourcemaster findById(long id) {
		log.debug("getting Sourcemaster instance with id: " + id);
		try {
			Sourcemaster instance = (Sourcemaster) getSession().get("com.propmgr.dao.Sourcemaster", id);
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

	public List findByExample(Sourcemaster instance) {
		log.debug("finding Sourcemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Sourcemaster")
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
		return new Sourcemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
