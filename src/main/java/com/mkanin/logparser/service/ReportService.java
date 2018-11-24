/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.model.Report;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface ReportService {
  public Integer findMaxReportNumber();
  public void insertReport(Report report);
  public List<Report> insertReports(Date startDate, Date endDate, String duration, Integer threshold) throws SQLException;
}
