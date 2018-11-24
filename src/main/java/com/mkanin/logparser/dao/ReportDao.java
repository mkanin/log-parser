/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.dao;

import com.mkanin.logparser.model.Report;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface ReportDao {
  public Integer findMaxReportNumber();
  public void insertReport(Report report);
}
