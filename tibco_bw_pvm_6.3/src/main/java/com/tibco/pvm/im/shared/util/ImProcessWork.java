package com.tibco.pvm.im.shared.util;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;
import com.tibco.pvm.infra.support.work.impl.BaseProcessWork;
import com.tibco.pvm.infra.support.work.impl.WorkItemAbstract;

@Weave(type = MatchType.BaseClass)
public abstract class ImProcessWork extends BaseProcessWork  {

	@Trace(dispatcher = true)
	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance process, IpmLifeCycleEvent event) {
		Weaver.callOriginal();
	}
	
	protected WorkItemAbstract newWorkItem(PmWorkUnit var1, PmObjectEvent<PmWorkUnit> var2, short var3, int var4, Boolean var5) {
		WorkItemAbstract workItem = Weaver.callOriginal();
		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			workItem.token = t;
			if(token == null) token = t;
		} else if(t != null) {
			t.expire();
			t = null;
		}
		return workItem;
	}
}
