package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Bankaccounttype.
 * @see com.propmgr.dao.Bankaccounttype
 * @author Arvind Sharma
 */
public class BankaccounttypeDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(BankaccounttypeDAO.class);

	public Bankaccounttype findById(java.lang.Long id) {
		log.debug("getting Bankaccounttype instance with id: " + id);
		try {
			Bankaccounttype instance = (Bankaccounttype) getSession().get("com.propmgr.dao.Bankaccounttype",
							id);
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

	public List findByExample(Bankaccounttype instance) {
		log.debug("finding Bankaccounttype instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Bankaccounttype")
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
		return new Bankaccounttype();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
