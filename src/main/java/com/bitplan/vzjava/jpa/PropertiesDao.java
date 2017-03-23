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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXBException;

import com.bitplan.jaxb.JaxbFactory;
import com.bitplan.jaxb.JaxbFactoryApi;
import com.bitplan.jaxb.JaxbPersistenceApi;
import com.bitplan.vzjava.Properties;
import com.bitplan.vzjava.PropertiesImpl;

/**
 * Properties Table
 * 
 * @author wf
 *
 */
@Entity(name = "Properties")
@Table(name = "Properties")
public class PropertiesDao extends PropertiesImpl implements JaxbPersistenceApi<Properties>, Properties {
	private static JaxbFactory<Properties> factory;

	@Id
	public int getId() {
		return super.getId();
	}

	@Override
	public int getEntity_id() {
		return super.getEntity_id();
	}

	@Override
	public String getPkey() {
		return super.getPkey();
	}
	
	@Override
	public String getValue() {
		return super.getValue();
	}

	public static JaxbFactoryApi<Properties> getFactoryStatic() {
		if (factory == null) {
			factory = new JaxbFactory<Properties>(PropertiesDao.class);
		}
		return factory;
	}

	public JaxbFactoryApi<Properties> getFactory() {
		return getFactoryStatic();
	}

	public String asJson() throws JAXBException {
		String json = getFactory().asJson(this);
		return json;
	}

	public String asXML() throws JAXBException {
		String xml = getFactory().asXML(this);
		return xml;
	}

}
