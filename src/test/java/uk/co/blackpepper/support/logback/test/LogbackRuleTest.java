package uk.co.blackpepper.support.logback.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LogbackRuleTest {
	
	private static class TestClass {
		// log class for use by tests
	}

	private LogbackRule rule;
	
	@Before
	public void setUp() {
		rule = new LogbackRule();
		rule.before();
	}
	
	@Test
	public void offSetsLevelToOff() {
		getLogger(TestClass.class).setLevel(Level.ALL);
		
		rule.off(TestClass.class);
		
		assertThat(getLogger(TestClass.class).getLevel(), is(Level.OFF));
	}
	
	@Test
	public void afterResetsLevel() {
		getLogger(TestClass.class).setLevel(Level.ALL);
		rule.off(TestClass.class);
		
		rule.after();

		assertThat(getLogger(TestClass.class).getLevel(), is(Level.ALL));
	}

	private static Logger getLogger(Class<TestClass> clazz) {
		return (Logger) LoggerFactory.getLogger(clazz);
	}
}
