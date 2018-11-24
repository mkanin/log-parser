/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.mkanin.logparser.model.Log;
import com.mkanin.logparser.model.Report;
import com.mkanin.logparser.service.LogFileService;
import com.mkanin.logparser.service.LogService;
import com.mkanin.logparser.service.ReportService;
import com.mkanin.logparser.util.converter.Converter;
import com.mkanin.logparser.util.converter.ConverterImpl;
import com.mkanin.logparser.util.converter.date.DateUtil;
import com.mkanin.logparser.util.converter.date.DateUtilImpl;

/**
 * 
 * @author MikhailKanin
 *
 */
public class Parser {
  
  private final static Logger logger = LoggerFactory.getLogger(Parser.class);
  
  private static DateUtil dateUtil = new DateUtilImpl();
  private static final String FORMAT = "yyyy-MM-dd.hh:mm:ss";
  private static final String DELIMITER = "|";
  
  public static void main(String[] args) {
    
    try {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      URL url = loader.getResource("log4j.properties");
      PropertyConfigurator.configure(url);
      
      AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
      ctx.scan("com.mkanin.logparser");
      ctx.refresh();
      
      LogFileService logFileService = ctx.getBean("logFileService", LogFileService.class);
      LogService logService = ctx.getBean("logService", LogService.class);
      ReportService reportService = ctx.getBean("reportService", ReportService.class);
      
      Converter converter = new ConverterImpl();
      
      Map<String, String> params = createParamsMapFromArgsArray(args);
      
      String fileName = params.get("accesslog");
      String startDateStr = params.get("startDate");
      String duration = params.get("duration");
      String thresholdStr = params.get("threshold");
      boolean additionalFlag = checkAdditionalParameters(startDateStr, duration, thresholdStr);
      
      if (((fileName == null) || fileName.isEmpty()) && (!additionalFlag)) {
        String outputStr = "You did not specify any of parameters";
        System.out.println(outputStr);
        logger.error(outputStr);
        return;
      }
      
      if ((fileName != null) && !fileName.isEmpty()) {
        String outputStr = "Start of parsing of the log file...";
        System.out.println(outputStr);
        logger.info(outputStr);
        List<Log> logs = logFileService.readAndParse(fileName, DELIMITER);
        Integer logFileNumber = logService.insertLogs(logs);
        outputStr = String.format("This log file was saved to database with the number %s", logFileNumber.toString());
        System.out.println(outputStr);
        logger.info(outputStr);
      }
            
      if (!additionalFlag) {
        return;
      }
      
      String outputStr = checkAdditionalParametersWithFormattedOutput(startDateStr, duration, thresholdStr);
      if (!outputStr.isEmpty()) {
        System.out.println(outputStr);
        logger.error(outputStr);
        return;
      }
      
      if ((duration == null) || duration.isEmpty()) {
        duration = "hourly";
      }
      duration = duration.trim();
      if (!duration.equals("daily")) {
        duration = "hourly";
      }
      
      Integer threshold = Integer.parseInt(thresholdStr.trim());
      Date startDate = dateUtil.convertStringToDate(startDateStr.trim(), FORMAT);
      Date endDate = dateUtil.createEndDateByDuration(startDate, duration);
      
      List<Report> reports = reportService.insertReports(startDate, endDate, duration, threshold);
      
      if (reports.size() > 0) {
        logger.info("Report: ");
        System.out.println("Report: ");
      }
      String datePattern = "yyyy-MM-dd HH:mm:ss";
      for (Report report : reports) {
        String reportStr = converter.formatReport(report, datePattern);
        logger.info(reportStr);
        System.out.println(reportStr);
      }
    // We catch this type of exception because we need to log it.
    } catch (NumberFormatException e) {
      System.out.println("Number format error.");
      logger.error("Number format error.", e);
    } catch (FileNotFoundException e) {
      System.out.println("The access log was not found.");
      logger.error("The access log was not found.", e);
    } catch (IOException e) {
      System.out.println("Error of input output.");
      logger.error("Error of input output.", e);
    } catch (ParseException e) {
      System.out.println("Incorrect format of one or more of input parameters.");
      logger.error("Incorrect format of one or more of input parameters.", e);  
    } catch (Exception e) {
      System.out.println("Something went wrong!");
      logger.error("Something went wrong!", e);
    }
  }
  
  public static Map<String, String> createParamsMapFromArgsArray(String[] args) {
    Map<String, String> params = new HashMap<>();
    for (String arg : args) {
      if (arg.startsWith("--")) {
        String[] paramStrings = arg.split("--");
        String paramString = paramStrings[1];
        String[] keyValueArr = paramString.split("=");
        if (keyValueArr.length == 2) {
          String key = keyValueArr[0];
          String value = keyValueArr[1];
          params.put(key, value);
        }
      }
    }
    return params;
  }
  
  public static boolean checkAdditionalParameters(String startDateStr, String durationStr, String thresholdStr) {
    if (((startDateStr == null) || startDateStr.isEmpty()) || ((thresholdStr == null) || thresholdStr.isEmpty())) {
      return false;
    }
    return true;
  }
  
  public static String checkAdditionalParametersWithFormattedOutput(String startDateStr, String durationStr, String thresholdStr) {
    String outputStr = "";
    if ((durationStr != null) && !durationStr.isEmpty()) {
      if ((startDateStr == null) || startDateStr.isEmpty()) {
        return "The parameter startDate was not defined";
      }
      if ((thresholdStr == null) || thresholdStr.isEmpty()) {
        return "The parameter Threshold was not defined";
      }
    }
    if (((startDateStr != null) && !startDateStr.isEmpty()) && ((thresholdStr == null) || thresholdStr.isEmpty())) {
      return "The parameter Threshold was not defined";
    }
    if (((thresholdStr != null) && !thresholdStr.isEmpty()) && ((startDateStr == null) || startDateStr.isEmpty())) {
      return "The parameter startDate was not defined";
    }
    return "";
  }
}