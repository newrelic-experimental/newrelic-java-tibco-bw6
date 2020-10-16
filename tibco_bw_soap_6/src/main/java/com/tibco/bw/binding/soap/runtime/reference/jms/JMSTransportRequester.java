package com.tibco.bw.binding.soap.runtime.reference.jms;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.tibcosoap.MessageCtxOutboundWrapper;

@Weave
public abstract class JMSTransportRequester {

	public abstract String getName();
	
	@Trace
	public Handler.InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getName(),msgContext.getSoapAction()});
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(new MessageCtxOutboundWrapper(msgContext));
		return Weaver.callOriginal();
	}

}
