package com.tibco.bw.platformservices.scheduler;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class Job {

	@Trace(dispatcher=true)
	public abstract void execute(JobExecutionContext jobExecutionCtx) throws JobExecutionException;
}
