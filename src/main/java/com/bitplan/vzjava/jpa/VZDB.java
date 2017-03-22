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

import javax.persistence.EntityManager;

/**
 * JPA access to Volkszähler database
 * @author wf
 *
 */
public class VZDB {
  private javax.persistence.EntityManagerFactory emf;
  private javax.persistence.EntityManager em;
  private String PERSISTENCE_UNIT_NAME = "vz";
  
  /**
   * init
   */
  public EntityManager getEntityManager() {
    if (em == null) {
      emf = javax.persistence.Persistence
          .createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
      em = emf.createEntityManager();
    }
    return em;
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
