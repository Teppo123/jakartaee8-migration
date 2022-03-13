package com.example.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerJaxrsConfig extends ResourceConfig {

	public SwaggerJaxrsConfig() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setTitle("Swagger API Title");
		beanConfig.setVersion("1.0.0");
		beanConfig.setSchemes(new String[] { "http" });
		beanConfig.setHost("localhost:8080/swagger-ui");
		beanConfig.setBasePath("/rest");
		beanConfig.setResourcePackage("your.restws.package");
		beanConfig.setScan(true);
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, Boolean.TRUE);
		packages("your.restws.package");
		packages("io.swagger.jaxrs.listing");
//		register(MoxyJsonFeature.class);
//		// register(BadRequestExceptionMapper.class);
//		register(new MoxyJsonConfig().setFormattedOutput(true)
//				// Turn off BV otherwise the entities on server would be validated by MOXy as
//				// well.
//				.property(MarshallerProperties.BEAN_VALIDATION_MODE, BeanValidationMode.NONE).resolver());
//
//		register(MoxyXmlFeature.class);
		register(RolesAllowedDynamicFeature.class);
	}
	
}
