package com.nr.jersey.instrumentation;

import com.newrelic.api.agent.HeaderType;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper implements InboundHeaders {
	
	private Response response;

	public InboundWrapper(Response r) {
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
