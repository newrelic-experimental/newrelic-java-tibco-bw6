package com.tibco.bw.binding.soap.runtime.axis2;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.agent.instrumentation.tibcosoap.MessageCtxOutboundWrapper;

@Weave
public class BWPolicyHandler {

	@Trace
	public Handler.InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		MessageCtxOutboundWrapper wrapper = new MessageCtxOutboundWrapper(msgContext);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders((wrapper));
		return Weaver.callOriginal();
	}

}
