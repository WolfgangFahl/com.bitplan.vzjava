package com.bitplan.vzjava;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * simple ErrorHandler
 * @author wf
 *
 */
public class ErrorHandler {
  protected static Logger LOGGER = Logger.getLogger("com.bitplan.nonolet");
  
  /***
   * handle the given Throwable
   * 
   * @param th
   */
  public static void handle(Throwable th) {
    LOGGER.log(Level.WARNING, "Error " + th.getClass().getName()+ ":"+ th.getMessage());
    StringWriter sw = new StringWriter();
    th.printStackTrace(new PrintWriter(sw));
    LOGGER.log(Level.WARNING, "Stacktrace: " + sw.toString());
  }

  /**
   * issue a warning
   * @param msg
   */
  public static void warn(String msg) {
    LOGGER.log(Level.WARNING,msg);
  }
}
