package com.tibco.bw.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
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
		NewRelic.getAgent().getTracedMethod().setMetricName(metricName);
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Work", new String[]{appName,processName});
		
		return Weaver.callOriginal();
	}
}
