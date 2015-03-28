package com.propmgr.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Paymentstatus.
 * @see com.propmgr.dao.Paymentstatus
 * @author Arvind Sharma
 */
public class PaymentstatusDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentstatusDAO.class);

	public void persist(Paymentstatus transientInstance) {
		log.debug("persisting Paymentstatus instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Paymentstatus instance) {
		log.debug("attaching dirty Paymentstatus instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paymentstatus instance) {
		log.debug("attaching clean Paymentstatus instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Paymentstatus persistentInstance) {
		log.debug("deleting Paymentstatus instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paymentstatus merge(Paymentstatus detachedInstance) {
		log.debug("merging Paymentstatus instance");
		try {
			Paymentstatus result = (Paymentstatus) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Paymentstatus findById(java.lang.Long id) {
		log.debug("getting Paymentstatus instance with id: " + id);
		try {
			Paymentstatus instance = (Paymentstatus) getSession()
					.get("com.propmgr.dao.Paymentstatus",
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

	public List findByExample(Paymentstatus instance) {
		log.debug("finding Paymentstatus instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymentstatus")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Paymentstatus findLatestByPaymentId(long paymentId) {
		Session hbmSession = getSession();
		List<Paymentstatus> resultList = null;
		try {
			String queryString = "from Paymentstatus u where u.paymentmaster.paymentid = " + paymentId + " order by u.paymentstatusid DESC";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList!= null && resultList.size() > 0) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	public Paymentstatus findLatestByPaymentIdAndState(long paymentId, Paymentstate paymentstate) {
		Session hbmSession = getSession();
		List<Paymentstatus> resultList = null;
		try {
			String queryString = "from Paymentstatus u where u.paymentmaster.paymentid = " + 
					paymentId + " and u.paymentstate.paymentstateid = " + paymentstate.getPaymentstateid();
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList!= null && resultList.size() > 0) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Paymentstatus();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
