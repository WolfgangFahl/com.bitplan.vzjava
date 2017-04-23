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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.bitplan.datatypes.DefaultTypeConverter;
import com.bitplan.datatypes.TypeConverter;
import com.bitplan.jaxb.JaxbFactory;
import com.bitplan.jaxb.JaxbFactoryApi;
import com.bitplan.jaxb.ManagerImpl;
import com.bitplan.vzjava.Entities;
import com.bitplan.vzjava.PowerValue;
import com.bitplan.vzjava.PowerValueImpl;
import com.bitplan.vzjava.PowerValueManager;

/**
 * manager for PowerValue
 * 
 * @author wf
 *
 */
@XmlRootElement(name = "powerValuesManager")
public class PowerValueManagerDao extends
    ManagerImpl<PowerValueManager, PowerValue> implements PowerValueManager {
  private static final double POWER_LIMIT = 15000; // powervalues over this
  // value are artefacts

  static SimpleDateFormat isoDateFormatter = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");

  private static JaxbFactory<PowerValueManager> factory;
  transient protected VZDB vzdb;
  List<PowerValue> powerValues = new ArrayList<PowerValue>();

  // make JAXB happy
  public PowerValueManagerDao() {

  }

  /**
   * construct me with a Volkszähler Database
   * 
   * @param lvzdb
   */
  public PowerValueManagerDao(VZDB lvzdb) {
    setVzdb(lvzdb);
  }

  @XmlElementWrapper(name = "powerValues")
  @XmlElement(name = "powerValue", type = PowerValueDao.class)
  public List<PowerValue> getPowerValues() {
    return powerValues;
  }

  @XmlTransient
  public VZDB getVzdb() {
    return vzdb;
  }

  public void setVzdb(VZDB vzdb) {
    this.vzdb = vzdb;
  }

  public void setPowerValues(List<PowerValue> PowerValue) {
    this.powerValues = PowerValue;
  }

  public List<PowerValue> getElements() {
    return getPowerValues();
  }

  public static JaxbFactoryApi<PowerValueManager> getFactoryStatic() {
    if (factory == null) {
      factory = new JaxbFactory<PowerValueManager>(PowerValueManagerDao.class);
    }
    return factory;
  }

  public JaxbFactoryApi<PowerValueManager> getFactory() {
    return getFactoryStatic();
  }

  /**
   * get values base on String time stamps
   * 
   * @param vz
   * @param isoFrom
   * @param isoTo
   * @param channel
   * @param channelMode
   * @return a list of PowerValues
   * @throws Exception
   */
  public List<PowerValue> get(String isoFrom, String isoTo, int channel,
      PowerValue.ChannelMode channelMode) throws Exception {
    TypeConverter tc = new DefaultTypeConverter();
    // Date fromDate = isoDateFormatter.parse(isoFrom);
    // Date toDate = isoDateFormatter.parse(isoTo);
    Date fromDate = tc.getDate(isoFrom);
    Date toDate = tc.getDate(isoTo);
    Calendar from = Calendar.getInstance();
    from.setTime(fromDate);
    Calendar to = Calendar.getInstance();
    to.setTime(toDate);
    List<PowerValue> result = this.get(from, to, channel, channelMode);
    return result;
  }

  /**
   * get the values
   * 
   * @param from
   * @param to
   * @param channel
   * @return
   */
  public List<PowerValue> get(Calendar from, Calendar to, int channel,
      PowerValue.ChannelMode channelMode) {
    List<PowerValue> result = null;
    switch (channelMode) {
    case Power:
      result = getPower(from, to, channel);
      break;
    case Counter:
      result = getPowerFromCounter(from, to, channel);
      break;
    }
    return result;
  }

  /**
   * get the powerValue based on the difference of two charge values
   * 
   * @param chargeValue
   * @param prevValue
   * @return - the power value
   */
  public static double getPower(PowerValue chargeValue, PowerValue prevValue) {
    double z1 = chargeValue.getValue();
    double z2 = prevValue.getValue();
    long msecs = chargeValue.getTimeStamp().getTime()
        - prevValue.getTimeStamp().getTime();
    double diff = z1 - z2;
    double power = diff / (msecs / 1000.0) * 3600;
    return power;
  }

  /**
   * get power Values for the given period from the given channel
   * 
   * @param from
   * @param to
   * @param channel
   * @return
   */
  public List<PowerValue> getPower(Calendar from, Calendar to, int channel) {
    @SuppressWarnings("unchecked")
    TypedQuery<PowerValueDao> query = (TypedQuery<PowerValueDao>) vzdb
        .getEntityManager().createNamedQuery("Data.period");
    query.setParameter(1, channel);
    query.setParameter(2, from.getTime().getTime());
    query.setParameter(3, to.getTime().getTime());
    List<PowerValueDao> dataRecs = query.getResultList();
    List<PowerValue> chargeValues = new ArrayList<PowerValue>();
    for (PowerValue chargeValue : dataRecs) {
      chargeValues.add(chargeValue);
    }
    return chargeValues;
  }

  /**
   * get the current PowerValue for the given channel
   * 
   * @param channel
   * @return the PowerValue
   */
  public PowerValue getCurrent(int channel) {
    EntitiesManagerDao emd = EntitiesManagerDao.getInstance();
    emd.getEntities(vzdb);
    Entities entity = emd.getEntityByChannel(channel);
    if (entity==null)
      throw new RuntimeException("entity is null for channel "+channel);
    @SuppressWarnings("unchecked")
    TypedQuery<PowerValueDao> query = (TypedQuery<PowerValueDao>) vzdb
        .getEntityManager().createNamedQuery("Data.current");
    query.setParameter(1, channel);
    PowerValue powerValue = null;
    if ("powersensor".equals(entity.getType())) {
      query.setMaxResults(1);
      powerValue = query.getSingleResult();
    } else {
      query.setMaxResults(2);
      List<PowerValueDao> currentPowerValues = query.getResultList();
      PowerValueDao currentValue = currentPowerValues.get(0);
      PowerValueDao prevValue = currentPowerValues.get(1);
      powerValue=new PowerValueImpl();
      powerValue.setTimeStamp(currentValue.getTimeStamp());
      powerValue.setValue(getPower(currentValue,prevValue));
    }
    return powerValue;
  }

  /**
   * get PowerValues from the two calendar instances
   * 
   * @param from
   * @param to
   * @param channel
   * @return
   */
  public List<PowerValue> getPowerFromCounter(Calendar from, Calendar to,
      int channel) {
    List<PowerValue> powerValues = getPower(from, to, channel);
    List<PowerValue> result = new ArrayList<PowerValue>();
    PowerValue prevValue = null;
    for (PowerValue chargeValue : powerValues) {
      if (prevValue != null) {
        double power = getPower(chargeValue, prevValue);
        if (power < POWER_LIMIT) {
          PowerValue cv = new PowerValueImpl();
          cv.setTimeStamp(chargeValue.getTimeStamp());
          cv.setValue(power);
          result.add(cv);
        }
      }
      prevValue = chargeValue;
    }
    return result;
  }

  /**
   * add a property to the PowerValue
   * 
   * @param prop
   */
  public void add(PowerValue prop) {
    powerValues.add(prop);
  }

  /**
   * get an instance based on the configured VZDB
   * 
   * @return
   * @throws Exception
   */
  public static PowerValueManagerDao getVZInstance() throws Exception {
    VZDB lvzdb = new VZDB();
    return getVZInstance(lvzdb);
  }

  /**
   * get an instance based on the given VZDB
   * 
   * @param pvzdb
   * @return - a PowerValueManagerDao
   * @throws Exception
   */
  public static PowerValueManagerDao getVZInstance(VZDB pvzdb)
      throws Exception {
    PowerValueManagerDao pvm = new PowerValueManagerDao(pvzdb);
    return pvm;
  }

}
