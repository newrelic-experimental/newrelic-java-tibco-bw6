package com.nr.instrumentation.apache.nhttp;

import org.apache.http.Header;
import org.apache.http.HttpRequest;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper implements InboundHeaders {
	
	private HttpRequest request;
	
	public InboundWrapper(HttpRequest req) {
		request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		Header[] headers = request.getHeaders(name);
		Header header = headers == null | headers.length == 0 ? null : headers[0];
		return header == null ? null : header.getValue();
	}

}
