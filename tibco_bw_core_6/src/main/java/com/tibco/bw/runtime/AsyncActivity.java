package com.tibco.bw.runtime;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class AsyncActivity<N> {
	
	@NewField
	protected Token token;

	@Trace(dispatcher=true)
	public void execute(N paramN, ProcessContext<N> processCtx, AsyncActivityController asyncActivityController) throws ActivityFault {
		HashMap<String, Object> attributes = new HashMap<>();
		String processName = processCtx.getProcessName();
		if(processName != null) attributes.put("ProcessName", processName);
		String appName = processCtx.getApplicationName();
		if(processName != null) attributes.put("AppName", appName);
		String activityID = processCtx.getActivityExecutionId();
		if(activityID != null) attributes.put("ActivityExecutionID", activityID);
		String jobId = processCtx.getJobId();
		if(jobId != null) attributes.put("JobId", jobId);
		String processId = processCtx.getProcessInstanceId();
		if(processId != null) attributes.put("ProcessExecutionID", processId);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName,getClass().getSimpleName(),"execute"});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public N postExecute(Serializable paramSerializable, ProcessContext<N> processCtx) throws ActivityFault {
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName,"postExecute"});
		
		
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void cancel(ProcessContext<N> processCtx) {
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName,"cancel"});
		
		Weaver.callOriginal();
	}

}
