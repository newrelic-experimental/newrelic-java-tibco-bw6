package com.tibco.governance.policy.interceptor;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.tibcogov.MessageCtxOutboundWrapper;

@Weave
public abstract class SoapInterceptorHandler {

	@Trace
	public Handler.InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(new MessageCtxOutboundWrapper(msgContext));
		return Weaver.callOriginal();
	}
}
