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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXBException;

import com.bitplan.jaxb.JaxbFactory;
import com.bitplan.jaxb.JaxbFactoryApi;
import com.bitplan.jaxb.JaxbPersistenceApi;
import com.bitplan.vzjava.Entities;
import com.bitplan.vzjava.EntitiesImpl;

/**
 * Entities Table
 * 
 * @author wf
 *
 */
@Entity(name = "Entities")
@Table(name = "Entities")
public class EntitiesDao extends EntitiesImpl implements JaxbPersistenceApi<Entities>, Entities {
	private static JaxbFactory<Entities> factory;

	@Id
	public int getId() {
		return super.getId();
	}

	@Override
	public String getUuid() {
		return super.getUuid();
	}

	@Override
	public String getType() {
		return super.getType();
	}
	
	@Override
	@Column(name = "class")
	public String getEclass() {
		return super.getEclass();
	}

	public static JaxbFactoryApi<Entities> getFactoryStatic() {
		if (factory == null) {
			factory = new JaxbFactory<Entities>(EntitiesDao.class);
		}
		return factory;
	}

	public JaxbFactoryApi<Entities> getFactory() {
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
