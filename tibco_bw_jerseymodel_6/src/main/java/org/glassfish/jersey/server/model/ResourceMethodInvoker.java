package org.glassfish.jersey.server.model;

import java.net.URI;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.jersey.instrumentation.NRContants;

@Weave
public abstract class ResourceMethodInvoker {

	@Trace(dispatcher=true)
	public ContainerResponse apply(ContainerRequest requestContext) {
		URI uri = requestContext.getRequestUri();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"apply",uri.toASCIIString()});
		requestContext.setProperty(NRContants.NR_KEY, NewRelic.getAgent().getTransaction().getToken());
		ContainerResponse response = Weaver.callOriginal();
		
		
		return response;
	}
	
	
}
