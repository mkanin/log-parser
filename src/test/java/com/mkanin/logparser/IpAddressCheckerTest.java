/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser;

import org.junit.Test;

import com.mkanin.logparser.util.checker.IpAddressChecker;
import com.mkanin.logparser.util.checker.IpAddressCheckerImpl;

import junit.framework.Assert;

/**
 * 
 * @author MikhailKanin
 *
 */
public class IpAddressCheckerTest {
  private IpAddressChecker ipAddressChecker;
  
  public IpAddressCheckerTest() {
    this.ipAddressChecker = new IpAddressCheckerImpl();
  }
  
  @Test
  public void checkIp1() {
    String ip = "8.8.8.8";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, true);
  }
  
  @Test
  public void checkIp2() {
    String ip = "8.8.4.4";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, true);
  }
  
  @Test
  public void checkIp3() {
    String ip = "256.0.0.0";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, false);
  }
  
  @Test
  public void checkIp4() {
    String ip = "192.256.0.1";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, false);
  }
  
  @Test
  public void checkIp5() {
    String ip = "192.168.256.3";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, false);
  }
  
  @Test
  public void checkIp6() {
    String ip = "192.168.5.256";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, false);
  }
  
  @Test
  public void checkIp7() {
    String ip = "192.168.1.5";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, true);
  }
  
  @Test
  public void checkIp8() {
    String ip = "192.168.0.8";
    
    boolean check = ipAddressChecker.check(ip);
    
    Assert.assertEquals(check, true);
  }
}
