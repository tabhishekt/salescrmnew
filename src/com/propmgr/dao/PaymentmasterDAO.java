package com.propmgr.dao;

import java.math.BigInteger;
import java.util.ArrayList;
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
 * Home object for domain model class Paymentmaster.
 * @see com.propmgr.dao.Paymentmaster
 * @author Arvind Sharma
 */
public class PaymentmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentmasterDAO.class);

	public void persist(Paymentmaster transientInstance) {
		log.debug("persisting Paymentmaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Paymentmaster instance) {
		log.debug("attaching dirty Paymentmaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Paymentmaster instance) {
		log.debug("attaching clean Paymentmaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Paymentmaster persistentInstance) {
		log.debug("deleting Paymentmaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Paymentmaster merge(Paymentmaster detachedInstance) {
		log.debug("merging Paymentmaster instance");
		try {
			Paymentmaster result = (Paymentmaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Paymentmaster findById(long id) {
		log.debug("getting Paymentmaster instance with id: " + id);
		try {
			Paymentmaster instance = (Paymentmaster) getSession().get("com.propmgr.dao.Paymentmaster", id);
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

	public List findByExample(Paymentmaster instance) {
		log.debug("finding Paymentmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Paymentmaster")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Paymentmaster> findByUnitbooking(long unitbookingid) {
		Session hbmSession = getSession();
		List<Paymentmaster> resultList = null;
		try {
			String queryString = "from Paymentmaster u where u.unitbooking.unitbookingid = " + unitbookingid;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	public long getMaxReceiptNumber() {
		Session hbmSession = getSession();
		List<BigInteger> resultList = null;
		try {
			String queryString = "Select max(receiptNumber) from paymentMaster";
			Query query = hbmSession.createSQLQuery(queryString);
			resultList = query.list ();
			
			if (resultList!= null && resultList.size() > 0 && resultList.get(0) != null) {
				return resultList.get(0).longValue();
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return -1;
	}
	
	public boolean isDuplicateCheque(String bankName, String chequeNumber) {
		Session hbmSession = getSession();
		List<Paymentmaster> resultList = null;
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		PaymentstateDAO paymentstateDAO = new PaymentstateDAO();
		Paymentstatus paymentstatus = null;
		
		try {
			String queryString = "from Paymentmaster u where u.bankname = '" 
				+ bankName + "' and u.chequenumber = '" + chequeNumber + "'";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				Paymentmaster payment = resultList.get(0);
				Paymentstate paymentstate = paymentstateDAO.findByName("Bounced");
				if (paymentstate != null) {
					paymentstatus = paymentstatusDAO.findLatestByPaymentIdAndState(payment.getPaymentid(), paymentstate);
				}
				return (paymentstatus != null) ? false : true;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return false;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Paymentmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
