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

/**
 * Properties Table
 * 
 * @author wf
 *
 */
@Entity(name = "Properties")
@Table(name = "Properties")
public class PropertiesDao implements JaxbPersistenceApi<Properties>, Properties {
	private static JaxbFactory<Properties> factory;
	int id;
	int entity_id;
	String pkey;
	String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#getId()
	 */
	@Id
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#setId(int)
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#getEntity_id()
	 */
	public int getEntity_id() {
		return entity_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#setEntity_id(int)
	 */
	public void setEntity_id(int entity_id) {
		this.entity_id = entity_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#getPkey()
	 */
	public String getPkey() {
		return pkey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#setPkey(java.lang.String)
	 */
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#getValue()
	 */
	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bitplan.vzjava.jpa.Properties#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public static JaxbFactoryApi<Properties> getFactoryStatic() {
		if (factory==null) {
			factory=new JaxbFactory<Properties>(PropertiesDao.class);
		}
		return factory;
	}
	public JaxbFactoryApi<Properties> getFactory() {
		return getFactoryStatic();
	}

	public String asJson() throws JAXBException {
		String json=getFactory().asJson(this);
		return json;
	}

	public String asXML() throws JAXBException {
		String xml=getFactory().asXML(this);
		return xml;
	}

}
