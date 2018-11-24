/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.converter;

import java.text.ParseException;

import com.mkanin.logparser.model.Log;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface StringLogParser {
  public Log parse(String str, String delimiter) throws ParseException;
}
