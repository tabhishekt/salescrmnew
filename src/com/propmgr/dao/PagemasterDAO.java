package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Pagemaster.
 * @see com.propmgr.dao.Pagemaster
 * @author Arvind Sharma
 */
public class PagemasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PagemasterDAO.class);

	public Pagemaster findById(java.lang.Long id) {
		log.debug("getting Pagemaster instance with id: " + id);
		try {
			Pagemaster instance = (Pagemaster) getSession()
					.get("com.propmgr.dao.Pagemaster", id);
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

	public List findByExample(Pagemaster instance) {
		log.debug("finding Pagemaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Pagemaster")
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
		return new Pagemaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
