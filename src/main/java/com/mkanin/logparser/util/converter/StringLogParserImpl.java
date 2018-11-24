/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.util.checker.IpAddressChecker;
import com.mkanin.logparser.util.checker.IpAddressCheckerImpl;
import com.mkanin.logparser.util.converter.date.DateUtil;

/**
 * 
 * @author MikhailKanin
 *
 */
public class StringLogParserImpl implements StringLogParser {
  private final int LEN_OF_ARRAY_FROM_INPUT_LINE = 5;
  
  private DateUtil dateUtil;
  private IpAddressChecker ipAddressChecker;

  public StringLogParserImpl(DateUtil dateUtil) {
    this.dateUtil = dateUtil;
    this.ipAddressChecker = new IpAddressCheckerImpl();
  }
  
  @Override
  public Log parse(String str, String delimiter) 
      throws ParseException {
    String[] strArray = str.split(Pattern.quote(delimiter));
    if (strArray.length != LEN_OF_ARRAY_FROM_INPUT_LINE) {
      throw new ParseException("Error of parsing the line of the input file.", 0);
    }

    Log log = new Log();
    Date time = dateUtil.convertStringToDate(strArray[0]);
    log.setTime(time);
    String ipAddress = strArray[1];
    boolean flagIp = ipAddressChecker.check(ipAddress);
    if (!flagIp) {
      throw new ParseException("Incorrect IP Address in the log file.", 0);
    }
    log.setIpAddress(ipAddress);
    log.setRequest(strArray[2]);
    Integer serverStatus = Integer.valueOf(strArray[3]);
    log.setServerStatus(serverStatus);
    log.setUserAgent(strArray[4]);
    return log;
  }
}
