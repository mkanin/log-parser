/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author MikhailKanin
 *
 */
@Entity
@Table(name = "t_report")
@NamedQueries({
  @NamedQuery(name = "report.findMaxReportNumber",
          query = "select max(report.reportNumber) from Report report")
})
public class Report {
  private Long id;
  private String ipAddress;
  private Date startDate;
  private Date endDate;
  private String duration;
  private Integer threshold;
  private String comment;
  private Integer logNumber;
  private Integer reportNumber;
  private List<Log> logs = new ArrayList<>();
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  @Transient
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  @Column(name = "start_date")
  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  @Transient  
  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Column(name = "duration")
  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
  
  @Column(name = "threshold")
  public Integer getThreshold() {
    return threshold;
  }

  public void setThreshold(Integer threshold) {
    this.threshold = threshold;
  }

  @Column(name = "report_comment")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
  
  @Transient  
  public Integer getLogNumber() {
    return logNumber;
  }

  public void setLogNumber(Integer logNumber) {
    this.logNumber = logNumber;
  }

  @Column(name = "report_number")
  public Integer getReportNumber() {
    return reportNumber;
  }

  public void setReportNumber(Integer reportNumber) {
    this.reportNumber = reportNumber;
  }
  
  @ManyToMany
  @JoinTable(name = "t_log_report_detail",
          joinColumns = @JoinColumn(name = "report_id"),
          inverseJoinColumns = @JoinColumn(name = "log_id"))
  public List<Log> getLogs() {
    return logs;
  }

  public void setLogs(List<Log> logs) {
    this.logs = logs;
  }
  
  public String toString() {    
    return String.format("Report - Id: %s, IP Address: %s, Start Date: %s, End Date: %s, Duration: %s, Threshold: %s, Comment: %s, Log number: %s, Report Number: %s",
        id.toString(), ipAddress, startDate.toString(), endDate.toString(), duration, threshold.toString(), comment, logNumber.toString(), reportNumber.toString());
  }
}
