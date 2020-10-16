package com.tibco.bx.bw.container.impl;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.MessageContext;
import com.tibco.bw.core.runtime.api.RequestContext;

@Weave(type=MatchType.ExactClass)
public abstract class BxServiceEndpointImpl {
	
	public abstract String getEndpointName();
	
	@Trace(dispatcher=true)
	public <N> void invoke(MessageContext messageContext, N[] requestMessage) {
		String operation = "Unknown";
		RequestContext requestCtx = messageContext.getRequestContext();
		if(requestCtx != null) {
			QName opName = requestCtx.getOperationName();
			operation = opName.getLocalPart();
		}
		
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName(new String[] {"Custom","BxServiceEndpoint",getEndpointName(),operation});
		traced.addRollupMetricName(new String[] {"Custom","BxServiceEndpoint",getEndpointName()});
		
		Weaver.callOriginal();
	}
}
