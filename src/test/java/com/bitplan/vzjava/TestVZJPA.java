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

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.bitplan.vzjava.PowerValue.ChannelMode;
import com.bitplan.vzjava.jpa.EntitiesManagerDao;
import com.bitplan.vzjava.jpa.PowerValueManagerDao;
import com.bitplan.vzjava.jpa.PropertiesManagerDao;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * test JPA access to Volkszähler DB
 * 
 * @author wf
 *
 */
public class TestVZJPA {
  protected static Logger LOGGER = Logger.getLogger("com.bitplan.vzjava");

  private static PropertiesManagerDao pm;
  private static List<Properties> props;
  boolean debug = false;
  static VZDB vzdb;

  /**
   * get the demo VZDB
   * @return
   * @throws Exception
   */
  public static VZDB getDemoVZ() throws Exception {
    if (vzdb == null) {
      vzdb = new VZDB("demo");
    }
    return vzdb;
  }

  /**
   * get the properties ManagerDao
   * 
   * @return
   */
  public static PropertiesManagerDao getPropertiesManager() {
    if (pm == null)
      pm = new PropertiesManagerDao();
    return pm;
  }

  /**
   * get all Properties
   * 
   * @return the Properties
   * @throws Exception
   */
  public static List<Properties> getProperties() throws Exception {
    if (props == null) {
      PropertiesManagerDao lpm = getPropertiesManager();
      props = lpm.getProperties(getDemoVZ());
    }
    return props;
  }

  /**
   * test getting all properties from the test database
   * 
   * @throws Exception
   */
  @Test
  public void testGetProperties() throws Exception {
    props = getProperties();
    for (Properties prop : props) {
      if (debug)
        System.out.println(String.format("%3d %3d %s=%s", prop.getId(),
            prop.getEntity_id(), prop.getPkey(), prop.getValue()));
    }
    // the number of properties in the test database is exactly 56
    assertEquals(56, props.size());
  }

  @Test
  public void testGetEntities() throws Exception {
    EntitiesManagerDao em = new EntitiesManagerDao();
    List<Entities> entities = em.getEntities(getDemoVZ());
    assertEquals(7,entities.size());
  }

  /***
   * test getting the properties in XML format
   * 
   * @throws Exception
   */
  @Test
  public void testAsXml() throws Exception {
    props = getProperties();
    String xml = getPropertiesManager().asXML();
    if (debug) {
      System.out.println(xml);
    }
    assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<propertiesManager>\n" + "   <properties>\n" + "      <property>\n"
        + "         <entity_id>1</entity_id>\n" + "         <id>1</id>\n"
        + "         <pkey>title</pkey>\n"
        + "         <value>Haus 1.8.0 EVU Bezug</value>"));
  }

  @Test
  public void testDBConfig() throws Exception {
    VZDB vzdb = new VZDB("demo");
    debug = false;
    // set to true if you'd like to see unencrypted passwords in debug mode
    boolean showPasswords = true;
    /*
     * EntityManager em = vzdb.getEntityManager();
     * Map<String, Object> emprops = em.getProperties();
     * if (debug) {
     * for (String key : emprops.keySet()) {
     * String value = (String) emprops.get(key);
     * if (!key.contains("password") || showPasswords)
     * LOGGER.log(Level.INFO, "entity manager property "+key + "=" + value);
     * }
     * }
     */
    DBConfig dbConfig = vzdb.getDbConfig();
    Map<String, String> fields = dbConfig.asMap();
    if (debug) {
      for (String key : fields.keySet()) {
        String value = fields.get(key);
        if (!key.contains("password") || showPasswords)
          LOGGER.log(Level.INFO, "dbConfig " + key + "=" + value);
      }
    }
    dbConfig = new DBConfigImpl();
    dbConfig.setName("xyzNeverUsed4711-z");
    dbConfig.setDriver("invalid Driver");
    try {
      dbConfig.testConnection();
      fail("invalid Driver should throw an exception");
    } catch (ClassNotFoundException cfe) {
      assertEquals("invalid Driver", cfe.getMessage());
    }
  }

  // @Ignore
  /**
   * test importing power VAlues in XML Format
   * 
   * @throws Exception
   */
  // https://github.com/WolfgangFahl/com.bitplan.vzjava/issues/5
  @Test
  public void testImportXml() throws Exception {
    // open the testdatabase
    VZDB vzdb = new VZDB("demo");
    // get the power values from the XML file
    File powerValueXmlFile = new File("src/test/data/vzdb/powervalues.xml");
    String xml = FileUtils.readFileToString(powerValueXmlFile);
    PowerValueManagerDao pvm = (PowerValueManagerDao) PowerValueManagerDao
        .getFactoryStatic().fromXML(xml);
    List<PowerValue> powerValues = pvm.getElements();
    // there should be 74669 power values in this test set
    assertEquals("xml import should have new Color(0x of records", 74669,
        powerValues.size());
    // delete existing data from the test database
    EntityManager em = vzdb.getEntityManager();
    em.getTransaction().begin();
    Query dquery = em.createNativeQuery("delete from data");
    dquery.executeUpdate();
    em.getTransaction().commit();

    String from = "2017-01-31 20:00:00";
    String to = "2017-03-24 14:00:00";
    em.getTransaction().begin();
    for (PowerValue powerValue : powerValues) {
      em.persist(powerValue);
    }
    em.getTransaction().commit();
    int channel = 4;
    ChannelMode channelMode = ChannelMode.Power;
    pvm.setVzdb(vzdb);
    List<PowerValue> dbPowerValues = pvm.get(from, to, channel, channelMode);
    assertTrue(String.format(
        "database should have more than 74400 of imported records but has %5d",
        dbPowerValues.size()), dbPowerValues.size() > 74400);
  }
  
  @Test
  public void testChannels() throws Exception {
    Paint[] expected={new Color(0xcc,0x00,0x00),
        new Color(0xFF,0x80,0x00),
        new Color(0x00,0x00,0x99),
        new Color(0x00,0x00,0x00),
        new Color(0x99,0x33,0x00),
        new Color(0x00,0xFF,0x00),
        new Color(0x00,0x99,0x99)};
    Channel.vzdb=getDemoVZ();
    int index=0;
    List<Channel> channels = Channel.getChannels();
    assertEquals(7,channels.size());
    for (Channel channel:channels) {
      assertNotNull(channel.getTitle());
      assertEquals("color "+index,expected[index],channel.getColor());
      index++;
    }
  }
}
