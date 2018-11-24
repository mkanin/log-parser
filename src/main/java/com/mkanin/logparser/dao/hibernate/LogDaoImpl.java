/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.dao.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mkanin.logparser.dao.LogDao;
import com.mkanin.logparser.model.Log;

/**
 * 
 * @author MikhailKanin
 *
 */
@Repository("logDao")
@Transactional
public class LogDaoImpl implements LogDao {
  private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }
  
  @Resource(name="sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  @Override
  @Transactional(readOnly = true)
  public Integer findMaxLogNumber() {
    Query maxLogNumberQuery = sessionFactory.getCurrentSession().getNamedQuery("log.findMaxLogNumber");
    return (Integer) maxLogNumberQuery.getSingleResult();
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<Log> findIpsByTimeAndThreshold(Date startDate, Date endDate, Integer threshold) {
    
    Long th = new Long(threshold);
    return (List<Log>) sessionFactory.getCurrentSession().getNamedQuery("log.findIpByTimeAndThreshold").
            setParameter("startDate", startDate).setParameter("endDate", endDate).
            setParameter("threshold", th).list();
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<Log> findIpsByTimeAndIpAddress(Date startDate, Date endDate, String ipAddress) {
    return (List<Log>) sessionFactory.getCurrentSession().getNamedQuery("log.findIpByTimeAndIpAddress").
        setParameter("startDate", startDate).setParameter("endDate", endDate).
        setParameter("ipAddress", ipAddress).list();
  }
  
  public void insertLog(Log log) {
    this.sessionFactory.getCurrentSession().save(log);
  }
}
