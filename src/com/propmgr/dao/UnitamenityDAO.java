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
 * Home object for domain model class Unitamenity.
 * @see com.propmgr.dao.Unitamenity
 * @author Arvind Sharma
 */
public class UnitamenityDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UnitamenityDAO.class);

	public Unitamenity findById(long id) {
		log.debug("getting Unitamenity instance with id: " + id);
		try {
			Unitamenity instance = (Unitamenity) getSession().get("com.propmgr.dao.Unitamenity", id);
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

	public List findByExample(Unitamenity instance) {
		log.debug("finding Unitamenity instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Unitamenity")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Unitamenity> findByUnit(long unitId) {
		Session hbmSession = getSession();
		List<Unitamenity> resultList = null;
		try {
			String queryString = "from Unitamenity u where u.unitmaster.unitid = " + unitId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Unitamenity>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Unitamenity();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
