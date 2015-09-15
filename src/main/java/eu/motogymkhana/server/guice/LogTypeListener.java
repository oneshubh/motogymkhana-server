package eu.motogymkhana.server.guice;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

class LogTypeListener implements TypeListener {
	public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
		for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
			if (field.getType() == Log.class && field.isAnnotationPresent(InjectLogger.class)) {
				typeEncounter.register(new LoggerMembersInjector<T>(field));
			}
		}
	}
}