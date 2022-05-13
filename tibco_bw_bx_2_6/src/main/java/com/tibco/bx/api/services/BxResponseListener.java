package com.tibco.bx.api.services;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class BxResponseListener {
	
	@Trace
	public <T> void onMessage(T[] paramArrayOfT, QName paramQName, BxInvocationInfo paramBxInvocationInfo) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, paramBxInvocationInfo.headers);
		Weaver.callOriginal();
	}

	@Trace
	public <T> void onFault(QName paramQName1, T[] paramArrayOfT, QName paramQName2, BxInvocationInfo paramBxInvocationInfo) {
		NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, paramBxInvocationInfo.headers);
		Weaver.callOriginal();
	}

}
