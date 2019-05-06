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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.InetAddress;
import java.util.List;

import org.junit.Test;

import com.bitplan.vzjava.AppMode.Mode;
import com.bitplan.vzjava.PowerValue.ChannelMode;
import com.bitplan.vzjava.jpa.PowerValueManagerDao;
import com.bitplan.vzjava.jpa.VZDB;

import ar.com.hjg.pngj.PngReader;

/**
 * test the Plot handling
 * 
 * @author wf
 *
 */
public class TestVZPlot {

  /**
   * test a demo Plot
   * 
   * @throws Exception
   */
  @Test
  public void testDemoPlot() throws Exception {
    AppMode.setMode(Mode.Test);
    PowerValuePlotManager pvpm = PowerValuePlotManager.getInstance();
    pvpm.setVzdb(new VZDB("demo"));
    // 2017-02-04 18:05/2017-02-05 04:00?width=640&height=480
    String isoFrom = "2017-02-04 18:05";
    String isoTo = "2017-02-05 04:00";
    int width = 640;
    int height = 480;
    File pngFile = pvpm.getPlot("4", isoFrom, isoTo, width, height);
    assertNotNull(pngFile);
    File cacheFile = pvpm.getPlotFile(pngFile.getName());
    assertEquals(cacheFile.getAbsolutePath(), pngFile.getAbsolutePath());
    PngReader pngr = new PngReader(cacheFile);
    assertEquals(width, pngr.imgInfo.cols);
    assertEquals(height, pngr.imgInfo.rows);
    pngr.close();
  }

  @Test
  public void testData() throws Exception {
    String host = InetAddress.getLocalHost().getHostName();
    if (host.contains(".bitplan.com")) {
      VZDB vzdb = new VZDB("vz");
      PowerValueManagerDao pvm = PowerValueManagerDao.getVZInstance(vzdb);
      String isoFrom = "2019-05-06 09:30";
      String isoTo = "2019-05-06 14:30";
      int channel = 5;
      ChannelMode channelMode = ChannelMode.Counter;
      List<PowerValue> powerValues = pvm.get(isoFrom, isoTo, channel,
          channelMode);
      assertEquals(182, powerValues.size());
      double min = Double.MAX_VALUE;
      double max = Double.MIN_VALUE;
      for (PowerValue powerValue : powerValues) {
        min = Math.min(powerValue.getValue(), min);
        max = Math.max(powerValue.getValue(), max);
      }
      boolean debug = true;
      if (debug)
        System.out.println(String.format("%s: min: %5.0f W max: %5.0f W",host, min, max));
      assertEquals(min, 0, 0.001);
      assertEquals(max, 4447, 0.1);
    }

  }

}
