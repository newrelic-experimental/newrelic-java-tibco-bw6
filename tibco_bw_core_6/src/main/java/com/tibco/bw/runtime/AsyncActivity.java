package com.tibco.bw.runtime;

import java.io.Serializable;

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
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName});
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public N postExecute(Serializable paramSerializable, ProcessContext<N> processCtx) throws ActivityFault {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName,"postExecute"});
		
		
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void cancel(ProcessContext<N> processCtx) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		String processName = processCtx.getProcessName();
		String appName = processCtx.getApplicationName();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom/AsyncActivity/",appName,processName,"cancel"});
		
		Weaver.callOriginal();
	}

}
