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

import javax.xml.bind.annotation.XmlTransient;

/**
 * interface for properties
 * @author wf
 *
 */
public interface Properties {

	int getId();

	void setId(int id);

  @XmlTransient
	Entities getEntity();

	void setEntity(Entities entity);

	String getPkey();

	void setPkey(String pkey);

	String getValue();

	void setValue(String value);

}