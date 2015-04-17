package com.propmgr.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

public abstract class SuperDAO {
	private static final Log logger = LogFactory.getLog(SuperDAO.class);
	

	/**
	 * Insert the object into the database.
	 * 
	 * @param obj
	 *            The object to save.
	 * 
	 * @return The primary key of the newly inserted object.
	 */
	public Serializable save(Object obj) {
		Serializable retval = null;
		try
        {
            retval = getSession().save(obj);
//            getSession().flush();
//            commitTransaction();
//            beginTransaction();
        }
        catch(Exception e)
        {
            throw new HibernateException(e);
        }
        finally
        {
            
        }
		return retval;
	}

	/**
	 * Delete the object from the database.
	 * 
	 * @param obj
	 *            The object to delete.
	 */
	public void delete(Object obj) {
		try
        {
            getSession().delete(obj);
            getSession().flush();
            commitTransaction();
            beginTransaction();
        }
        catch(Exception e)
        {
            throw new DAOException(e);
        }
        finally
        {
        }

	}

	/**
	 * Update the object in the database.
	 * 
	 * @param obj
	 *            The object to update.
	 */
	public void update(Object obj) {
		try {
			getSession().update(obj);
	        getSession().flush();
	        commitTransaction();
	        beginTransaction();
		} catch (Exception e) {
			throw new HibernateException(e);
		} finally {
		}
	}
	
	/**
	 * Find object by primary key.
	 * 
	 * @param pk
	 *            the pk of the object to find.
	 */
	public Object findByPk(Serializable pk)
			throws com.propmgr.hibernate.DAOException {
		Session hibSession = null;
		Object retval = null;
		try {
			hibSession = getSession();
			retval = hibSession.get(getPojoObj().getClass(), pk);
		} catch (HibernateException e) {
			logger.error ("", e);
			throw new com.propmgr.hibernate.DAOException(e);
		} finally {
		}
		return retval;
	}

	/**
	 * Retrieve all objects of this type from the database.
	 * 
	 * @return All objects of this type from the database.
	 */
	public List findAll() throws com.propmgr.hibernate.DAOException {
		Session hibSession = null;
		List retval = null;
		try {
			hibSession = getSession();
			Criteria query = hibSession.createCriteria(getPojoObj().getClass());
			retval = query.list();
		} catch (HibernateException e) {
			logger.error ("", e);
			throw new com.propmgr.hibernate.DAOException(e);
		} finally {
		}
		return retval;
	}

	/**
	 * Find objects that match the object that was passed in.
	 * 
	 * @param obj
	 *            The object to base the search on.
	 * 
	 * @return A list of objects that match the object passed in.
	 */
	public List findByExample(Object obj) throws com.propmgr.hibernate.DAOException {
		List retval = new ArrayList();
		try {
			Example exampleCriteria = Example.create(obj).ignoreCase()
					.enableLike(MatchMode.ANYWHERE);
			Criteria criteria = createCriteria(obj);
			criteria.add(exampleCriteria);
			retval = criteria.list();
		} catch (HibernateException e) {
			logger.error ("", e);
			throw new com.propmgr.hibernate.DAOException(e);
		} finally {
		}
		return retval;
	}

	/**
	 * Find objects that match the object that was passed in starting from
	 * <code>from(0,1,..)</code> to <code>from+max</code>
	 * 
	 * @param obj
	 *            The object to base the search on.
	 * 
	 * @return A list of objects that match the object passed in.
	 */
	public List findByExampleWithRange(Object obj, int from, int max,
			Integer[] totalRecords) throws com.propmgr.hibernate.DAOException {
		List retval = new ArrayList();
		try {
			Example exampleCriteria = Example.create(obj).ignoreCase()
					.enableLike(MatchMode.ANYWHERE);
			if (totalRecords != null && totalRecords.length > 0) {
				Criteria criteria = createCriteria(obj);
				criteria.add(exampleCriteria);
				criteria.setProjection(Projections.projectionList().add(
						Projections.rowCount()));
				List cl = criteria.list();
				if (cl.size() > 0) {
					String total = cl.get(0).toString();
					if (logger.isDebugEnabled())
						logger.debug("+ Total Results [" + total + "]");
					totalRecords[0] = new Integer(total);
				} else {
					totalRecords[0] = new Integer(0);
					return retval;
				}
			}

			Criteria criteria = createCriteria(obj);
			criteria.add(exampleCriteria);
			criteria.setFirstResult(from);
			criteria.setMaxResults(max);
			retval = criteria.list();
		} catch (HibernateException e) {
			logger.error ("", e);
			throw new com.propmgr.hibernate.DAOException(e);
		} finally {

		}
		return retval;
	}

	/**
	 * commit the transaction.
	 * 
	 * All the subclass should not know abt the HibernateConnection.
	 *  
	 */
	public void beginTransaction() {
		//Doest not do any thing
	}

	/**
	 * Rollback the transaction.
	 * 
	 * This would rollback the enteries attached with the current txn. New txn
	 * will start with calling of save/update/delete method of DAO IF the no txn
	 * is created. Txn should be used as required.
	 * 
	 * After call to rollbackTransaction() -> Calling of save() method will
	 * start with a new Txn. Typically, its useful, when we want to do some
	 * insert/update/delete which is not very important and should not interfere
	 * with normal application flow.
	 * 
	 *  
	 */
	public void rollbackTransaction() {
		HibernateConnection.rollbackTransaction();
	}

	/**
	 * commit the transaction.
	 * 
	 * This would flush the save/update/delete in the current txn. It should not
	 * be called normally. This can be typically called when some thing like
	 * dont care inserts
	 *  
	 */
	public void commitTransaction() {
		HibernateConnection.commitTransaction();
	}

	/**
	 * clear the session. This will flush the entries from session and
	 * synchronize with database
	 *  
	 */
	public void flushSession() {
		HibernateConnection.getSession().flush();
	}

	/**
	 * close the session.
	 *  
	 */
	public void closeSession() {
		HibernateConnection.closeSession();
	}

	/**
	 * Remove the entity from the session.
	 *  
	 */
	public void evict(Object entity) {
		HibernateConnection.getSession().evict(entity);
	}

	/**
	 * Get the hibernate session
	 *  
	 */
	public Session getSession() {
		// HibernateConnection.beginTransaction();
		return HibernateConnection.getSession();
	}

	/**
	 * Get the hibernate session
	 *  
	 */
	public SessionFactory getSessionFactory() {
		return HibernateConnection.getSessionFactory();
	}

	/**
	 * This should be used with discussion and should be used only for purpose
	 * of some architecture level work. No application code should every try to
	 * use this. Caller should close the session and commit/rollback txns from
	 * this session. NOTE: This is not managed by the Architecture.
	 * 
	 * Get the hibernate session
	 *  
	 */
	public Session getNewSession() {
		return HibernateConnection.getSessionFactory().openSession();
	}

	protected abstract Object getPojoObj();

	protected abstract Criteria createCriteria(Object obj);
	
}