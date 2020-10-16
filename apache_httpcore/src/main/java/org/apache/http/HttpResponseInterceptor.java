package org.apache.http;

import org.apache.http.protocol.HttpContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class HttpResponseInterceptor {
	
	@Trace
	public void process(HttpResponse httpResponse, HttpContext httpContext) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpResponseInterceptor",getClass().getName(),"process"});
		Weaver.callOriginal();
	}
}
