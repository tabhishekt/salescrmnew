package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitmodificationstate.
 * @see com.propmgr.dao.Unitmodificationstate
 * @author Arvind Sharma
 */
public class UnitmodificationstateDAO extends SuperDAO {

	private static final Log log = LogFactory
			.getLog(UnitmodificationstateDAO.class);

	public Unitmodificationstate findById(java.lang.Long id) {
		log.debug("getting Unitmodificationstate instance with id: " + id);
		try {
			Unitmodificationstate instance = (Unitmodificationstate) 
					getSession().get("com.propmgr.dao.Unitmodificationstate", id);
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

	public List findByExample(Unitmodificationstate instance) {
		log.debug("finding Unitmodificationstate instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitmodificationstate")
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
		return new Unitmodificationstate();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
