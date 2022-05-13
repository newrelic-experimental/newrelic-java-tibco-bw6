package com.tibco.pvm.infra.support.work.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.work.WorkItem;

@Weave(type=MatchType.BaseClass)
public abstract class BaseProcessWork {


	@NewField
	public Token token;
	
	@NewField
	public String processName;

	protected PmProcessInstance m_masterProcess = Weaver.callOriginal();

	public abstract WorkItem getWorkItem();

	@Trace(dispatcher=true)
	public void bound() {
		if(processName != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ProcessWork","bound",processName);
		}
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}

	public boolean continueProcessing(PmContext context) {
		if(m_masterProcess != null && processName == null) {
			processName = m_masterProcess.getName(context);
		}
		boolean b = Weaver.callOriginal();
		return b;
	}

	@Trace(dispatcher=true)
	protected void doneProcessingWork(PmContext context, short lcStatus) {
		if(m_masterProcess != null && processName == null) {
			processName = m_masterProcess.getName(context);
		}
		if(processName != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ProcessWork","doneProcessingWork",processName);
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public void unbound() {
		if(processName != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","ProcessWork","unbound",processName);
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
}
