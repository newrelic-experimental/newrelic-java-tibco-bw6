package org.glassfish.jersey.server.spi.internal;

import java.net.URI;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ContainerRequest;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.jersey.instrumentation.NRContants;

@Weave(type=MatchType.Interface)
public abstract class ResourceMethodDispatcher {

	@Trace(dispatcher=true)
	public Response dispatch(Object paramObject, ContainerRequest containerRequest) throws ProcessingException {
		URI uri = containerRequest.getRequestUri();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ResourceMethodDispatcher",getClass().getSimpleName(),"dispatch",uri.toASCIIString()});
		Token token = (Token) containerRequest.getProperty(NRContants.NR_KEY);
		if(token != null) {
			token.linkAndExpire();
		}
		Response response = Weaver.callOriginal();
		return response;
	}
}
