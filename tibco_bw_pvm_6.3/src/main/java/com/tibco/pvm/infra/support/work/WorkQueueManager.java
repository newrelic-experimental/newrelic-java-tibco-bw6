package com.tibco.pvm.infra.support.work;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class WorkQueueManager {

	@Trace(dispatcher=true)
	public void scheduleWork(Work paramWork, short paramShort1, short paramShort2) {
		if(paramWork.token == null) {
			paramWork.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
}
