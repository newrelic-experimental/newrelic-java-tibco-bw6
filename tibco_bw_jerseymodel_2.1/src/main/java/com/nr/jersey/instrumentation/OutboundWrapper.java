package com.nr.jersey.instrumentation;

import org.glassfish.jersey.server.ContainerRequest;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {
	
	private ContainerRequest request;
	
	public OutboundWrapper(ContainerRequest r) {
		request = r;
	}

	@Override
	public HeaderType getHeaderType() {
		// TODO Auto-generated method stub
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		String val = request.getHeaderString(name);
		if(val == null || !val.equals(value)) {
			request.header(name, value);
		}
	}

}
