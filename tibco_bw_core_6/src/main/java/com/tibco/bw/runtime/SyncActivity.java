package com.tibco.bw.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class SyncActivity<N> {

	@Trace(dispatcher=true)
	public N execute(N paramN, ProcessContext<N> processCtx) throws ActivityFault {
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		String classname = getClass().getSimpleName();
		String metricName = "Custom/SyncActivity/" + classname;
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("ProcessName", processName);
		traced.addCustomAttribute("AppName", appName);
		traced.setMetricName(metricName);
		
		return Weaver.callOriginal();
	}
}
