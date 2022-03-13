package com.example.utils.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.StringJoiner;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestUtils {

	public static <T> T postRest(String url, Class<T> clazz) throws MalformedURLException {
		return postRest(url, null, null, clazz);
	}

	public static <T> T postRest(String url, String requestBody, Class<T> clazz) throws MalformedURLException {
		return postRest(url, null, requestBody, clazz);
	}

	public static <T> T postRest(String url, Map<String, String> queryParams, String requestBody, Class<T> clazz)
			throws MalformedURLException {
		Response response = requestBase(url, queryParams)
				.post(requestBody == null ? null : Entity.entity(requestBody, MediaType.APPLICATION_JSON));
		if (Response.Status.Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
			return response.readEntity(clazz);
		}
		throw new SpringRestException(new StringJoiner(": ").add("Unexpected REST call exception has occurred")
				.add(String.valueOf(response.getStatusInfo().getStatusCode()))
				.add(response.getStatusInfo().getReasonPhrase()).add("Tried to send body of").add(requestBody)
				.add("To URL").add(url).toString());
	}

	public static <T> T getRestResponse(String url, Map<String, String> queryParams, Class<T> clazz)
			throws MalformedURLException {
		return requestBase(url, queryParams).get(clazz);
	}

	public static <T> T getRestResponse(String url, Class<T> clazz) throws MalformedURLException {
		return getRestResponse(url, null, clazz);
	}

	private static Builder requestBase(String url, Map<String, String> queryParams) throws MalformedURLException {
		WebTarget webTarget = ClientBuilder.newClient().target(URI.create(new URL(url).toExternalForm()));
		if (queryParams != null) {
			queryParams.entrySet().forEach(entry -> webTarget.queryParam(entry.getKey(), entry.getValue()));
		}
		return webTarget.request(APPLICATION_JSON);
	}

}
