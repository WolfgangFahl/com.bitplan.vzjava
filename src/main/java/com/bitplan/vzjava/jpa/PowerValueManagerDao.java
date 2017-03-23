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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.bitplan.jaxb.JaxbFactory;
import com.bitplan.jaxb.JaxbFactoryApi;
import com.bitplan.jaxb.ManagerImpl;
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
public class PowerValueManagerDao extends ManagerImpl<PowerValueManager, PowerValue> implements PowerValueManager {
	private static final double POWER_LIMIT = 15000; // powervalues over this
														// value are artefacts
	private static JaxbFactory<PowerValueManager> factory;
	List<PowerValue> powerValues = new ArrayList<PowerValue>();

	@XmlElementWrapper(name = "powerValues")
	@XmlElement(name = "powerValue")
	public List<PowerValue> getPowerValues() {
		return powerValues;
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
	 * get the values
	 * 
	 * @param from
	 * @param to
	 * @param channel
	 * @return
	 */
	public List<PowerValue> get(VZDB vz, Calendar from, Calendar to, int channel, PowerValue.ChannelMode channelMode) {
		List<PowerValue> result = null;
		switch (channelMode) {
		case Power:
			result = getPower(vz, from, to, channel);
			break;
		case Counter:
			result = getPowerFromCounter(vz, from, to, channel);
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
		long msecs = chargeValue.getTimeStamp().getTime() - prevValue.getTimeStamp().getTime();
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
	public List<PowerValue> getPower(VZDB vz, Calendar from, Calendar to, int channel) {
		@SuppressWarnings("unchecked")
		TypedQuery<PowerValueDao> query = (TypedQuery<PowerValueDao>) vz.getEntityManager()
				.createNamedQuery("Data.period");
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
	 * get PowerValues from the two calendar instances
	 * 
	 * @param from
	 * @param to
	 * @param channel
	 * @return
	 */
	public List<PowerValue> getPowerFromCounter(VZDB vz, Calendar from, Calendar to, int channel) {
		List<PowerValue> powerValues = getPower(vz, from, to, channel);
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

}
