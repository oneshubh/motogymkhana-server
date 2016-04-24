package eu.motogymkhana.server.properties.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Util {

	public static String stacktraceToString(Exception e){
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

}
