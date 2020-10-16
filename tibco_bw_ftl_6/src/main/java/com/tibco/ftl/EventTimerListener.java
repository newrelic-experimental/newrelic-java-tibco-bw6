package com.tibco.ftl;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class EventTimerListener {

	@Trace(dispatcher=true)
	public abstract void timerFired(EventTimer paramEventTimer, EventQueue paramEventQueue);

}
