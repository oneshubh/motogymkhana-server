package eu.motogymkhana.server.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util {

	/**
	 * update the object with all string, boolean, int and long values from
	 * newAgent this could be generalized for other classes as well.
	 * 
	 * @param object2
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void updateWith(Object object1, Object object2) throws SecurityException,
			NoSuchMethodException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		Method[] methods = object2.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
				// getter
				if (!method.getName().endsWith("_id") && isUpdate(method.getReturnType())) {
					try {
						Method setter = object1.getClass().getMethod(
								method.getName().startsWith("get") ? method.getName().replaceFirst(
										"g", "s") : method.getName().replaceFirst("is", "set"),
								method.getReturnType());
						setter.invoke(object1, method.invoke(object2, null));
					} catch (NoSuchMethodException nsme) {

					}
				}
			}
		}
	}

	private static boolean isUpdate(Class<?> clazz) {
		return clazz.equals(String.class) || clazz.equals(boolean.class)
				|| clazz.equals(long.class) || clazz.equals(int.class);
	}
	
	public static String stacktraceToString(Exception e){
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
