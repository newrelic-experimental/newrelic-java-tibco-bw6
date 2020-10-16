package com.tibco.bw.runtime;

import java.util.logging.Level;

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
			NewRelic.getAgent().getLogger().log(Level.FINE, "Created token {0} in AsyncActivityController", acn.token);
		}
		return acn;
	}
}
