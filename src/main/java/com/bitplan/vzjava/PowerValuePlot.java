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

import java.awt.Color;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * allows Plotting of PowerValues
 * 
 * @author wf
 *
 */
public class PowerValuePlot extends Plot {
  List<List<PowerValue>> curves = new ArrayList<List<PowerValue>>();

  @SuppressWarnings("deprecation")
  @Override
  public ColoredDataSet createDataset() {
    ColoredDataSet result = new ColoredDataSet();
    final TimeSeriesCollection dataset = new TimeSeriesCollection();
    dataset.setDomainIsPointsInTime(true);
    for (List<PowerValue> curve : curves) {
      final TimeSeries series = new TimeSeries("power", Minute.class);
      for (PowerValue powerValue : curve) {
        Date ts = powerValue.getTimeStamp();
        Minute min = new Minute(ts);
        series.addOrUpdate(min, powerValue.getValue());
      }
      dataset.addSeries(series);
    }
    result.dataSet = dataset;
    Paint[] colors = { Color.blue, new Color(0xff, 0x80, 00) };
    result.paintSequence = colors;
    return result;
  }

  /**
   * add the given List of PowerValues
   * 
   * @param powervalues
   */
  public void add(List<PowerValue> powervalues) {
    curves.add(powervalues);
  }

}
