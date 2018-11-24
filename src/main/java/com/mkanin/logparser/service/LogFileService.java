/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.mkanin.logparser.model.Log;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface LogFileService {
  public List<Log> readAndParse(String fileName, String delimiter) throws FileNotFoundException, IOException;
}
