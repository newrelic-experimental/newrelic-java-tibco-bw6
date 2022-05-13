package com.tibco.bx.core;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bx.api.BxProcessInstance;
import com.tibco.bx.api.BxProcessRunner;
import com.tibco.pvm.runner.util.PmRunnerContext;

@Weave
public abstract class BxProcessRunnerImpl {

	@Trace(dispatcher=true)
	public boolean run() {
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public BxProcessRunner.EventFQName createEventFQName(String moduleName, String processName, String eventName) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom/BxProcessRunnerImpl",moduleName,processName,eventName);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public BxProcessInstance getProcessInstance() {
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public boolean stop(PmRunnerContext context) {
		return Weaver.callOriginal();
	}
}
