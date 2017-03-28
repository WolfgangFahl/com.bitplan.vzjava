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
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitplan.vzjava.jpa.PowerValueManagerDao;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * manages PowerValue Plots by caching
 * 
 * @author wf
 *
 */
public class PowerValuePlotManager {
  java.nio.file.Path tmpDir = null;
  static PowerValuePlotManager instance;
  Map<String, File> plotFileCacheBySignature = new HashMap<String, File>();
  Map<String, File> plotFileCacheByName = new HashMap<String, File>();
  VZDB vzdb;

  public VZDB getVzdb() throws Exception {
    if (vzdb == null)
      vzdb = new VZDB();
    return vzdb;
  }

  public void setVzdb(VZDB vzdb) {
    this.vzdb = vzdb;
  }

  /**
   * private constructor to force instance handling
   */
  protected PowerValuePlotManager() {

  }

  /**
   * singleton access
   * 
   * @return my instance
   */
  public static PowerValuePlotManager getInstance() {
    if (instance == null) {
      instance = new PowerValuePlotManager();
    }
    return instance;
  }

  /**
   * get a temporary Directory
   * 
   * @return
   * @throws Exception
   */
  public java.nio.file.Path getTempDirectory() throws Exception {
    if (tmpDir == null)
      tmpDir = Files.createTempDirectory("powerRange");
    return tmpDir;
  }

  /**
   * get the plot File
   * 
   * @param channelNos
   * @param isoFrom
   * @param isoTo
   * @param width
   * @param height
   * @throws Exception
   */
  public File getPlot(String channelNos, String isoFrom, String isoTo,
      int width, int height) throws Exception {
    String signature = String.format("%s %s-%s %4d-%4d", channelNos, isoFrom,
        isoTo, width, height);
    // check cache
    if (this.plotFileCacheBySignature.containsKey(signature)) {
      // if the file is in the cache
      File plotFile = plotFileCacheBySignature.get(signature);
      // and if it is accessible
      if (plotFile.canRead()) {
        return plotFile;
      }
    }
    PowerValuePlot pvplot = new PowerValuePlot();
    PowerValueManagerDao pvm = PowerValueManagerDao
        .getVZInstance(this.getVzdb());
    for (String channelNo : channelNos.split(";")) {
      // FIXME - get from Channel Info
      PowerValue.ChannelMode channelMode = PowerValue.ChannelMode.Power;
      List<PowerValue> powervalues = pvm.get(isoFrom, isoTo,
          Integer.parseInt(channelNo), channelMode);
      // FIXME add title
      pvplot.add(powervalues);
    }
    File pngFile = Files
        .createTempFile(getTempDirectory(), "powerRange", ".png").toFile();
    pvplot.saveAsPng(pngFile, width, height);
    plotFileCacheBySignature.put(signature, pngFile);
    plotFileCacheByName.put(pngFile.getName(), pngFile);
    return pngFile;
  }

  /**
   * get the file with the given filename from the cache
   * 
   * @param filename
   * @return
   */
  public File getPlotFile(String filename) {
    File pngFile = plotFileCacheByName.get(filename);
    return pngFile;
  }

}
