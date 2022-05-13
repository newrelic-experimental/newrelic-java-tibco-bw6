package com.tibco.pvm.infra.api.impl.event;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.im.rt.xm.ImxInstProcess;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave
public abstract class IpmEventSchedulerImpl {

	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance process, IpmLifeCycleEvent event) {
		if(process instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)process;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(iProcess.headers);
			}
		}
		Weaver.callOriginal();
	}
	
}
