package com.propmgr.dao;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Bankbranch.
 * @see com.propmgr.dao.Bankbranch
 * @author Arvind Sharma
 */
public class BankbranchDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(BankbranchDAO.class);

	public Bankbranch findById(java.lang.Long id) {
		log.debug("getting Bankbranch instance with id: " + id);
		try {
			Bankbranch instance = (Bankbranch) getSession().get("com.propmgr.dao.Bankbranch", id);
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

	public List findByExample(Bankbranch instance) {
		log.debug("finding Bankbranch instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Bankbranch")
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
		return new Bankbranch();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
