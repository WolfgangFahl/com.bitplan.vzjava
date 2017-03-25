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
package com.bitplan.vzjava.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.bitplan.vzjava.DBConfig;
import com.bitplan.vzjava.DBConfigImpl;

/**
 * JPA access to Volkszähler database
 * 
 * @author wf
 *
 */
public class VZDB {

  private javax.persistence.EntityManagerFactory emf;
  private javax.persistence.EntityManager em;
  private String PERSISTENCE_UNIT_NAME = "vz";
  // JPA properties
  private Map<String, String> properties = new HashMap<String, String>();
  private DBConfig dbConfig;

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }

  public DBConfig getDbConfig() {
    return dbConfig;
  }

  public void setDbConfig(DBConfig dbConfig) {
    this.dbConfig = dbConfig;
  }

  /**
   * construct the database connector
   * 
   * @param testMode
   * 
   * @throws Exception
   */
  public VZDB(String dbConfigName) throws Exception {
    init(dbConfigName);
  }

  /**
   * initialize the properties map for the given Database Configuration name
   * 
   * @param dbConfigName
   *          - the name of the database configuration
   */
  public void init(String dbConfigName) throws Exception {
    dbConfig = DBConfigImpl.getDBConfig(dbConfigName);
    if (dbConfig == null) {
      dbConfig=new DBConfigImpl();
      dbConfig.setName("demo");
      Map<String, Object> emprops = getEntityManager().getProperties();
      dbConfig.setUrl((String) emprops.get("javax.persistence.jdbc.url"));
      dbConfig.setDriver((String) emprops.get("javax.persistence.jdbc.driver"));
      if (emprops.containsKey("javax.persistence.jdbc.user")) dbConfig.setUser((String) emprops.get("javax.persistence.jdbc.user"));
      if (emprops.containsKey("javax.persistence.jdbc.password")) {
        dbConfig.setPassword((String) emprops.get("javax.persistence.jdbc.password"));
        dbConfig.setPassword2(dbConfig.getPassword());
      }
    } else {
      Map<String, String> dbConfigMap = dbConfig.asMap();
      String[] keys = { "driver", "url", "user", "password" };
      for (final String key : keys) {
        setProperty("javax.persistence.jdbc." + key, dbConfigMap.get(key));
      }
    }
  }

  /**
   * init
   */
  public EntityManager getEntityManager() {
    if (em == null) {
      emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,
          properties);
      em = emf.createEntityManager();
    }
    return em;
  }

  /**
   * set a property with the given key and value
   * 
   * @param key
   * @param value
   */
  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  /**
   * close
   */
  public void close() {
    em.close();
    em = null;
    emf.close();
    emf = null;
  }

}
