/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter;

import java.util.Date;
import java.util.List;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.model.Report;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface Converter {
  public Report createReportFromLog(List<Log> logs, Log log, Date startDate, Date endDate, String duration, 
      Integer threshold, Integer maxReportNumber);
  public String formatReport(Report report, String datePattern);
}
