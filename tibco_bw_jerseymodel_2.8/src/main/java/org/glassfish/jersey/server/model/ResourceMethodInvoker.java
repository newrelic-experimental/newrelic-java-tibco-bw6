package org.glassfish.jersey.server.model;

import java.net.URI;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.RoutingContext;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.jersey.instrumentation.NRContants;

@Weave
public abstract class ResourceMethodInvoker {

	@Trace(dispatcher=true)
	public ContainerResponse apply(RequestProcessingContext requestProcessingContext) {
		
		RoutingContext ctx = requestProcessingContext.routingContext();
		URI uri = null;
		if(ctx instanceof UriRoutingContext) {
			UriRoutingContext uriCtx = (UriRoutingContext)ctx;
			uri = uriCtx.getRequestUri();
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("URI", uri.toASCIIString());
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"apply"});
		ContainerRequest requestContext = requestProcessingContext.request();
		if (requestContext != null) {
			requestContext.setProperty(NRContants.NR_KEY, NewRelic.getAgent().getTransaction().getToken());
		}
		ContainerResponse response = Weaver.callOriginal();
		
		
		return response;
	}
	
	
}
