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
