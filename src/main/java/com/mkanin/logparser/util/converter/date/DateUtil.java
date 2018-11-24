/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter.date;

import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface DateUtil {
  public Date convertStringToDate(String str) throws ParseException;
  public Date convertStringToDate(String str, String format) throws ParseException;
  public Date createEndDateByDuration(Date startDate, String duration);
}
