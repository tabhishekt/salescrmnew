package com.propmgr.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Projectbankaccount.
 * @see com.propmgr.dao.Projectbankaccount
 * @author Arvind Sharma
 */
public class ProjectbankaccountDAO extends SuperDAO {

	private static final Log log = LogFactory
			.getLog(ProjectbankaccountDAO.class);

	public Projectbankaccount findById(java.lang.Long id) {
		log.debug("getting Projectbankaccount instance with id: " + id);
		try {
			Projectbankaccount instance = (Projectbankaccount) getSession().get(
							"com.propmgr.dao.Projectbankaccount", id);
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

	public List findByExample(Projectbankaccount instance) {
		log.debug("finding Projectbankaccount instance by example");
		try {
			List results = getSession().createCriteria("com.propmgr.dao.Projectbankaccount")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Projectbankaccount> findByProject(long projectId) {
		Session hbmSession = getSession();
		List<Projectbankaccount> resultList = null;
		
		try {
			String queryString = "from Projectbankaccount u where u.projectmaster.projectid = " + projectId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Projectbankaccount>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Projectbankaccount();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
