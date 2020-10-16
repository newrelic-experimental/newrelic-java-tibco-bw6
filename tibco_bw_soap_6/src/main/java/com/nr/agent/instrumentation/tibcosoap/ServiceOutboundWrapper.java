package com.nr.agent.instrumentation.tibcosoap;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import com.tibco.bw.binding.soap.runtime.service.ServiceMessageContext;

public class ServiceOutboundWrapper implements OutboundHeaders {
	
	private ServiceMessageContext context;
	
	public ServiceOutboundWrapper(ServiceMessageContext ctx) {
		context = ctx;
	}


	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public void setHeader(String key, String val) {
		HashMap<String, Object> parms;
		Map<String,Object> tmp = context.getParameters();
		parms = new HashMap<String, Object>(tmp);
		parms.put(key, val);
		context.setContextPrams(parms);
	}

}
