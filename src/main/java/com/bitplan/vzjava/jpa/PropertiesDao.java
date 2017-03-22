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

/**
 * Properties Table
 * @author wf
 *
 */
@Entity(name = "Properties")
@Table(name = "Properties")
public class PropertiesDao {
  int id;
  int entity_id;
  String pkey;
  String value;
  
  @Id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getEntity_id() {
    return entity_id;
  }

  public void setEntity_id(int entity_id) {
    this.entity_id = entity_id;
  }

  public String getPkey() {
    return pkey;
  }

  public void setPkey(String pkey) {
    this.pkey = pkey;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

 
}
