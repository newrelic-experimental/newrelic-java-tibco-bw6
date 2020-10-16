package com.tibco.pvm.infra.api.impl.event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave
public abstract class IpmEventSchedulerImpl {

	@Trace(dispatcher=true)
	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance process, IpmLifeCycleEvent event) {
		String name = process.getName(context);
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IpmEventSchedulerImpl","scheduleLifeCycleEvent",name);
		}
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void scheduleModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event, short priority, Boolean pause) {
		String name = target.getName(context);
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","IpmEventSchedulerImpl","scheduleModelEvent",name);
		}
		Weaver.callOriginal();
	}
	
}
