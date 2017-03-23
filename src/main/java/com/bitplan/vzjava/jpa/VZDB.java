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
	private Map<String, String> properties = new HashMap<String, String>();

	/**
	 * init
	 */
	public EntityManager getEntityManager() {
		if (em == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
			em = emf.createEntityManager();
		}
		return em;
	}
	
	/**
	 * set a property with the given key and value
	 * @param key
	 * @param value
	 */
	public void setProperty(String key,String value) {
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
