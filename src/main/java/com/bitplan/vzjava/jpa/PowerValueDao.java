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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bitplan.vzjava.PowerValueImpl;

@Entity(name = "Data")
@Table(name = "Data")
@NamedNativeQuery(name="Data.period", query="SELECT * FROM Data d where d.channel_id=?1 and d.timestamp>=?2 and d.timestamp<=?3", resultClass=PowerValueDao.class)
// timestamp between :from and :to
public class PowerValueDao extends PowerValueImpl {
  int id;
  int channel_id;

  @Id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getChannel_id() {
    return channel_id;
  }

  public void setChannel_id(int channel_id) {
    this.channel_id = channel_id;
  }
  
  public double getValue() {
    return super.getValue();
  }

  // http://stackoverflow.com/questions/811845/setting-a-jpa-timestamp-column-to-be-generated-by-the-database

  @Column(name = "timestamp", insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date getTimeStamp() {
    return super.getTimeStamp();
  }
  
}
