package com.tibco.bw.frwk;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.MessageContext;

@Weave
public abstract class BWServiceEndpointRequestHandlerProxy {

	public abstract String getEndpointName();
	
	@Trace(dispatcher=true)
	public <N> void invoke(MessageContext paramMessageContext, N[] paramArrayOfN) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BWServiceEndpointRequestHandlerProxy",getEndpointName()});
		Weaver.callOriginal();
	}
}
