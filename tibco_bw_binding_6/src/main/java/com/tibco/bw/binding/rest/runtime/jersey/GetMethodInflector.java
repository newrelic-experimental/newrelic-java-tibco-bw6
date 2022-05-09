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
public abstract class GetMethodInflector<N> {

	String operationName = Weaver.callOriginal();
	private ServiceEndpoint õ00000 = Weaver.callOriginal();

	@Trace
	public Response apply(ContainerRequestContext request) {
		String opName = null;
		if(operationName != null) {
			opName = operationName;
		} else if(õ00000 != null) {
			opName = õ00000.getEndpointName();
		}
		if(opName == null) opName = "Unknown";             
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","GetMethodInflector",opName});
		return Weaver.callOriginal();
	}
}
