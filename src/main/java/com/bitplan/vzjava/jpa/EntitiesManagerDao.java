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
import com.bitplan.vzjava.Entities;
import com.bitplan.vzjava.EntitiesManager;

/**
 * manager for Entities
 * @author wf
 *
 */
@XmlRootElement(name="entitiesManager")
public class EntitiesManagerDao extends ManagerImpl<EntitiesManager,Entities> implements com.bitplan.vzjava.EntitiesManager{

	private static JaxbFactory<EntitiesManager> factory;
	List<Entities> entities=new ArrayList<Entities>();
	@XmlElementWrapper(name = "entities")
	@XmlElement(name = "entity")
	public List<Entities> getEntities() {
		return entities;
	}

	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}

	public List<Entities> getElements() {
		return getEntities();
	}

	public static JaxbFactoryApi<EntitiesManager> getFactoryStatic() {
		if (factory==null) {
			factory=new JaxbFactory<EntitiesManager>(EntitiesManagerDao.class);
		}
		return factory;
	}
	public JaxbFactoryApi<EntitiesManager> getFactory() {
		return getFactoryStatic();
	}

	/**
	 * get the Entities from the Volkszähler database
	 * @param vz
	 * @return the entities
	 */
	public List<Entities> getEntities(VZDB vz) {
		entities.clear();
		Query query = vz.getEntityManager().createQuery("select p from Entities p");
		@SuppressWarnings("unchecked")
		List<EntitiesDao> props = query.getResultList();
		for (Entities prop:props) {
			add(prop);
		}
		return getEntities();
	}

	/**
	 * add a entity to the entities
	 * @param entity
	 */
	public void add(Entities entity) {
		entities.add(entity);
	}

	
}
