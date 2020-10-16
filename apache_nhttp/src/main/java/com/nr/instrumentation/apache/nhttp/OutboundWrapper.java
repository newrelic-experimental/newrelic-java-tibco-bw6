package com.nr.instrumentation.apache.nhttp;

import org.apache.http.HttpResponse;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {

	private HttpResponse response;
	
	public OutboundWrapper(HttpResponse resp) {
		response = resp;
	}
	
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		response.addHeader(name, value);
	}

}
