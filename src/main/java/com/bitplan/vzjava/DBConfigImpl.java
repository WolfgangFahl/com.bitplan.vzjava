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
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.bitplan.datatypes.DefaultTypeConverter;
import com.bitplan.datatypes.TypeConverter;

/**
 * Database Configuration
 * 
 * @author wf
 *
 */
public class DBConfigImpl implements DBConfig {
  String name;
  String driver;
  String url;
  String user;
  String password;
  String password2;
  transient TypeConverter tc = new DefaultTypeConverter();

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getName()
   */

  public String getName() {
    return name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setName(java.lang.String)
   */

  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getDriver()
   */

  public String getDriver() {
    return driver;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setDriver(java.lang.String)
   */

  public void setDriver(String driver) {
    this.driver = driver;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getUrl()
   */

  public String getUrl() {
    return url;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setUrl(java.lang.String)
   */

  public void setUrl(String url) {
    this.url = url;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getUser()
   */

  public String getUser() {
    return user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setUser(java.lang.String)
   */

  public void setUser(String user) {
    this.user = user;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getPassword()
   */

  public String getPassword() {
    return password;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setPassword(java.lang.String)
   */

  public void setPassword(String password) {
    this.password = password;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#getPassword2()
   */

  public String getPassword2() {
    return password2;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#setPassword2(java.lang.String)
   */

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  /**
   * get the Database Properties for the given database Configuration name
   * 
   * @param DBConfigName
   * @return the properties
   */
  public static DBConfig getDBConfig(String dbConfigName) {
    DBConfigImpl dbConfig = null;
    // Java properties
    Properties jproperties = new Properties();
    String propertyFilesName = System.getProperty("user.home") + "/.vzdb_"+dbConfigName+".ini";
    File propertyFile = new File(propertyFilesName);
    if (propertyFile.exists()) {
      try {
        dbConfig = new DBConfigImpl();
        jproperties.load(new FileInputStream(propertyFile));
        Map<String,String> propMap=new HashMap<String,String>();
        for (String key:jproperties.stringPropertyNames()) {
          propMap.put(key, jproperties.getProperty(key));
        }
        dbConfig.fromMap(propMap);
      } catch (Exception e) {
      }
    }
    return dbConfig;
  }
  
  /**
   * save me as an ini file
   */
  public void save() {
    
  }

  /**
   * get my values from the given map
   */
  public void fromMap(Map<String, String> map) {
    if (map.containsKey("name"))
      this.setName(tc.getString(map.get("name")));
    else
      this.setName(null);
    if (map.containsKey("driver"))
      this.setDriver(tc.getString(map.get("driver")));
    else
      this.setDriver(null);
    if (map.containsKey("url"))
      this.setUrl(tc.getString(map.get("url")));
    else
      this.setUrl(null);
    if (map.containsKey("user"))
      this.setUser(tc.getString(map.get("user")));
    else
      this.setUser(null);
    if (map.containsKey("password"))
      this.setPassword(tc.getString(map.get("password")));
    else
      this.setPassword(null);
    if (map.containsKey("password2"))
      this.setPassword2(tc.getString(map.get("password2")));
    else
      this.setPassword2(null);
  }

  /**
   * return my values a a  map
   */
  public Map<String, String> asMap() {
    Map<String, String> result = new HashMap<String, String>();
    result.put("name", tc.fromNullValue(this.getName()));
    result.put("driver", tc.fromNullValue(this.getDriver()));
    result.put("url", tc.fromNullValue(this.getUrl()));
    result.put("user", tc.fromNullValue(this.getUser()));
    result.put("password", tc.fromNullValue(this.getPassword()));
    result.put("password2", tc.fromNullValue(this.getPassword2()));
    return result;
  }

  public void setTypeConverter(TypeConverter typeConverter) {
    tc = typeConverter;
  }

  public TypeConverter getTypeConverter() {
    return tc;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.bitplan.vzjava.DBConfig#testConnection()
   */
  public Properties testConnection() throws Exception {
    // http://stackoverflow.com/questions/18756113/how-to-test-connection-to-oracle-database-using-java
    Class.forName(driver);
    Connection conn=DriverManager.getConnection(url,user,password);
    Properties clientInfo = conn.getClientInfo();
    conn.close();
    return clientInfo;
  }
}
