package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Contactinfo.
 * @see com.propmgr.dao.Contactinfo
 * @author Arvind Sharma
 */
public class ContactinfoDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ContactinfoDAO.class);

	public Contactinfo findById(long id) {
		log.debug("getting Contactinfo instance with id: " + id);
		try {
			Contactinfo instance = (Contactinfo) getSession().get("com.propmgr.dao.Contactinfo", id);
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

	public List findByExample(Contactinfo instance) {
		log.debug("finding Contactinfo instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Contactinfo")
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
		return new Contactinfo();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
