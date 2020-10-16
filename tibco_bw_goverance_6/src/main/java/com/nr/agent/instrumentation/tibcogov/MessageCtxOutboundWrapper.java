package com.nr.agent.instrumentation.tibcogov;

import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class MessageCtxOutboundWrapper implements OutboundHeaders {
	
	private MessageContext context;
	
	public MessageCtxOutboundWrapper(MessageContext ctx) {
		context = ctx;
	}


	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public void setHeader(String key, String val) {
		context.setProperty(key, val);
	}

}
