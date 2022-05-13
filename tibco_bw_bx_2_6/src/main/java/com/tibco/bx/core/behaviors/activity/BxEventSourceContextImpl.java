package com.tibco.bx.core.behaviors.activity;

import java.util.ArrayList;
import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bx.plugin.ActivityFault;
import com.tibco.bx.plugin.EventContext;
import com.tibco.bx.plugin.EventSourceContext;

@Weave
public abstract class BxEventSourceContextImpl implements EventSourceContext {

	@Trace(dispatcher=true)
	public void newError() {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void newEvent(ActivityFault exp) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public <N> void newEvent(N input, EventContext eventContext) {
		List<String> list = new ArrayList<String>();
		list.add("Custom");
		list.add(getClass().getSimpleName());
		list.add("newEvent");
		String temp = getProcessName();
		if(temp != null && !temp.isEmpty()) {
			list.add(temp);
		}
		temp = getModuleName();
		if(temp != null && !temp.isEmpty()) {
			list.add(temp);
		}
		temp = getActivityName();
		if(temp != null && !temp.isEmpty()) {
			list.add(temp);
		}
		String[] strArray = new String[list.size()];
		list.toArray(strArray);
		NewRelic.getAgent().getTracedMethod().setMetricName(strArray);
		Weaver.callOriginal();
	}
}
