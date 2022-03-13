package com.example.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import com.example.utils.PropertiesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractWrapperService {

	Properties properties;

	public void callService(Callable<Void> jakartaExecution, Callable<Void> springExecution) throws Exception {
		boolean runSpring = runSpring();
		boolean runJakarta = runJakarta(runSpring);
		if (runJakarta) {
			jakartaExecution.call();
		}
		if (runSpring) {
			springExecution.call();
		}
	}

	public <T> List<T> getServiceListResponse(Callable<List<T>> jakartaExecution, Callable<T[]> springExecution)
			throws Exception {
		return getServiceResponse(jakartaExecution, () -> Arrays.asList(springExecution.call()));
	}

	public <T> T getServiceResponse(Callable<T> jakartaExecution, Callable<T> springExecution) throws Exception {
		boolean runSpring = runSpring();
		boolean runJakarta = runJakarta(runSpring);
		T jakartaResponse = null;
		T springResponse = null;

		if (runJakarta) {
			jakartaResponse = jakartaExecution.call();
		}
		if (runSpring) {
			springResponse = springExecution.call();
		}

		return runJakarta ? jakartaResponse : springResponse;
	}

	private boolean runJakarta(boolean runSpring) throws IOException {
		return runSpring ? getBooleanProperty("userService.runJakartaEE") : true;
	}

	private boolean runSpring() throws IOException {
		return getBooleanProperty("userService.runSpring");
	}

	private ObjectMapper objectMapper = null;

	private ObjectMapper getObjectMapper() {
		if (this.objectMapper == null) {
			this.objectMapper = new ObjectMapper();
		}
		return this.objectMapper;
	}

	public String toJsonString(final Object obj) {
		try {
			return getObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	boolean getBooleanProperty(String key) throws IOException {
		return Boolean.valueOf(getStringProperty(key));
	}

	String getStringProperty(String key) throws IOException {
		return getProperties().getProperty(key);
	}

	private Properties getProperties() throws IOException {
		if (this.properties == null) {
			this.properties = PropertiesUtils.getDefaultProperties();
		}
		return this.properties;
	}

}
