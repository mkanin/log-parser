package com.mkanin.logparser.util.checker;

import java.util.regex.Pattern;

/**
 * 
 * @author MikhailKanin
 *
 */
public class IpAddressCheckerImpl implements IpAddressChecker {
  
  private final String IP_ADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
      + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
  
  @Override
  public boolean check(String ipAddress) {
    return ipAddress.matches(IP_ADDRESS_PATTERN);    
  }
}
