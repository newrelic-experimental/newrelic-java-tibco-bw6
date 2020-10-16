package com.tibco.bx.api.services;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bx.api.BxModule;

@Weave(type=MatchType.Interface)
public abstract class BxServiceAgent {
	
	  public abstract String getName();

	  public abstract String getVersion();

	  public abstract String getAppName();

	  public abstract BxModule getModule();

	@Trace(dispatcher=true)
	  public <T> void onDispatch(String paramString1, String paramString2, QName paramQName, T[] paramArrayOfT, BxReplyEndpointReference paramBxReplyEndpointReference, BxInvocationInfo paramBxInvocationInfo) {
		if(paramBxReplyEndpointReference.token == null) {
			paramBxReplyEndpointReference.token = NewRelic.getAgent().getTransaction().getToken();
		}

		Weaver.callOriginal();
	}

}
