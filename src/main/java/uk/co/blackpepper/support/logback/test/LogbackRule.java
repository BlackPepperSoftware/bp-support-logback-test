/*
 * Copyright 2014 Black Pepper Software
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
package uk.co.blackpepper.support.logback.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.rules.ExternalResource;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class LogbackRule extends ExternalResource {
	
	private Map<Class<?>, Level> oldLevelsByClass;
	
	@Override
	protected void before() {
		oldLevelsByClass = new HashMap<>();
	}
	
	@Override
	protected void after() {
		resetLoggerLevels();
		
		oldLevelsByClass = null;
	}

	public void off(Class<?> loggerClass) {
		Logger logger = getLogger(loggerClass);
		
		oldLevelsByClass.put(loggerClass, logger.getLevel());
		
		logger.setLevel(Level.OFF);
	}

	private void resetLoggerLevels() {
		for (Entry<Class<?>, Level> entry : oldLevelsByClass.entrySet()) {
			Class<?> clazz = entry.getKey();
			Level level = entry.getValue();
			
			getLogger(clazz).setLevel(level);
		}
	}

	private static Logger getLogger(Class<?> clazz) {
		return (Logger) LoggerFactory.getLogger(clazz);
	}
}
