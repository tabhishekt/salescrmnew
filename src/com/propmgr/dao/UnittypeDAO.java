package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unittype.
 * @see com.propmgr.dao.Unittype
 * @author Arvind Sharma
 */
public class UnittypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnittypeDAO.class);

	public Unittype findById(long id) {
		log.debug("getting Unittype instance with id: " + id);
		try {
			Unittype instance = (Unittype) getSession()
					.get("com.propmgr.dao.Unittype", id);
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

	public List findByExample(Unittype instance) {
		log.debug("finding Unittype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unittype")
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
		return new Unittype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
