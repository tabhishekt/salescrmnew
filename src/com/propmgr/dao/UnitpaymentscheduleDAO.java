package com.propmgr.dao;

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
 * Home object for domain model class Unitpaymentschedule.
 * @see com.propmgr.dao.Unitpaymentschedule
 * @author Arvind Sharma
 */
public class UnitpaymentscheduleDAO extends SuperDAO {

	private static final Log log = LogFactory
			.getLog(UnitpaymentscheduleDAO.class);

	public void persist(Unitpaymentschedule transientInstance) {
		log.debug("persisting Unitpaymentschedule instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Unitpaymentschedule instance) {
		log.debug("attaching dirty Unitpaymentschedule instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Unitpaymentschedule instance) {
		log.debug("attaching clean Unitpaymentschedule instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Unitpaymentschedule persistentInstance) {
		log.debug("deleting Unitpaymentschedule instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Unitpaymentschedule merge(Unitpaymentschedule detachedInstance) {
		log.debug("merging Unitpaymentschedule instance");
		try {
			Unitpaymentschedule result = (Unitpaymentschedule) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Unitpaymentschedule findById(long id) {
		log.debug("getting Unitpaymentschedule instance with id: " + id);
		try {
			Unitpaymentschedule instance = (Unitpaymentschedule) getSession().get("com.propmgr.dao.Unitpaymentschedule", id);
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

	public List findByExample(Unitpaymentschedule instance) {
		log.debug("finding Unitpaymentschedule instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitpaymentschedule")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Unitpaymentschedule findByType(String type) {
		Session hbmSession = getSession();
		List<Unitpaymentschedule> resultList = null;
		
		try {
			String queryString = "from Unitpaymentschedule u where u.paymentscheduletype = '" + type + "'";
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
	
	public List<Unitpaymentschedule> findByProjectBuilding(long buildingId) {
		Session hbmSession = getSession();
		List<Unitpaymentschedule> resultList = null;
		try {
			String queryString = "from Unitpaymentschedule u where u.projectbuilding.projectbuildingid = " + buildingId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Unitpaymentschedule>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Unitpaymentschedule();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
