package com.tibco.bw.runtime;

import java.io.Serializable;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class AsyncActivity<N> {
	
	@Trace
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
		
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity",processName,getClass().getSimpleName(),"execute"});
		Weaver.callOriginal();
	}

	@Trace
	public N postExecute(Serializable paramSerializable, ProcessContext<N> processCtx) throws ActivityFault {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String processName = processCtx.getProcessName();
		traced.addCustomAttribute("ProcessName", processName);;
		String appName = processCtx.getApplicationName();
		traced.addCustomAttribute("AppName", appName);
		traced.setMetricName(new String[] {"Custom/AsyncActivity/",processName,"postExecute"});
		
		
		return Weaver.callOriginal();
	}

	@Trace
	public void cancel(ProcessContext<N> processCtx) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String processName = processCtx.getProcessName();
		traced.addCustomAttribute("ProcessName", processName);;
		String appName = processCtx.getApplicationName();
		traced.addCustomAttribute("AppName", appName);
		traced.setMetricName(new String[] {"Custom/AsyncActivity/",processName,"cancel"});
		
		Weaver.callOriginal();
	}

}
