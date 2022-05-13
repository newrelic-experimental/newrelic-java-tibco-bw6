package com.tibco.bx.bw.container.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.ReferenceEndpoint;
import com.tibco.bx.api.services.BxInvocationInfo;
import com.tibco.bx.api.services.BxResponseListener;

@Weave
public abstract class BxReferenceRequestHandler {

	private ReferenceEndpoint endpoint = Weaver.callOriginal();
	
	@Trace
	public <T> void send(T[] msg, BxResponseListener listener, BxInvocationInfo invokeInfo) {
		String operation = invokeInfo.getOperationName();
		
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String endpointName = "Unknown";
		if(endpoint != null) {
			endpointName = endpoint.getEndpointName();
		}
		traced.setMetricName(new String[] {"Custom","BxReferenceRequestHandler",endpointName,operation,"send"});
		
		Weaver.callOriginal();
	}
}
