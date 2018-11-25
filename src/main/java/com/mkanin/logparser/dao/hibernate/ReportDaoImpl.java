/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.dao.hibernate;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mkanin.logparser.dao.ReportDao;
import com.mkanin.logparser.model.Report;

/**
 * 
 * @author MikhailKanin
 *
 */
@Repository("reportDao")
public class ReportDaoImpl implements ReportDao {

  private SessionFactory sessionFactory;

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }
  
  @Resource(name="sessionFactory")
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  @Override
  public void insertReport(Report report) {
    sessionFactory.getCurrentSession().save(report); 
  }
  
  @Override
  public Integer findMaxReportNumber() {
    Query maxLogNumberQuery = sessionFactory.getCurrentSession().getNamedQuery("report.findMaxReportNumber");
    return (Integer) maxLogNumberQuery.getSingleResult();
  }
}
