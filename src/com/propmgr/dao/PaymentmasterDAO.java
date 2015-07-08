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
 * Home object for domain model class Paymentmaster.
 * @see com.propmgr.dao.Paymentmaster
 * @author Arvind Sharma
 */
public class PaymentmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(PaymentmasterDAO.class);

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
	
	public long getMaxReceiptNumber(long projectId) {
		Session hbmSession = getSession();
		List<Long> resultList = null;
		try {
			String queryString = "Select max(receiptnumber) from Paymentmaster p "
					+ "where p.unitbooking.unitmaster.projectbuilding.projectphase.projectmaster.projectid = " + projectId;
			
			Query query = hbmSession.createQuery(queryString);
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
	
	public boolean isDuplicateAltReceiptNumber(long altReceiptNumber, long projectId) {
		Session hbmSession = getSession();
		List<Paymentmaster> resultList = null;
		PaymentstatusDAO paymentstatusDAO = new PaymentstatusDAO();
		PaymentstateDAO paymentstateDAO = new PaymentstateDAO();
		Paymentstatus paymentstatus = null;
		
		try {
			String queryString = "from Paymentmaster u where u.altreceiptnumber = " 
				+ altReceiptNumber + " and u.unitbooking.unitmaster.projectbuilding.projectphase.projectmaster.projectid = " + projectId;
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
