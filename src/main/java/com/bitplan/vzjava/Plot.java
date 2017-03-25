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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;

/**
 * general Plot
 * @author wf
 *
 */
public abstract class Plot {
  protected static Logger LOGGER = Logger.getLogger("com.bitplan.vzjava");
  public final boolean debug = false;
  protected String title;
  protected JFrame frame;
  protected ColoredDataSet dataset;
  protected JFreeChart chart = null;
  protected ChartPanel chartPanel;
  
  /**
   * dataset + paintSequence
   * 
   * @author wf
   *
   */
  public class ColoredDataSet {
    public XYDataset dataSet;
    public Paint[] paintSequence;
  }
  
  /**
   * get the Chart
   * 
   * @return
   */
  public JFreeChart getChart() {
    if (chart == null) {
      dataset = createDataset();
      chart = createChart(dataset);
    }
    return chart;
  }
  
  /**
   * Creates a chart.
   * 
   * @param dataset
   *          a dataset.
   * 
   * @return A chart.
   */
  private JFreeChart createChart(final ColoredDataSet cdataset) {

    final JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Time",
        "Power", cdataset.dataSet, true, true, false);

    chart.setBackgroundPaint(Color.white);

    // final StandardLegend sl = (StandardLegend) chart.getLegend();
    // sl.setDisplaySeriesShapes(true);

    final XYPlot plot = chart.getXYPlot();
    // plot.setOutlinePaint(null);
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);
    // plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(false);

    final XYItemRenderer renderer = plot.getRenderer();
    if (renderer instanceof StandardXYItemRenderer) {
      renderer.setSeriesStroke(0, new BasicStroke(3.0f));
      renderer.setSeriesStroke(1, new BasicStroke(3.0f));
    }

    final DateAxis axis = (DateAxis) plot.getDomainAxis();
    axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));

    // http://www.jfree.org/jfreechart/api/gjdoc/org/jfree/chart/plot/DefaultDrawingSupplier-source.html
    DrawingSupplier supplier = new DefaultDrawingSupplier(
        cdataset.paintSequence,
        DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
        DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
    chart.getPlot().setDrawingSupplier(supplier);
    return chart;
  }
  
  public abstract ColoredDataSet createDataset();
 
  /**
   * add a value to the given time series
   * 
   * @param series
   * @param ts
   * @param value
   */
  public void add(TimeSeries series, Date ts, Double value) {
    Minute min = new Minute(ts);
    series.addOrUpdate(min, value);
  }

  /**
   * get the Panel
   * 
   * @return
   */
  public ChartPanel getPanel() {
    chartPanel = new ChartPanel(getChart());
    return chartPanel;
  }
  
  /**
   * show me
   * 
   * @return
   */
  public JFrame show() {
    frame = new JFrame(title);
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.getContentPane().add(getPanel(), BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    return frame;
  }

  public void waitVisible() throws InterruptedException {
    while (chartPanel == null || (!chartPanel.isVisible())) {
      Thread.sleep(50);
    }
  }

  /**
   * wait for close
   * 
   * @throws InterruptedException
   */
  public void waitClose() throws InterruptedException {
    while (frame != null && frame.isVisible()) {
      Thread.sleep(50);
    }

  }

  /**
   * save me as a png file
   * 
   * @param file
   * @param width
   * @param height
   * @throws Exception
   */
  public void saveAsPng(File file, int width, int height) throws Exception {
    FileOutputStream outputstream = new FileOutputStream(file);
    ChartUtilities.writeChartAsPNG(outputstream, getChart(), width, height);
    outputstream.close();
  }

}
