package com.tibco.bx.bw.container.impl;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bx.api.services.BxInvocationInfo;
import com.tibco.bx.api.services.BxResponseListener;

@Weave
public abstract class BxWireEndpointReferenceImpl {

	@Trace
	public <T> void send(T[] msg, BxResponseListener listener, BxInvocationInfo invokeInfo) throws Exception {
		Weaver.callOriginal();
	}
}
