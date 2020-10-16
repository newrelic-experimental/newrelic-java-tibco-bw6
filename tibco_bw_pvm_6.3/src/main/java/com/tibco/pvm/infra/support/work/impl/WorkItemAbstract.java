package com.tibco.pvm.infra.support.work.impl;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.work.Work;

@Weave(type=MatchType.BaseClass)
public abstract class WorkItemAbstract {

	public abstract PmWorkUnit getTarget(PmContext context);
	protected transient Work m_work = Weaver.callOriginal();
	

	  @Trace(async=true)
	  public Object execute(PmContext paramPmContext) {
		  if(m_work.token != null) {
			  m_work.token.linkAndExpire();
			  m_work.token = null;
		  }
		  String name = getTarget(paramPmContext).getName(paramPmContext);
		  if(name != null && !name.isEmpty()) {
			  NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","WorkUnit",name});
			  NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW,false,"WorkUnit",new String[] {"Custom","WorkUnit",name});
		  }
		  return Weaver.callOriginal();
	  }
	  
	  public void setWork(Work work)
	  {
		  Weaver.callOriginal();
		  if(m_work.token == null) {
			  m_work.token = NewRelic.getAgent().getTransaction().getToken();
		  }
	  }
}
