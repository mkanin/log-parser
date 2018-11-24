/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkanin.logparser.dao.ReportDao;
import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.model.Report;
import com.mkanin.logparser.service.LogService;
import com.mkanin.logparser.service.ReportService;
import com.mkanin.logparser.util.converter.Converter;
import com.mkanin.logparser.util.converter.ConverterImpl;

/**
 * 
 * @author MikhailKanin
 *
 */
@Service("reportService")
@Repository
@Transactional
public class ReportServiceImpl implements ReportService {
  @Autowired
  private ReportDao reportDao;
  
  @Autowired
  private LogService logService;
  
  private Converter converter;
  
  public ReportServiceImpl() {
    converter = new ConverterImpl();
  }
  
  @Override
  @Transactional(readOnly = true)
  public Integer findMaxReportNumber() {
    return reportDao.findMaxReportNumber();
  }
  
  @Override
  public void insertReport(Report report) {
    reportDao.insertReport(report);
  }
  
  @Override
  public List<Report> insertReports(Date startDate, Date endDate, String duration, Integer threshold) 
      throws SQLException {
    Integer previousReportNumber = findMaxReportNumber();
    if (previousReportNumber == null) {
      previousReportNumber = 0;
    }
    Integer currentReportNumber = previousReportNumber + 1;
    List<Log> tmpLogs = logService.findIpsByTimeAndThreshold(startDate, endDate, threshold);
    List<Report> reports = new ArrayList<>();
    for (Log log : tmpLogs) {
      // We need to get IDs for each IP address from tmpLogs, because tmpLogs does not contain all ids.
      List<Log> logs = logService.findIpsByTimeAndLog(startDate, endDate, log);
      Report report = converter.createReportFromLog(logs, log, startDate, endDate, duration, threshold, currentReportNumber);
      reports.add(report);
      insertReport(report);
    }
    
    Integer maxReportNumberAfterSaving = findMaxReportNumber();
    if (!maxReportNumberAfterSaving.equals(currentReportNumber)) {
      throw new SQLException("The report was not saved in database!");
    }
    for (Report report : reports) {
      report.setReportNumber(maxReportNumberAfterSaving);
    }
    return reports;
  }
}
