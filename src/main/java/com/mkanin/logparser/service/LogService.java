/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mkanin.logparser.model.Log;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface LogService {
  public Integer findMaxLogNumber();
  public List<Log> findIpsByTimeAndThreshold(Date startDate, Date endDate, Integer threshold);
  public List<Log> findIpsByTimeAndLog(Date startDate, Date endDate, Log log);
  public List<Log> findIpsByTimeAndIpAddress(Date startDate, Date endDate, String ipAddress);
  public void insertLog(Log log);
  public Integer insertLogs(List<Log> logs) throws SQLException;
}
