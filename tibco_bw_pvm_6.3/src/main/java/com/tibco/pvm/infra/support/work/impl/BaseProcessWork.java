package com.tibco.pvm.infra.support.work.impl;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.work.WorkItem;
import com.tibco.pvm.infra.util.ProcessWork;

@Weave(type=MatchType.BaseClass)
public abstract class BaseProcessWork implements ProcessWork {

	@NewField
	public String processName;

	@NewField 
	public Token token = null;

	protected PmProcessInstance m_masterProcess = Weaver.callOriginal();

	public abstract WorkItem getWorkItem();

	protected void doneProcessingWork(PmContext context, short lcStatus) {
		if(m_masterProcess != null && processName == null) {
			processName = m_masterProcess.getName(context);
		}
		Weaver.callOriginal();
	}

}
