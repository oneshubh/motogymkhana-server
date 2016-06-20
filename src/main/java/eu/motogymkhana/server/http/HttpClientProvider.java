/*******************************************************************************
 * Copyright (c) 2015, 2016, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 *  
 *******************************************************************************/
package eu.motogymkhana.server.http;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLException;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AbstractVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HttpClientProvider implements Provider<HttpClient> {

	private HttpClient client;

	@Inject
	public HttpClientProvider() {
	}

	public HttpClient get() {

		if (client == null) {
			try {
				client = createClient();
			} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return client;
	}

	private HttpClient createClient() throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException {

		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory();

		javax.net.ssl.SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
				new TrustStrategy() {
					public boolean isTrusted(X509Certificate[] arg0, String arg1)
							throws CertificateException {
						return true;
					}
				}).build();

		X509HostnameVerifier hostnameVerifier = new AbstractVerifier() {

			@Override
			public void verify(String hostname, String[] arg1, String[] arg2) throws SSLException {

				boolean verified = false;

				for (String s : arg1) {
					if (hostname.equals(s)) {
						verified = true;
					}
				}
				for (String s : arg2) {
					if (hostname.equals(s)) {
						verified = true;
					}
				}

				String names = "";
				for (String s : arg1) {
					names += s + " ";
				}
				for (String s : arg2) {
					names += s + " ";
				}
				if (!verified) {
					throw new SSLException("invalid hostname " + hostname + " " + names);
				}
			}

		};

		SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
				hostnameVerifier);

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", sslSocketFactory).build();

		DnsResolver dnsResolver = new SystemDefaultDnsResolver();

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry, connFactory, dnsResolver);

		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(connManager)
				.build();

		return httpclient;
	}
}
