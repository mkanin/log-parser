/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.service.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.service.LogFileService;
import com.mkanin.logparser.util.converter.StringLogParser;
import com.mkanin.logparser.util.converter.StringLogParserImpl;
import com.mkanin.logparser.util.converter.date.DateUtil;
import com.mkanin.logparser.util.converter.date.DateUtilImpl;
import com.mkanin.logparser.util.file.StringFileReader;
import com.mkanin.logparser.util.file.StringFileReaderImpl;

/**
 * 
 * @author MikhailKanin
 *
 */
@Service("logFileService")
@Repository
public class LogFileServiceImpl implements LogFileService {
  private final Logger logger = LoggerFactory.getLogger(LogFileServiceImpl.class);
  
  private DateUtil dateUtil;
  private StringFileReader stringFileReader; 
  private StringLogParser stringLogParser;
  
  public LogFileServiceImpl() {
    this.dateUtil = new DateUtilImpl();
    this.stringFileReader = new StringFileReaderImpl();
    this.stringLogParser = new StringLogParserImpl(this.dateUtil);
    
  }
  
  public List<Log> readAndParse(String fileName, String delimiter) throws FileNotFoundException, IOException {
    List<String> logStrings = stringFileReader.read(fileName);
    List<Log> logs = new ArrayList<>();
    for (String logString : logStrings) {
      try {
        Log log = stringLogParser.parse(logString, delimiter);
        logs.add(log);
      } catch (ParseException e) {
        logger.error("Error of parsing the line of the input file.", e);
      }
      
    }
    return logs;
  }
}
