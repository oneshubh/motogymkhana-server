package eu.motogymkhana.server;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.motogymkhana.server.util.Util;


public class EngineLauncher implements Daemon {

	private static final Log log = LogFactory.getLog(EngineLauncher.class);

	private static GymkhanaServer gymkhana = null;

	@Override
	public void destroy() {
		log.debug("Daemon destroy");
	}

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {
		log.debug("Daemon init");
	}

	@Override
	public void start() throws Exception {
		log.debug("Daemon start");
		initialize();
	}

	@Override
	public void stop() throws Exception {
		log.debug("Daemon stop");
		terminate();
	}

	private void initialize() {

		if (gymkhana == null) {
			
			log.info("Starting the Engine");
			gymkhana = new GymkhanaServer();

			try {

				gymkhana.startIt();

			} catch (Exception e) {
				log.error(Util.stacktraceToString(e));
				e.printStackTrace();
			}
		}
	}

	private void terminate() {

		if (gymkhana != null) {
			log.info("Stopping the Engine");

			try {
				gymkhana.stop();
			} catch (Exception e) {
				log.error(Util.stacktraceToString(e));
				e.printStackTrace();
			}

			log.info("Engine stopped");
		}
	}
}
