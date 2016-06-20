/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

public interface MyHttpClient {

	public HttpResultWrapper getStringFromUrl(String url, String authString) throws ClientProtocolException,
			IOException;

	public HttpResultWrapper postStringFromUrl(String url, String input)
			throws UnsupportedEncodingException, ClientProtocolException, IOException;
}
