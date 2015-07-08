package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitprice.
 * @see com.propmgr.dao.Unitprice
 * @author Arvind Sharma
 */
public class UnitpricepolicyDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitpricepolicyDAO.class);

	public Unitpricepolicy findById(long id) {
		log.debug("getting Unitpricepolicy instance with id: " + id);
		try {
			Unitpricepolicy instance = (Unitpricepolicy) getSession()
					.get("com.propmgr.dao.Unitpricepolicy", id);
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

	public List findByExample(Unitpricepolicy instance) {
		log.debug("finding Unitprice instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitprice")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Unitpricepolicy findByPolicyName(String policyName) {
		Session hbmSession = getSession();
		List<Unitpricepolicy> resultList = null;
		try {
			String queryString = "from Unitpricepolicy u where u.policyname = '" + policyName + "'";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Unitpricepolicy();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
