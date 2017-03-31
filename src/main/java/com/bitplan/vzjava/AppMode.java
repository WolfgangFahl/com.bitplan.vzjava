package com.bitplan.vzjava;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * check the mode of operation
 * 
 * @author wf
 *
 */
public class AppMode {
  public static enum Mode {
    Welcome, Demo, Operation
  };

  static File demoDb = new File("src/test/data/vzdb/vztest.db");
  static File demoDbRelease = new File("src/test/data/vzdb/vztest.db.release");

  /**
   * get the mode of operation
   * 
   * @return the current mode of operation
   */
  public static Mode getMode() {
    if (!demoDb.exists()) {
      return Mode.Welcome;
    } else {
      if ("demo".equals(DBConfigImpl.getConfigName())) {
        return Mode.Demo;
      }
    }
    return Mode.Operation;
  }

  /**
   * set the given mode of operation
   * 
   * @param mode
   *          - the mode to set
   * @throws Exception 
   */
  public static void setMode(Mode mode) throws Exception {
    switch (mode) {
    case Welcome:
      demoDb.delete();
      break;
    case Demo:
      FileUtils.copyFile(demoDbRelease, demoDb);
      break;
    }
  }
}
