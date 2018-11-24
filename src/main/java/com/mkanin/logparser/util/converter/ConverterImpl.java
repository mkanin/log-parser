/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.model.Report;

/**
 * 
 * @author MikhailKanin
 *
 */
public class ConverterImpl implements Converter {
  
  public Report createReportFromLog(List<Log> logs, Log log, Date startDate, Date endDate, String duration, 
      Integer threshold, Integer reportNumber) {
    Report report = new Report();
    String ipAddress = log.getIpAddress();
    report.setIpAddress(ipAddress);
    String comment = ">" + threshold.toString();
    report.setComment(comment);
    report.setDuration(duration);
    Integer logNumber = log.getLogNumber();
    report.setLogNumber(logNumber);
    report.setReportNumber(reportNumber);
    report.setThreshold(threshold);
    report.setStartDate(startDate);
    report.setEndDate(endDate);
    report.setLogs(logs);
    return report;
  }
  
  @Override
  public String formatReport(Report report, String datePattern) {
    Date startDate = report.getStartDate();
    Date endDate = report.getEndDate();
    
    DateFormat dateFormat = new SimpleDateFormat(datePattern);
    String startDateStr = dateFormat.format(startDate);
    String endDateStr = dateFormat.format(endDate);
    
    String ipAddress = report.getIpAddress();    
    String duration = report.getDuration();    
    String comment = report.getComment();    
    Integer logNumber = report.getLogNumber();    
    Integer reportNumber = report.getReportNumber();
    
    return String.format("IP Address: %s, Start Date: %s, End Date (except border): %s, Duration: %s, Comment: %s, Log File Number: %s, Report Number: %s", 
        ipAddress, startDateStr, endDateStr, duration, comment, logNumber.toString(), reportNumber.toString());
  }
}
