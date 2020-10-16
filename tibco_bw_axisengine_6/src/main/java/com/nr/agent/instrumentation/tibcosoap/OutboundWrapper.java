package com.nr.agent.instrumentation.tibcosoap;

import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {
	
	private MessageContext msgCtx;
	
	public OutboundWrapper(MessageContext messageCtx) {
		msgCtx = messageCtx;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String key, String val) {
		msgCtx.setProperty(key, val);
	}

}
