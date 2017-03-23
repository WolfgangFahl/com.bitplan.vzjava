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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import com.bitplan.vzjava.jpa.PropertiesManagerDao;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * test JPA access to Volkszähler DB
 * 
 * @author wf
 *
 */
public class TestVZJPA {
	private static PropertiesManagerDao pm;
	private static List<Properties> props;
	boolean debug = false;

	public static PropertiesManagerDao getPropertiesManager() {
		if (pm == null)
			pm = new PropertiesManagerDao();
		return pm;
	}

	public static List<Properties> getProperties() {
		if (props==null) {
		PropertiesManagerDao lpm = getPropertiesManager();
		props=lpm.getProperties(new VZDB());
		}
		return props;
	}

	@Test
	public void testGetProperties() {
		props=getProperties();
		for (Properties prop : props) {
			if (debug)
				System.out.println(String.format("%3d %3d %s=%s", prop.getId(), prop.getEntity_id(), prop.getPkey(),
						prop.getValue()));
		}
		assertEquals(56,props.size());
	}

	@Test
	public void testXml() throws JAXBException {
		props=getProperties();
		String xml=getPropertiesManager().asXML();
		if (debug) {
			System.out.println(xml);
		}
		assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<propertiesManager>\n" + 
				"   <properties>\n" + 
				"      <property>\n" + 
				"         <entity_id>1</entity_id>\n" + 
				"         <id>1</id>\n" + 
				"         <pkey>title</pkey>\n" + 
				"         <value>Haus 1.8.0 EVU Bezug</value>"));
	}
}
