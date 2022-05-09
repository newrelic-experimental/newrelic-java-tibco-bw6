package com.nr.instrumentation.bx;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;
import com.tibco.bx.api.services.BxInvocationInfo;

public class InboundWrapper implements InboundHeaders {

	private BxInvocationInfo info;
	
	public InboundWrapper(BxInvocationInfo i) {
		info = i;
	}
	
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public String getHeader(String name) {
		return (String) info.getInputContextVariables().get(name);
	}

}
