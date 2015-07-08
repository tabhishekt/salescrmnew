package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Refundmaster.
 * @see com.propmgr.dao.Refundmaster
 * @author Arvind Sharma
 */
public class RefundmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(RefundmasterDAO.class);

	public Refundmaster findById(java.lang.Long id) {
		log.debug("getting Refundmaster instance with id: " + id);
		try {
			Refundmaster instance = (Refundmaster) getSession()
					.get("com.propmgr.dao.Refundmaster", id);
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

	public List findByExample(Refundmaster instance) {
		log.debug("finding Refundmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Refundmaster")
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
		return new Refundmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
