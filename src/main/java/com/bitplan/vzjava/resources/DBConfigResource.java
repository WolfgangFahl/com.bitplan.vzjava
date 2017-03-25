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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.bitplan.vzjava.DBConfig;
import com.bitplan.vzjava.DBConfigImpl;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * Jersey Resource for Configuration
 */
@Path("/dbconfig")
public class DBConfigResource extends VZResource<DBConfig, DBConfig> {

  /**
   * constructor
   */
  public DBConfigResource() {
    super.prepareRootMap("Datenbank-Einstellungen", "Datenbank-Einstellungen");
  }

  @GET
  @Path("{configname}")
  public Response showConfig(@PathParam("configname") String configname)
      throws Exception {
    VZDB vzdb = new VZDB(configname);
    rootMap.put("dc", vzdb.getDbConfig());
    Response response = super.templateResponse("dbconfig.rythm");
    return response;
  }

  @GET
  public Response showConfig() throws Exception {
    String configname = DBConfigImpl.getConfigName();
    return showConfig(configname);
  }

  /**
   * get the Database Configuration from the Form
   * 
   * @param formParams
   * @return
   */
  public DBConfig fromForm(MultivaluedMap<String, String> formParams) {
    debug = true;
    Map<String, String> dbConfigMap = super.asMap(formParams);
    DBConfigImpl dbConfig = new DBConfigImpl();
    dbConfig.fromMap(dbConfigMap);
    rootMap.put("dc", dbConfig);
    return dbConfig;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces({ "text/html" })
  @Path("{configname}")
  public Response workOnConfigFromPost(@PathParam("name") String configname,
      MultivaluedMap<String, String> formParams) {
    DBConfig dbConfig = fromForm(formParams);
    String cmd = formParams.getFirst("cmd");
    if ("save".equals(cmd) || "test".equals(cmd)) {
      if (!dbConfig.getPassword().equals(dbConfig.getPassword2())) {
        rootMap.put("error", true);
        rootMap.put("message", "check passwords");
      }
    }
    if (!rootMap.containsKey("error")) {
      if ("save".equals(cmd)) {
        try {
          dbConfig.save();
          rootMap.put("error", false);
          rootMap.put("message", "saved");
        } catch (Throwable th) {
          rootMap.put("error", true);
          rootMap.put("message", th.getMessage());
        }
      } else if ("test".equals(cmd)) {
        try {
          dbConfig.testConnection();
          rootMap.put("error", false);
          rootMap.put("message", "ok");
        } catch (Throwable th) {
          rootMap.put("error", true);
          rootMap.put("message", th.getMessage());
        }
      }
    }
    Response response = super.templateResponse("dbconfig.rythm");
    return response;
  }

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces({ "text/html" })
  public Response workOnConfigFromPost(
      MultivaluedMap<String, String> formParams) {
    String configname = formParams.getFirst("name");
    return this.workOnConfigFromPost(configname, formParams);
  }

} // DBConfigResource
