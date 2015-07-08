package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitclassificationmaster.
 * @see com.propmgr.dao.Unitclassificationmaster
 * @author At
 */
public class UnitClassificationmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitClassificationmasterDAO.class);

	public Unitclassificationmaster findById(long id) {
		log.debug("getting Unitclassificationmaster instance with id: " + id);
		try {
			Unitclassificationmaster instance = (Unitclassificationmaster) getSession().get("com.propmgr.dao.Unitclassificationmaster", id);
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

	public List findByExample(Unitclassificationmaster instance) {
		log.debug("finding Unitclassificationmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitclassificationmaster")
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
		return new Unitclassificationmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
