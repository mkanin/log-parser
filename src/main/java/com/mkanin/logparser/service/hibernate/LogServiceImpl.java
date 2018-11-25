/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service.hibernate;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkanin.logparser.dao.LogDao;
import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.service.LogService;

/**
 * 
 * @author MikhailKanin
 *
 */
@Service("logService")
public class LogServiceImpl implements LogService {
  
  @Autowired
  private LogDao logDao;
  
  @Override
  @Transactional(readOnly = true)
  public Integer findMaxLogNumber() {
    return logDao.findMaxLogNumber();
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<Log> findIpsByTimeAndThreshold(Date startDate, Date endDate, Integer threshold) {
    return logDao.findIpsByTimeAndThreshold(startDate, endDate, threshold);
  }
  
  @Override
  @Transactional(readOnly = true)
  public List<Log> findIpsByTimeAndLog(Date startDate, Date endDate, Log log) {
    String ipAddress = log.getIpAddress();
    return findIpsByTimeAndIpAddress(startDate, endDate, ipAddress);
  } 
  
  @Override
  @Transactional(readOnly = true)
  public List<Log> findIpsByTimeAndIpAddress(Date startDate, Date endDate, String ipAddress) {
    return logDao.findIpsByTimeAndIpAddress(startDate, endDate, ipAddress);
  }
  
  @Override
  @Transactional
  public void insertLog(Log log) {
    logDao.insertLog(log);
  }
  
  @Override
  @Transactional
  public Integer insertLogs(List<Log> logs) 
          throws SQLException {
      Integer previousLogFileNumber = findMaxLogNumber();
      if (previousLogFileNumber == null) {
        previousLogFileNumber = 0;
      }
      Integer currentLogFileNumber = ++previousLogFileNumber;
      for (Log log : logs) {
        log.setLogNumber(currentLogFileNumber);
        insertLog(log);
      }
      Integer maxLogFileNumberAfterSaving = findMaxLogNumber();
      if (!maxLogFileNumberAfterSaving.equals(currentLogFileNumber)) {
        throw new SQLException("This log file was not saved in database!");
      }
      return maxLogFileNumberAfterSaving;
  }
}
