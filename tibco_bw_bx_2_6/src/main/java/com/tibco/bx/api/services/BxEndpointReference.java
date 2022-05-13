package com.tibco.bx.api.services;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.bx.TibcoBXHeaders;

@Weave(type=MatchType.Interface)
public abstract class BxEndpointReference {

	@Trace(dispatcher=true)
	public <T> void send(T[] paramArrayOfT, BxResponseListener paramBxResponseListener, BxInvocationInfo paramBxInvocationInfo) {
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(new TibcoBXHeaders());
		Weaver.callOriginal();
	}
}
