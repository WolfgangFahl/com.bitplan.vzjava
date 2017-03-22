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
package com.bitplan.vzjava.rest;
import com.bitplan.rest.RestServerImpl;
import com.bitplan.rest.providers.JsonProvider;
import com.bitplan.rest.users.UserManagerImpl;

/**
 * RESTful Server for Volkszähler
 */
public class VZServer extends RestServerImpl{

  /**
   * construct VZServer
   * setting defaults
   * @throws Exception 
   */
  public VZServer() throws Exception {
    settings.setHost("0.0.0.0");
    settings.setPort(8380);
    
    settings.setContextPath("/vz".toLowerCase());
    settings.addClassPathHandler("/", "/static/");
    String packages="com.bitplan.vzjava.resources;com.bitplan.vzjava.rest;com.bitplan.rest.providers";
    JsonProvider.registerType(UserManagerImpl.class);
    settings.setPackages(packages);
  }       
       
  /**
   * start Server
   * 
   * @param args
   * @throws Exception
   */
   public static void main(String[] args) throws Exception {
     VZServer rs=new VZServer();
     rs.settings.parseArguments(args);
     rs.startWebServer();
   } // main
} // NonoletServer
