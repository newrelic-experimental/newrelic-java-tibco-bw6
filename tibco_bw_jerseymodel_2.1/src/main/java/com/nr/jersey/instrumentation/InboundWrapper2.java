package com.nr.jersey.instrumentation;

import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.server.ContainerResponse;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper2 implements InboundHeaders {
	
	private ContainerResponse response;

	public InboundWrapper2(ContainerResponse r) {
		response = r;
	}
	
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		MultivaluedMap<String, String> headers = response.getStringHeaders();
		return headers.getFirst(name);
	}

}
