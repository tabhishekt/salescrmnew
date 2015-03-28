package com.propmgr.dao;

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
 * Home object for domain model class Usermaster.
 * @see com.propmgr.dao.Usermaster
 * @author Arvind Sharma
 */
public class UsermasterDAO extends SuperDAO {

	private static final Log log = LogFactory.getLog(UsermasterDAO.class);

	public void persist(Usermaster transientInstance) {
		log.debug("persisting Usermaster instance");
		try {
			getSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Usermaster instance) {
		log.debug("attaching dirty Usermaster instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Usermaster instance) {
		log.debug("attaching clean Usermaster instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Usermaster persistentInstance) {
		log.debug("deleting Usermaster instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Usermaster merge(Usermaster detachedInstance) {
		log.debug("merging Usermaster instance");
		try {
			Usermaster result = (Usermaster) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Usermaster findById(long id) {
		log.debug("getting Usermaster instance with id: " + id);
		try {
			Usermaster instance = (Usermaster) getSession().get("com.propmgr.dao.Usermaster", id);
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

	public List findByExample(Usermaster instance) {
		log.debug("finding Usermaster instance by example");
		try {
			List results = getSession()
					.createCriteria("com.propmgr.dao.Usermaster")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	@Override
	protected Object getPojoObj() {
		return new Usermaster();
	}

	@Override
	protected Criteria createCriteria(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Usermaster findByUserLoginCredentials(String loginName, String password) {
		Session hbmSession = getSession();
		List<Usermaster> resultList = null;
		try {
			String queryString = "from Usermaster u where u.loginname = '" + loginName + "' and u.password = '" + password + "'";
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
	
	public boolean isValidUserCredentials(String loginName, String password) {
		Session hbmSession = getSession();
		List<Usermaster> resultList = null;
		try {
			String queryString = "from Usermaster u where u.loginname = '" + loginName + "' and u.password = '" + password + "'";
			Query query = hbmSession.createQuery(queryString);
			resultList = query.list ();
			if (resultList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			log.error ("", e);
		}
		
		return false;
	}
}
