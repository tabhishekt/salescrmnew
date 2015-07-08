package com.propmgr.dao;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Address.
 * @see com.propmgr.dao.Address
 * @author Arvind Sharma
 */
public class AddressDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(AddressDAO.class);

	public Address findById(long id) {
		log.debug("getting Address instance with id: " + id);
		try {
			Address instance = (Address) getSession()
					.get("com.propmgr.dao.Address", id);
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

	public List findByExample(Address instance) {
		log.debug("finding Address instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Address")
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
		return new Address();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
