package eu.motogymkhana.server.http.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import eu.motogymkhana.server.guice.InjectLogger;
import eu.motogymkhana.server.http.HttpResultWrapper;
import eu.motogymkhana.server.http.MyHttpClient;

@Singleton
public class MyHttpClientImpl implements MyHttpClient {

	@Inject
	private HttpClient client;

	@InjectLogger
	private Log log;

	public MyHttpClientImpl() {

	}

	@Override
	public HttpResultWrapper getStringFromUrl(String url, String authString)
			throws ClientProtocolException, IOException {

		log.debug("url for download " + url);
		
		HttpGet httpGet = new HttpGet(url);
		if (authString != null) {
			httpGet.setHeader("Authorization", authString);
		}

		HttpResponse response = client.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in), 512);
		return new HttpResultWrapper(response.getStatusLine().getStatusCode(), response
				.getStatusLine().getReasonPhrase(), br.readLine());
	}

	@Override
	public HttpResultWrapper postStringFromUrl(String url, String input)
			throws ClientProtocolException, IOException {

		HttpPost httpPost = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(input.replaceAll("\"", "\\\""));
		stringEntity.setContentType("application/json");
		httpPost.setEntity(stringEntity);
		HttpResponse response = client.execute(httpPost);
		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(in), 512);
		return new HttpResultWrapper(response.getStatusLine().getStatusCode(), response
				.getStatusLine().getReasonPhrase(), br.readLine());
	}

}
