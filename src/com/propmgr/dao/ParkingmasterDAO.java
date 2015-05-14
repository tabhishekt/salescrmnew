package com.propmgr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Parkingmaster.
 * @see com.propmgr.dao.Parkingmaster
 * @author Arvind Sharma
 */
public class ParkingmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ParkingmasterDAO.class);

	public void persist(Parkingmaster transientInstance) {
		log.debug("persisting Parkingmaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Parkingmaster instance) {
		log.debug("attaching dirty Parkingmaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Parkingmaster instance) {
		log.debug("attaching clean Parkingmaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Parkingmaster persistentInstance) {
		log.debug("deleting Parkingmaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Parkingmaster merge(Parkingmaster detachedInstance) {
		log.debug("merging Parkingmaster instance");
		try {
			Parkingmaster result = (Parkingmaster) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Parkingmaster findById(java.lang.Long id) {
		log.debug("getting Parkingmaster instance with id: " + id);
		try {
			Parkingmaster instance = (Parkingmaster) getSession().get("com.propmgr.dao.Parkingmaster",
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

	public List findByExample(Parkingmaster instance) {
		log.debug("finding Parkingmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Parkingmaster")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Parkingmaster> findByProjectBuilding(long buildingId) {
		Session hbmSession = getSession();
		List<Parkingmaster> resultList = null;
		try {
			String queryString = "from Parkingmaster u where u.projectbuilding.projectbuildingid = " + buildingId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Parkingmaster>();
	}
	
	public Parkingmaster findByProjectBuildingAndType(long buildingId, String parkingTypeCode) {
		Session hbmSession = getSession();
		List<Parkingmaster> resultList = null;
		
		try {
			String queryString = "from Parkingmaster u where u.projectbuilding.projectbuildingid = " + buildingId
					+ " and u.parkingtype.parkingcode = '" + parkingTypeCode + "'";
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
		return new Parkingmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		return null;
	}
}
