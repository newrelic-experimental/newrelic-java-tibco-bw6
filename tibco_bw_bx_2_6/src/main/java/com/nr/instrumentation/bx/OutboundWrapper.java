package com.nr.instrumentation.bx;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import com.tibco.bx.api.services.BxInvocationInfo;

public class OutboundWrapper implements OutboundHeaders {

	private BxInvocationInfo info;
	
	public OutboundWrapper(BxInvocationInfo i) {
		info = i;
	}
	
@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public void setHeader(String name, String value) {
		info.setInputContextVariable(name, value);
	}

}
