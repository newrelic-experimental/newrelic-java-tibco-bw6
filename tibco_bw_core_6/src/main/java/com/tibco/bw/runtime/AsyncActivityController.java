package com.tibco.bw.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class AsyncActivityController {

	public AsyncActivityCompletionNotifier setPending(long paramLong) {
		AsyncActivityCompletionNotifier acn = Weaver.callOriginal();
		if(acn.token == null) {
			acn.token = NewRelic.getAgent().getTransaction().getToken();
		}
		return acn;
	}
}
