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
 * Home object for domain model class Amenitypricepolicy.
 * @see com.propmgr.dao.Amenitypricepolicy
 * @author Arvind Sharma
 */
public class AmenitypricepolicyDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(AmenitypricepolicyDAO.class);

	public Amenitypricepolicy findById(java.lang.Long id) {
		log.debug("getting Amenitypricepolicy instance with id: " + id);
		try {
			Amenitypricepolicy instance = (Amenitypricepolicy) getSession().get("com.propmgr.dao.Amenitypricepolicy", id);
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

	public List findByExample(Amenitypricepolicy instance) {
		log.debug("finding Amenitypricepolicy instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Amenitypricepolicy")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public Double findAmenityChargeByPricePolicyAndAmenityType(long unitPricePolicyId, long amenityId) {
		Session hbmSession = getSession();
		List<Amenitypricepolicy> resultList = null;
		
		try {
			String queryString = "from Amenitypricepolicy u where u.unitpricepolicy.unitpricepolicyid = " + unitPricePolicyId + 
					" and u.amenity.amenityid = " + amenityId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				Amenitypricepolicy amenitypricepolicy = resultList.get(0);
				return amenitypricepolicy.getAmenitycharge();
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new Double(0.0);
	}
	
	public Amenitypricepolicy findByPricePolicyAndAmenityType(long unitPricePolicyId, long amenityId) {
		Session hbmSession = getSession();
		List<Amenitypricepolicy> resultList = null;
		
		try {
			String queryString = "from Amenitypricepolicy u where u.unitpricepolicy.unitpricepolicyid = " + unitPricePolicyId + 
					" and u.amenity.amenityid = " + amenityId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				Amenitypricepolicy amenitypricepolicy = resultList.get(0);
				return amenitypricepolicy;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return null;
	}
	
	@Override
	protected Object getPojoObj() {
		return new Amenitypricepolicy();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
