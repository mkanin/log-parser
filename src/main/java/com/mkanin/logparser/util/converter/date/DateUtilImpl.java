/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author MikhailKanin
 *
 */
public class DateUtilImpl implements DateUtil {
  
  private final String FORMAT_DATE = "yyyy-MM-dd hh:mm:ss";
  
  @Override
  public Date convertStringToDate(String str) throws ParseException {
    return convertStringToDate(str, FORMAT_DATE);
  }
  
  @Override
  public Date convertStringToDate(String str, String format)  
          throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(format);
    
    Date date = formatter.parse(str);
    return date;
  }
  
  @Override
  public Date createEndDateByDuration(Date startDate, String duration) {
    Date endDate;
    Calendar cal = Calendar.getInstance();
    cal.setTime(startDate);
    if (duration.equals("daily")) {
      cal.add(Calendar.DATE, 1);
      endDate = cal.getTime();
    } else {
      cal.add(Calendar.HOUR_OF_DAY, 1);
      endDate = cal.getTime();
    }
    return endDate;
  }
}
