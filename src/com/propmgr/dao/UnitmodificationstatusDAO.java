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
 * Home object for domain model class Unitmodificationstatus.
 * @see com.propmgr.dao.Unitmodificationstatus
 * @author Arvind Sharma
 */
public class UnitmodificationstatusDAO extends SuperDAO {

	private static final Log log = LogFactory
			.getLog(UnitmodificationstatusDAO.class);

	public Unitmodificationstatus findById(java.lang.Long id) {
		log.debug("getting Unitmodificationstatus instance with id: " + id);
		try {
			Unitmodificationstatus instance = (Unitmodificationstatus) 
					getSession().get("com.propmgr.dao.Unitmodificationstatus", id);
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

	public List findByExample(Unitmodificationstatus instance) {
		log.debug("finding Unitmodificationstatus instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitmodificationstatus")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Unitmodificationstatus findLatestByBookingId(long bookingId) {
		Session hbmSession = getSession();
		List<Unitmodificationstatus> resultList = null;
		try {
			String queryString = "from Unitmodificationstatus u where u.unitbooking.unitbookingid = " + bookingId + " order by u.unitmodificationstatusid DESC";
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
	
	public Unitmodificationstatus findLatestByBookingIdAndState(long bookingId, long unitmodificationstateID) {
		Session hbmSession = getSession();
		List<Unitmodificationstatus> resultList = null;
		
		try {
			String queryString = "from Unitmodificationstatus u where u.unitbooking.unitbookingid = " + 
					bookingId + " and u.unitmodificationstate.unitmodificationstateid = " + unitmodificationstateID;
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
		return new Unitmodificationstatus();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
