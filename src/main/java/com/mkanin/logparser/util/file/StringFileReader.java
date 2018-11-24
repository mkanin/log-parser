/*
 * Copyright (c) 2018 Mikhail Kanin
 */
package com.mkanin.logparser.util.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 
 * @author MikhailKanin
 *
 */
public interface StringFileReader {
  public List<String> read(String fileName) throws FileNotFoundException, IOException;
}
