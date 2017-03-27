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

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.bitplan.vzjava.jpa.VZDB;

/**
 * test the Plot handling
 * @author wf
 *
 */
public class TestVZPlot {
  
  /**
   * test a demo Plot
   * @throws Exception 
   */
  @Test
  public void testDemoPlot() throws Exception {
    PowerValuePlotManager pvpm = PowerValuePlotManager.getInstance();
    pvpm.setVzdb(new VZDB("demo"));
    // 2017-02-04 18:05/2017-02-05 04:00?width=640&height=480
    String isoFrom="2017-02-04 18:05";
    String isoTo="2017-02-05 04:00";
    int width=640;
    int height=480;
    File pngFile=pvpm.getPlot(isoFrom, isoTo, width, height);
    assertNotNull(pngFile);
  }

}
