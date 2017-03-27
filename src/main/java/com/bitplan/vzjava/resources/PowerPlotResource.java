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
package com.bitplan.vzjava.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.time.DateUtils;

import com.bitplan.vzjava.PowerValuePlotManager;

@SuppressWarnings("rawtypes")
@Path("/plot")
public class PowerPlotResource extends VZResource {
  protected static Logger LOGGER = Logger
      .getLogger("com.bitplan.vzjava.resources");

  /**
   * Power Plot Resource constructor
   */
  public PowerPlotResource() {
    super.prepareRootMap("Plot", "Plot");
  }
  
  @GET
  @Path("/range/{isoFrom}/{isoTo}/plot") 
  @Produces("image/png")
  public Response getPlotFileForRange(
      @HeaderParam("If-Modified-Since") String modified,
      @QueryParam("width") @DefaultValue("1024") int width,
      @QueryParam("height") @DefaultValue("768") int height,
      @PathParam("isoFrom") String isoFrom, @PathParam("isoTo") String isoTo)
          throws Exception {
    File pngFile=PowerValuePlotManager.getInstance().getPlot(isoFrom, isoTo, width, height);
    Response result = returnFile(pngFile, modified);
    return result;
  }
  
  @GET
  @Path("/file/{filename}") 
  public Response getPlotFileForRange(
      @HeaderParam("If-Modified-Since") String modified,
      @PathParam("filename") String filename)
          throws Exception {
    File pngFile=PowerValuePlotManager.getInstance().getPlotFile(filename);
    Response result = returnFile(pngFile, modified);
    return result;
  }

  @GET
  @Path("/range/{isoFrom}/{isoTo}")
  public Response getPlotForRange(
      @QueryParam("width") @DefaultValue("1024") int width,
      @QueryParam("height") @DefaultValue("768") int height,
      @PathParam("isoFrom") String isoFrom, @PathParam("isoTo") String isoTo)
          throws Exception {
    File pngFile=PowerValuePlotManager.getInstance().getPlot(isoFrom, isoTo, width, height);
    rootMap.put("isoFrom", isoFrom);
    rootMap.put("isoTo", isoTo);
    rootMap.put("pngFile", pngFile.getName());
    return super.templateResponse("plot.rythm");
  }

  /**
   * http://stackoverflow.com/a/10257341/1497139
   * Sends the file if modified and "not modified" if not modified
   * future work may put each file with a unique id in a separate folder in
   * tomcat
   * * use that static URL for each file
   * * if file is modified, URL of file changes
   * * -> client always fetches correct file
   * 
   * method header for calling method public Response
   * getXY(@HeaderParam("If-Modified-Since") String modified) {
   * 
   * @param file
   *          to send
   * @param modified
   *          - HeaderField "If-Modified-Since" - may be "null"
   * @return Response to be sent to the client
   */
  public static Response returnFile(File file, String modified) {
    if (!file.exists()) {
      return Response.status(Status.NOT_FOUND).build();
    }

    // do we really need to send the file or can send "not modified"?
    if (modified != null) {
      Date modifiedDate = null;

      // we have to switch the locale to ENGLISH as parseDate parses in the
      // default locale
      Locale old = Locale.getDefault();
      Locale.setDefault(Locale.ENGLISH);
      try {
        modifiedDate = DateUtils.parseDate(modified, DEFAULT_PATTERNS);
      } catch (ParseException e) {
        LOGGER.log(Level.WARNING, e.getMessage());
      }
      Locale.setDefault(old);

      if (modifiedDate != null) {
        // modifiedDate does not carry milliseconds, but fileDate does
        // therefore we have to do a range-based comparison
        // 1000 milliseconds = 1 second
        if (file.lastModified()
            - modifiedDate.getTime() < DateUtils.MILLIS_PER_SECOND) {
          return Response.status(Status.NOT_MODIFIED).build();
        }
      }
    }
    // we really need to send the file

    try {
      Date fileDate = new Date(file.lastModified());
      return Response.ok(new FileInputStream(file)).lastModified(fileDate)
          .build();
    } catch (FileNotFoundException e) {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  /**
   * Date format pattern used to parse HTTP date headers in RFC 1123 format.
   */
  public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

  /**
   * Date format pattern used to parse HTTP date headers in RFC 1036 format.
   */
  public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

  /**
   * Date format pattern used to parse HTTP date headers in ANSI C
   * <code>asctime()</code> format.
   */
  public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

  public static final String[] DEFAULT_PATTERNS = new String[] {
      PATTERN_RFC1036, PATTERN_RFC1123, PATTERN_ASCTIME };

}
