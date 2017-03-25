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

import java.util.Properties;

import com.bitplan.persistence.Postable;

/**
 * Database configuration
 * @author wf
 *
 */
public interface DBConfig extends Postable<DBConfig> {
  String getName();

  void setName(String name);

  String getDriver();

  void setDriver(String driver);

  String getUrl();

  void setUrl(String url);

  String getUser();

  void setUser(String user);

  String getPassword();

  void setPassword(String password);

  String getPassword2();

  void setPassword2(String password2);

  /**
   * test the connection and return the clientInfo for it
   * @return the client Info properties
   * @throws Exception
   */
  Properties testConnection() throws Exception;
  
  /**
   * save my settings
   * @throws Exception 
   */
  void save() throws Exception;
}
