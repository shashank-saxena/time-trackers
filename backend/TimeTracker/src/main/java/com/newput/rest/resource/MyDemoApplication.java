package com.newput.rest.resource;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class MyDemoApplication extends ResourceConfig{

	public MyDemoApplication(){
//		register(RequestContextListener.class);
//		register(PodcastRestService.class);
		register(JacksonFeature.class);
	}
}
