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

/**
 * 
 * @author MikhailKanin
 *
 */
@Entity
@Table(name = "t_log")
@NamedQueries({
  @NamedQuery(name = "log.findMaxLogNumber",
          query = "select max(lg.logNumber) from Log lg"),
  @NamedQuery(name = "log.findIpByTimeAndIpAddress",
          query = "select log1 from Log log1 "
                  + "where log1.time >= :startDate and log1.time < :endDate and "
                  + "log1.logNumber = (select max(lg.logNumber) from Log lg) and "
                  + "log1.ipAddress = :ipAddress "
                  + "order by log1.ipAddress"),
  @NamedQuery(name = "log.findIpByTimeAndThreshold",
          query = "select log1 from Log log1 "
                  + "where log1.time >= :startDate and log1.time < :endDate and "
                  + "log1.logNumber = (select max(lg.logNumber) from Log lg) "
                  + "group by log1.ipAddress "
                  + "having count(log1.ipAddress) > :threshold "
                  + "order by log1.ipAddress")
})
public class Log {
  private Long id;
  private String ipAddress;
  private Date time;
  private String request;
  private Integer serverStatus;
  private String userAgent;
  private Integer logNumber;
  private List<Report> reports = new ArrayList<>();
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "log_id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  @Column(name = "ip_address")
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  
  @Column(name = "request_time")
  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }
  
  @Column(name = "request")
  public String getRequest() {
    return request;
  }

  public void setRequest(String request) {
    this.request = request;
  }

  @Column(name = "server_status")
  public Integer getServerStatus() {
    return serverStatus;
  }

  public void setServerStatus(Integer serverStatus) {
    this.serverStatus = serverStatus;
  }
  
  @Column(name = "user_agent")
  public String getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  @Column(name = "log_number")
  public Integer getLogNumber() {
    return logNumber;
  }

  public void setLogNumber(Integer logNumber) {
    this.logNumber = logNumber;
  }
  
  @ManyToMany
  @JoinTable(name = "t_log_report_detail",
          joinColumns = @JoinColumn(name = "log_id"),
          inverseJoinColumns = @JoinColumn(name = "report_id"))
  public List<Report> getReports() {
    return reports;
  }

  public void setReports(List<Report> reports) {
    this.reports = reports;
  }
    
  public String toString() {
    return String.format("Log - Id: %s, IP Address: %s, Time: %s, Log request: %s, Server status: %s, User Agent: %s, Log Number: %s", 
        id.toString(), ipAddress, time.toString(), request, serverStatus.toString(), userAgent, logNumber.toString());
  }
}
