package com.tibco.pvm.im.rt.xm.util.work;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.im.rt.xm.ImxInstProcess;

@Weave
public abstract class ImxProcessWork  {
	
	protected void enqueueWorkItem(PmWorkUnit target, PmObjectEvent<PmWorkUnit> event, short priority, int details,
			Boolean paused) {
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public void scheduleEvent(PmContext context, PmWorkUnit target, PmObjectEvent<PmWorkUnit> event, short priority,
			int details, Boolean paused) {
		HashMap<String, Object> attributes = new HashMap<>();
		attributes.put("Context", context != null ? context.toString() : "null");
		attributes.put("Target-Class", target != null ? target.getClass().getName() : "null");
		if(target instanceof ImxInstProcess) {
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(((ImxInstProcess)target).headers);
			attributes.put("Headers-Populated", true);
			attributes.put("Headers-Size", ((ImxInstProcess)target).headers.getHeaderNames().size());
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public Object invokeModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event) {
		
		return Weaver.callOriginal();
	}
}
