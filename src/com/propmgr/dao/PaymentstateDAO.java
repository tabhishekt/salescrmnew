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
 * Home object for domain model class Paymentstate.
 * @see com.propmgr.dao.Paymentstate
 * @author Arvind Sharma
 */
public class PaymentstateDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentstateDAO.class);

	public Paymentstate findById(java.lang.Long id) {
		log.debug("getting Paymentstate instance with id: " + id);
		try {
			Paymentstate instance = (Paymentstate) getSession()
					.get("com.propmgr.dao.Paymentstate", id);
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

	public List findByExample(Paymentstate instance) {
		log.debug("finding Paymentstate instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymentstate")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Paymentstate findByName(String state) {
		Session hbmSession = getSession();
		List<Paymentstate> resultList = null;
		
		try {
			String queryString = "from Paymentstate u where u.paymentstatename = '" + state + "'";
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
		return new Paymentstate();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
