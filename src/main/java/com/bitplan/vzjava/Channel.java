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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bitplan.vzjava.jpa.EntitiesManagerDao;
import com.bitplan.vzjava.jpa.PropertiesManagerDao;
import com.bitplan.vzjava.jpa.VZDB;

/**
 * domain logical access to channels
 * hides the technical details of properties and entities
 * 
 * @author wf
 *
 */
public class Channel {
  protected static VZDB vzdb;

  protected static Map<String, Channel> channelsByTitle = new HashMap<String, Channel>();
  Map<String, Properties> properties = new HashMap<String, Properties>();
  Entities entity;
  
  int no;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  /**
   * get the available channels of the system
   * 
   * @return - the list of channels
   * @throws Exception
   */
  public static List<Channel> getChannels() throws Exception {
    List<Channel> channels = new ArrayList<Channel>();
    if (vzdb == null)
      vzdb = new VZDB();
    PropertiesManagerDao pm = new PropertiesManagerDao();
    List<Properties> allproperties = pm.getProperties(vzdb);
    EntitiesManagerDao em = new EntitiesManagerDao();
    int index=0;
    for (Entities entity : em.getEntities(vzdb)) {
      Channel channel = new Channel();
      channel.entity = entity;
      for (Properties property : allproperties) {
        if (property.getEntity_id() == entity.getId()) {
          channel.properties.put(property.getPkey(), property);
        }
      }
      if ("channel".equals(entity.getEclass())) {
        channel.setNo(++index);
        channels.add(channel);
        channelsByTitle.put(channel.getTitle(), channel);
      }
    }
    return channels;

  }

  /**
   * get the title of the channel
   * 
   * @return the title
   */
  public String getTitle() {
    String name = properties.get("title").getValue();
    return name;
  }
  
  /**
   * get the description of the channel
   * @return
   */
  public String getDescription() {
    String description = properties.get("description").getValue();
    return description;
  }
}
