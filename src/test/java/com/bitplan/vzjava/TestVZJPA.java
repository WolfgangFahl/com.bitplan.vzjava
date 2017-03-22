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

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.bitplan.vzjava.jpa.PropertiesDao;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * test JPA access to Volkszähler DB
 * @author wf
 *
 */
public class TestVZJPA {
  boolean debug = true;

  @Test
  public void testGetProperties() {
    VZDB vz = new VZDB();
    Query query = vz.getEntityManager()
        .createQuery("select p from Properties p");
    @SuppressWarnings("unchecked")
    List<PropertiesDao> props = query.getResultList();
    assertNotNull(props);
    for (PropertiesDao prop : props) {
      if (debug)
        System.out.println(String.format("%3d %3d %s=%s", prop.getId(),
            prop.getEntity_id(), prop.getPkey(), prop.getValue()));
    }
  }
}
