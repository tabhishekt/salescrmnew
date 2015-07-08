package com.propmgr.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitbooking.
 * @see com.propmgr.dao.Unitbooking
 * @author Arvind Sharma
 */
public class UnitbookingDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitbookingDAO.class);

	public Unitbooking findById(long id) {
		log.debug("getting Unitbooking instance with id: " + id);
		try {
			Unitbooking instance = (Unitbooking) getSession().get("com.propmgr.dao.Unitbooking", id);
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

	public List findByExample(Unitbooking instance) {
		log.debug("finding Unitbooking instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitbooking")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public long getMaxBookingFormNumber() {
		Session hbmSession = getSession();
		List<BigInteger> resultList = null;
		try {
			String queryString = "Select max(bookingFormNumber) from unitBooking";
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
	
	public boolean isBookingExistsForUnit(long unitId) {
		Session hbmSession = getSession();
		List<Unitbooking> resultList = null;
		try {
			String queryString = "from Unitbooking u where u.unitmaster.unitid = " + unitId + " and u.iscancelled = false";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList != null && resultList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return false;
	}
	
	public Unitbooking findByBookingFormNumber(long bookingFormNumber) {
		Session hbmSession = getSession();
		List<Unitbooking> resultList = null;
		try {
			String queryString = "from Unitbooking u where u.bookingformnumber = " + bookingFormNumber;
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
	
	public List<Unitbooking> findByBuilding(long buildingId) {
		Session hbmSession = getSession();
		List<Unitbooking> resultList = null;
		try {
			String queryString = "from Unitbooking u where u.unitmaster.projectbuilding.projectbuildingid = " + buildingId + " and u.iscancelled = false";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return resultList;
	}
	
	public Unitbooking findByUnit(Unitmaster unit) {
		Session hbmSession = getSession();
		List<Unitbooking> resultList = null;
		try {
			String queryString = "from Unitbooking u where u.unitmaster.unitid = " + unit.getUnitid() + " and u.iscancelled = false";
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
	
	public List<Unitbooking> findAllBookingsByCancelFlag(boolean isCancelled, long buildingId) {
		Session hbmSession = getSession();
		
		try {
			String queryString = "from Unitbooking u where u.iscancelled = " + isCancelled + " and u.unitmaster.projectbuilding.projectbuildingid = " + buildingId;
			Query query = hbmSession.createQuery(queryString);
			
			return query.list ();
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Unitbooking();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
