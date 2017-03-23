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
import java.util.List;

import javax.persistence.Query;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.bitplan.jaxb.JaxbFactory;
import com.bitplan.jaxb.JaxbFactoryApi;
import com.bitplan.jaxb.ManagerImpl;
import com.bitplan.vzjava.Properties;
import com.bitplan.vzjava.PropertiesManager;

/**
 * manager for Properties
 * @author wf
 *
 */
@XmlRootElement(name="propertiesManager")
public class PropertiesManagerDao extends ManagerImpl<PropertiesManager,Properties> implements com.bitplan.vzjava.PropertiesManager{

	private static JaxbFactory<PropertiesManager> factory;
	List<Properties> properties=new ArrayList<Properties>();
	@XmlElementWrapper(name = "properties")
	@XmlElement(name = "property")
	public List<Properties> getProperties() {
		return properties;
	}

	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}

	public List<Properties> getElements() {
		return getProperties();
	}

	public static JaxbFactoryApi<PropertiesManager> getFactoryStatic() {
		if (factory==null) {
			factory=new JaxbFactory<PropertiesManager>(PropertiesManagerDao.class);
		}
		return factory;
	}
	public JaxbFactoryApi<PropertiesManager> getFactory() {
		return getFactoryStatic();
	}

	/**
	 * get the Properties from the Volkszähler database
	 * @param vz
	 * @return
	 */
	public List<Properties> getProperties(VZDB vz) {
		properties.clear();
		Query query = vz.getEntityManager().createQuery("select p from Properties p");
		@SuppressWarnings("unchecked")
		List<PropertiesDao> props = query.getResultList();
		for (Properties prop:props) {
			add(prop);
		}
		return getProperties();
	}

	/**
	 * add a property to the properties
	 * @param prop
	 */
	public void add(Properties prop) {
		properties.add(prop);
	}

	
}
