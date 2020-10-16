package com.tibco.bw.frwk;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.MessageContext;

@Weave
abstract class ReferenceEndpointWrapper {

	public abstract String getEndpointName();
	
	@Trace(dispatcher=true)
	public <N> void invoke(MessageContext msgCtx, N[] paramArrayOfN) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Entering ReferenceEndpointWrapper", new Object[0]);
		
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ReferenceEndpointWrapper",getEndpointName()});
		Weaver.callOriginal();
	}
}
