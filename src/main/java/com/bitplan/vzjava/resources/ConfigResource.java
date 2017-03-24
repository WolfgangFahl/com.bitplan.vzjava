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

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.bitplan.vzjava.jpa.VZDB;

/**
 * Jersey Resource for Configuration
 */
@SuppressWarnings("rawtypes")
@Path("/config")
public class ConfigResource extends VZResource {

  /**
   * constructor
   */
  public ConfigResource() {
    super.prepareRootMap("Einstellungen", "Einstellungen");
  }

  @GET
  public Response showConfig() throws Exception {
    VZDB vzdb = new VZDB(false);
    Map<String, String> props = vzdb.getProperties();
    for (String key : props.keySet()) {
      if (key.startsWith("javax.persistence.jdbc.")) {
        String formkey = key.replace("javax.persistence.jdbc.", "");
        rootMap.put(formkey, props.get(key));
      }
    }
    Response response = super.templateResponse("config.rythm");
    return response;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces({ "text/html" })
  public Response modifyConfigFromPost(
      MultivaluedMap<String, String> formParams) {
    for (String key:formParams.keySet()) {
      rootMap.put(key, formParams.getFirst(key));
    }
    Response response = super.templateResponse("config.rythm");
    return response;
  }

} // ConfigResource
