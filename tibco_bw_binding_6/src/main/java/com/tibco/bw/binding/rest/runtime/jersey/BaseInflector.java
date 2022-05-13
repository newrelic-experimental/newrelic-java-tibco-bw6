package com.tibco.bw.binding.rest.runtime.jersey;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.ServiceEndpoint;

@Weave(type=MatchType.BaseClass)
public abstract class BaseInflector<N> {
	
	ServiceEndpoint endpoint = Weaver.callOriginal();
	String operationName = Weaver.callOriginal();


	@Trace(dispatcher=true)
	public Response apply(ContainerRequestContext request) {
		String opName = null;
		if(operationName != null) {
			opName = operationName;
		} else if(endpoint != null) {
			opName = endpoint.getEndpointName();
		}
		if(opName == null) opName = "Unknown";             
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","GetMethodInflector",opName});
		return Weaver.callOriginal();
	}
}
