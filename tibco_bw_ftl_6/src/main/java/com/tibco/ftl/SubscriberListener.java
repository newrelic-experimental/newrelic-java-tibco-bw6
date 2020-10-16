package com.tibco.ftl;

import java.util.List;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class SubscriberListener {

	@Trace(dispatcher=true)
	public abstract void messagesReceived(List<Message> paramList, EventQueue paramEventQueue);

}
