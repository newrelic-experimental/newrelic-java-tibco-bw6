package org.apache.axis2.transport.http;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CommonsHTTPTransportSender {

	public abstract String getName();
	
	@Trace(dispatcher=true)
	public Handler.InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getName(),msgContext.getSoapAction()});
		return Weaver.callOriginal();
	}
	
}
