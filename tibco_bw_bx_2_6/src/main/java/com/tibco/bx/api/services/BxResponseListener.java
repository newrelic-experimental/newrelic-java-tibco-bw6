package com.tibco.bx.api.services;

import javax.xml.namespace.QName;
import com.newrelic.api.agent.Token;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class BxResponseListener {
	
	@NewField
	public Token token;

	@Trace(dispatcher=true)
	public <T> void onMessage(T[] paramArrayOfT, QName paramQName, BxInvocationInfo paramBxInvocationInfo) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public <T> void onFault(QName paramQName1, T[] paramArrayOfT, QName paramQName2, BxInvocationInfo paramBxInvocationInfo) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

}
