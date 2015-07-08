package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Paymenttype.
 * @see com.propmgr.dao.Paymenttype
 * @author Arvind Sharma
 */
public class PaymenttypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymenttypeDAO.class);

	public Paymenttype findById(long id) {
		log.debug("getting Paymenttype instance with id: " + id);
		try {
			Paymenttype instance = (Paymenttype) getSession().get("com.propmgr.dao.Paymenttype", id);
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

	public List findByExample(Paymenttype instance) {
		log.debug("finding Paymenttype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymenttype")
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
		return new Paymenttype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
