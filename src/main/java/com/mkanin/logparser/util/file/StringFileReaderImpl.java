/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author MikhailKanin
 *
 */
public class StringFileReaderImpl implements StringFileReader {
  
  @Override
  public List<String> read(String fileName)
    throws FileNotFoundException, IOException {
    List<String> logStrings = new ArrayList<>();
    
    FileReader fileReader = new FileReader(fileName);
    
    BufferedReader bufferedReader = new BufferedReader(fileReader);
    
    String line;
    while((line = bufferedReader.readLine()) != null) {
      logStrings.add(line);
    }
      
    bufferedReader.close();
    
    return logStrings;
  }
}
