package eu.motogymkhana.server.guice;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.MembersInjector;

class LoggerMembersInjector<T> implements MembersInjector<T> {
	private final Field field;
	private final Log logger;

	LoggerMembersInjector(Field field) {
		this.field = field;
		this.logger = LogFactory.getLog(field.getDeclaringClass());
		field.setAccessible(true);
	}

	public void injectMembers(T t) {
		try {
			field.set(t, logger);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
