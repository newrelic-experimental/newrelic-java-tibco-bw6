package com.tibco.bw.binding.rest.runtime.jersey;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.ContainerRequest;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.binding.instrumentation.NRContants;

@Weave(type=MatchType.BaseClass)
public abstract class GetMethodInflector<N> {

	@Trace(dispatcher=true)
	public Response apply(ContainerRequestContext request) {
		Request req = request.getRequest();
		if(ContainerRequest.class.isInstance(req)) {
			ContainerRequest cr = (ContainerRequest)req;
			Token token  = (Token)cr.getProperty(NRContants.NR_KEY);
			if(token != null) {
				token.link();
			}
		}
		UriInfo uriInfo = request.getUriInfo();
		if(uriInfo != null) {
			String path = uriInfo.getPath();
			if(path != null && !path.isEmpty()) {
				NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","GetMethodInflector",path});
			}
		}
		return Weaver.callOriginal();
	}
}
