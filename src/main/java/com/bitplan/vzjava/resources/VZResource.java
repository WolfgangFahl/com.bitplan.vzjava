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
package com.bitplan.vzjava.resources;

import java.util.Map;

import com.bitplan.rest.resources.BaseResource;

/**
 * Baseresource for all Volkszähler Resources
 * @author wf
 *
 */
@SuppressWarnings("rawtypes")
public class VZResource extends BaseResource {
	 /**
	   * prepare the root Map
	   * @param title
	   */
	  public static void prepareMap(Map<String,Object> rootMap,String title, String toolTip) {
	    rootMap.put("title", title);
	    rootMap.put("tooltip", toolTip);
	    // TODO - add link to opensource URL
	    rootMap.put("comment",
	        "This page is generated by the com.bitplan.vzjar RESTful solution Volkszähler");
	  }
	  
	  /**
	   * 
	   * @param title
	   */
	  public void prepareRootMap(String title, String toolTip) {
		  prepareMap(rootMap,title,toolTip);
	  }
}
