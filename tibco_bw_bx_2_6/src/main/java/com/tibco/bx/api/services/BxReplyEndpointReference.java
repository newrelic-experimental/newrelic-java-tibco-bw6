package com.tibco.bx.api.services;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class BxReplyEndpointReference {
	
	@NewField
	public com.newrelic.api.agent.Token token;

	@Trace(dispatcher=true)
	public <T> void send(T[] paramArrayOfT, BxInvocationInfo paramBxInvocationInfo) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BxReplyEndpointReference",getClass().getSimpleName(),"send",getOperationName());
		Weaver.callOriginal();
	}
	

	@Trace(dispatcher=true)
	public <T> void sendFault(QName paramQName, T[] paramArrayOfT, BxInvocationInfo paramBxInvocationInfo) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BxReplyEndpointReference",getClass().getSimpleName(),"sendFault",getOperationName());
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void onException(String paramString, Throwable paramThrowable) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BxReplyEndpointReference",getClass().getSimpleName(),"onException",getOperationName());
		NewRelic.noticeError(paramThrowable);
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void onCompletion() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","BxReplyEndpointReference",getClass().getSimpleName(),"onCompletion",getOperationName());
		Weaver.callOriginal();
	}


	public abstract String getOperationName();

}
