package eu.motogymkhana.server;

import java.util.concurrent.TimeUnit;

public class ServerConstants {

	public static final int HTTPS_PORT = 9005;
	public static final int HTTP_PORT = 8085;

	public static String keyStoreType = "JKS";
	public static String keyAlias = "pengo_ssl";
	// public static final String testKeyAlias = "gossiptest";

	public static final String HTTPS = "https://";
	public static final String HTTP = "http://";
	public static final String UPDATE_RIDER = "/updateRider/";
	public static final String UPDATE_TIMES = "/updateTimes/";
	public static final String UPDATE_TEXT = "/updateText/";
	public static final String DELETE_RIDER = "/deleteRider/";
	public static final String GET_RIDERS = "/getRiders/";
	public static final String UPLOAD_RIDERS = "/uploadRiders/";
	public static final String UPDATE_RIDERS = "/updateRiders/";
	public static final String UPLOAD_ROUNDS = "/uploadRounds/";
	public static final String GET_ROUNDS = "/getRounds/";
	public static final String CHECK_PASSWORD = "/pw/";
	public static final String UPDATE_SETTINGS = "/updateSettings/";
	public static final String GET_SETTINGS = "/getSettings/";

	public static final String UI_GET_RIDERS = "/ui/getRiders/";
	public static final String UI_GET_ROUNDS = "/ui/getRounds/";
	public static final String UI_GET_SETTINGS = "/ui/getSettings/";
	public static final String UI_SEND_RIDER_TOKEN = "/ui/sendToken/";
	public static final String UI_REGISTER_RIDER = "/ui/registerRider/";

	public static final String MOTOGYMKHANA = "/motogymkhana";

	public static final long RONALD_DOWNLOAD_START = 10;
	public static final long RONALD_DOWNLOAD_INTERVAL = 300;
	public static final TimeUnit RONALD_DOWNLOAD_TIMEUNIT = TimeUnit.SECONDS;

	public static String digestAlgorithm = "SHA-256";
}
