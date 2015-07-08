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
 * Home object for domain model class Projectbuilding.
 * @see com.propmgr.dao.Projectbuilding
 * @author Arvind Sharma
 */
public class ProjectbuildingDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ProjectbuildingDAO.class);

	public Projectbuilding findById(long id) {
		log.debug("getting Projectbuilding instance with id: " + id);
		try {
			Projectbuilding instance = (Projectbuilding) getSession().get("com.propmgr.dao.Projectbuilding", id);
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

	public List findByExample(Projectbuilding instance) {
		log.debug("finding Projectbuilding instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Projectbuilding")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Projectbuilding> findByProject(long projectId) {
		Session hbmSession = getSession();
		List<Projectbuilding> resultList = null;
		try {
			String queryString = "from Projectbuilding u where u.projectphase.projectmaster.projectid = " + projectId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Projectbuilding>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Projectbuilding();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
