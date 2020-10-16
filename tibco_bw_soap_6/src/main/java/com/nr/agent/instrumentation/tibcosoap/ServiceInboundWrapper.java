package com.nr.agent.instrumentation.tibcosoap;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;
import com.tibco.bw.binding.soap.runtime.service.ServiceMessageContext;

public class ServiceInboundWrapper implements InboundHeaders {
	
	private ServiceMessageContext context;
	
	public ServiceInboundWrapper(ServiceMessageContext ctx) {
		context = ctx;
	}

	@Override
	public String getHeader(String key) {
		return (String) context.getParameter(key);
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

}
