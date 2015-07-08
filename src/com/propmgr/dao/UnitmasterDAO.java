package com.propmgr.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.propmgr.hibernate.SuperDAO;

/**
 * Home object for domain model class Unitmaster.
 * @see com.propmgr.dao.Unitmaster
 * @author Arvind Sharma
 */
public class UnitmasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitmasterDAO.class);

	public Unitmaster findById(long id) {
		log.debug("getting Unitmaster instance with id: " + id);
		try {
			Unitmaster instance = (Unitmaster) getSession().get("com.propmgr.dao.Unitmaster", id);
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

	public List findByExample(Unitmaster instance) {
		log.debug("finding Unitmaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitmaster")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Unitmaster> findByProjectBuilding(long buildingId) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		try {
			String queryString = "from Unitmaster u where u.projectbuilding.projectbuildingid = " + buildingId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Unitmaster>();
	}
	
	public List<Unitmaster> findByProjectBuildingAndFloorNumber(long buildingId, int floorNumber) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		try {
			String queryString = "from Unitmaster u where u.projectbuilding.projectbuildingid = " 
				+ buildingId + "and u.floornumber = " + floorNumber;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Unitmaster>();
	}
	
	public Double findMaxFloorRiseByFloorNumberAndBuilding(int floorNumber, long buildingId) {
		Session hbmSession = getSession();
		List<Double> resultList = null;
		try {
			String queryString = "select max(floorRise) from unitMaster u where u.floorNumber = " + floorNumber +  " and u.projectBuilding = " + buildingId;
			Query query = hbmSession.createSQLQuery(queryString);
			resultList = query.list ();
			
			if (resultList!= null && resultList.size() > 0 && resultList.get(0) != null) {
				return resultList.get(0);
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new Double(0);
	}
	
	public Double findMaxBookingAmountByUnitTypeAndBuilding(long unitTypeId, long buildingId) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		List<Long> bookedUnits = new ArrayList<Long>();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		double maxBookingAmount = 0;
		
		try {
			List<Unitbooking> bookings = unitbookingDAO.findByBuilding(buildingId);
			for (Unitbooking booking : bookings) {
				bookedUnits.add(booking.getUnitmaster().getUnitid());
			}
			
			String queryString = "from Unitmaster u where u.unittype.unittypeid = " + unitTypeId + 
					" and u.projectbuilding.projectbuildingid = " 
					+ buildingId + " and unitid not in (:bookedunits)";
			Query query = hbmSession.createQuery(queryString);
			query.setParameterList("bookedunits", bookedUnits);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				for (Unitmaster unit : resultList) {
					double bookingAmount = unit.getBookingamount().doubleValue();
					if (bookingAmount > maxBookingAmount) {
						maxBookingAmount = bookingAmount;
					}
				}
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return maxBookingAmount;
	}
	
	public Double findMaxOtherChargesByUnitTypeAndBuilding(long unitTypeId, long buildingId) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		List<Long> bookedUnits = new ArrayList<Long>();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		double maxOtherCharges = 0;
		
		try {
			List<Unitbooking> bookings = unitbookingDAO.findByBuilding(buildingId);
			for (Unitbooking booking : bookings) {
				bookedUnits.add(booking.getUnitmaster().getUnitid());
			}
			
			String queryString = "from Unitmaster u where u.unittype.unittypeid = " + unitTypeId + 
					" and u.projectbuilding.projectbuildingid = " 
					+ buildingId + " and unitid not in (:bookedunits)";
			Query query = hbmSession.createQuery(queryString);
			query.setParameterList("bookedunits", bookedUnits);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				for (Unitmaster unit : resultList) {
					double otherCharges = unit.getOthercharges().doubleValue();
					if (otherCharges > maxOtherCharges) {
						maxOtherCharges = otherCharges;
					}
				}
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return maxOtherCharges;
	}
	
	public boolean hasUnbookedByUnittypeAndProjectBuilding(long unitTypeId, long buildingId) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		List<Long> bookedUnits = new ArrayList<Long>();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		
		try {
			List<Unitbooking> bookings = unitbookingDAO.findByBuilding(buildingId);
			for (Unitbooking booking : bookings) {
				bookedUnits.add(booking.getUnitmaster().getUnitid());
			}
			
			String queryString = "from Unitmaster u where u.unittype.unittypeid = " + unitTypeId + 
					" and u.projectbuilding.projectbuildingid = " 
					+ buildingId + " and unitid not in (:bookedunits)";
			Query query = hbmSession.createQuery(queryString);
			query.setParameterList("bookedunits", bookedUnits);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return false;
	}
	
	public List<Unitmaster> findUnbookedByProjectBuilding(long buildingId) {
		Session hbmSession = getSession();
		List<Unitmaster> resultList = null;
		List<Long> bookedUnits = new ArrayList<Long>();
		UnitbookingDAO unitbookingDAO = new UnitbookingDAO();
		
		try {
			List<Unitbooking> bookings = unitbookingDAO.findByBuilding(buildingId);
			for (Unitbooking booking : bookings) {
				bookedUnits.add(booking.getUnitmaster().getUnitid());
			}
			
			String queryString = "from Unitmaster u where u.projectbuilding.projectbuildingid = " 
					+ buildingId + " and unitid not in (:bookedunits)";
			Query query = hbmSession.createQuery(queryString);
			query.setParameterList("bookedunits", bookedUnits);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Unitmaster>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Unitmaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
