package com.nr.agent.instrumentation.tibcosoap;

import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

public class InboundWrapper implements InboundHeaders {
	
	private MessageContext msgCtx;
	
	public InboundWrapper(MessageContext messageCtx) {
		msgCtx = messageCtx;
	}

	@Override
	public String getHeader(String key) {
		
		return (String) msgCtx.getProperty(key);
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

}
