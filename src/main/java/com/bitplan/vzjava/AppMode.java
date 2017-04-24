/**
 * Copyright (c) 2017 BITPlan GmbH
 *
 * http://www.bitplan.com
 *
 * This file is part of the Opensource project at:
 * https://github.com/WolfgangFahl/com.bitplan.vzjava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
  /**
   * Welcome= first start
   * Demo = use demo database for next start of application
   * Test = use demo database once
   * Operation = use configured database
   */
  public static enum Mode {
    Welcome, Demo, Test, Operation
  };

  static File demoDb = new File("src/test/data/vzdb/vzdemo.db");
  static File testDb = new File("src/test/data/vzdb/vztest.db");
  static File demoDbRelease = new File("src/test/data/vzdb/vztest.db.release");

  /**
   * get the mode of operation
   * 
   * @return the current mode of operation
   */
  public static Mode getMode() {
    if (!demoDb.exists()) {
      return Mode.Welcome;
    } else if (testDb.exists()) {
      return Mode.Test;
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
   */
  public static void setMode(Mode mode) {
    try {
      switch (mode) {
      case Welcome:
        demoDb.delete();
        break;
      case Demo:
        testDb.delete();
        FileUtils.copyFile(demoDbRelease, demoDb);
        break;
      case Test:
        demoDb.delete();
        FileUtils.copyFile(demoDbRelease, testDb);
      case Operation:
        break;
      default:
        break;
      }
    } catch (IOException e) {
       ErrorHandler.handle(e);
    }
  }
}
