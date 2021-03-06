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

import java.util.Date;

/**
 * power Values
 * 
 * @author wf
 */
public interface PowerValue {
	enum ChannelMode {
		Power, Counter
	}
	public int getId();
    public void setId(int id);
    
	public int getChannel_id();
	public void setChannel_id(int channel_id);
	
	public Date getTimeStamp();
	public void setTimeStamp(Date timeStamp);
	
	public double getValue();
	public void setValue(double value);

	public PowerValue cloneMe();
}
