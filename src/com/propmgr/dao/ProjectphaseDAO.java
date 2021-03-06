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
 * Home object for domain model class Projectphase.
 * @see com.propmgr.dao.Projectphase
 * @author Arvind Sharma
 */
public class ProjectphaseDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(ProjectphaseDAO.class);

	public Projectphase findById(long id) {
		log.debug("getting Projectphase instance with id: " + id);
		try {
			Projectphase instance = (Projectphase) getSession().get("com.propmgr.dao.Projectphase", id);
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

	public List findByExample(Projectphase instance) {
		log.debug("finding Projectphase instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Projectphase")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<Projectphase> findByProject(long projectId) {
		Session hbmSession = getSession();
		List<Projectphase> resultList = null;
		try {
			String queryString = "from Projectphase u where u.projectmaster.projectid = " + projectId;
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			
			if (resultList.size() > 0) {
				return resultList;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return new ArrayList<Projectphase>();
	}
	
	@Override
	protected Object getPojoObj() {
		return new Projectphase();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
}
