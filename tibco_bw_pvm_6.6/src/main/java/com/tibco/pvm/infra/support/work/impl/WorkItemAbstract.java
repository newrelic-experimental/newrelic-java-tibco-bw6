package com.tibco.pvm.infra.support.work.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.work.WorkItem;

@Weave(type=MatchType.BaseClass)
public abstract class WorkItemAbstract  implements WorkItem {

	public abstract PmWorkUnit getTarget(PmContext context);
	

	@Trace
	public Object execute(PmContext paramPmContext) {
		String name = getTarget(paramPmContext).getName(paramPmContext);
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","WorkUnit",name});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,false,"WorkUnit",new String[] {"Custom","WorkUnit",name});
		}
		return Weaver.callOriginal();
	}

}
