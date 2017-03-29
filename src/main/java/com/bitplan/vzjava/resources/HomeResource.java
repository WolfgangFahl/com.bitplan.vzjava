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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.bitplan.vzjava.Channel;

/**
 * Jersey Resource for Home
 */
@SuppressWarnings("rawtypes")
@Path("/home")
public class HomeResource extends VZResource {
  String isoFrom;
  String isoTo;
  String channels;
  String width;
  String height;

  /**
   * constructor
   */
  public HomeResource() {
    super.prepareRootMap("Home", "home");
  }

  @GET
  public Response showHome() throws Exception {
    // FIXME - allow modification
    isoFrom = "2017-02-04 18:05";
    isoTo = "2017-02-05 04:00";
    channels="Haus you Leistung";
    rootMap.put("channels", channels);
    rootMap.put("isoFrom", isoFrom);
    rootMap.put("isoTo", isoTo);
    rootMap.put("width","1920");
    rootMap.put("height","1080");
    return channelPlotResponse();
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces({ "text/html" })
  public Response homePost(MultivaluedMap<String, String> formParams) throws Exception {
    super.formToMap(formParams);
    return channelPlotResponse();
  }

  /**
   * generic channel Plot response
   * 
   * @return - the response for showing channels
   * @throws Exception
   */
  public Response channelPlotResponse() throws Exception {
    rootMap.put("available_channels", Channel.getChannels());
    Response response = super.templateResponse("home.rythm");
    return response;
  }

} // HomeResource
