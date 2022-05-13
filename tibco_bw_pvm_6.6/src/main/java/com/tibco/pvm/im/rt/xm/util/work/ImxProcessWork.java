       package com.tibco.pvm.im.rt.xm.util.work;

import java.util.HashMap;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.tibco.pvm_66.Utils;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.PmWuId;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.im.rt.xm.ImxInstProcess;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave(type = MatchType.BaseClass)
public abstract class ImxProcessWork  {

	public void scheduleEvent(PmContext context, PmWorkUnit target, PmObjectEvent<PmWorkUnit> event, short priority,
			int details, Boolean paused) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		if(target instanceof ImxInstProcess) {
			NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(((ImxInstProcess)target).headers);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace
	public boolean invokeLifeCycleEvent(PmContext context, PmProcessInstance target, IpmLifeCycleEvent event) {
		String processInstanceName = target.getName(context);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(processInstanceName != null) {
			traced.addCustomAttribute("ProcessInstance", processInstanceName);
		}
		PmWuId id = target.getId();
		if(id != null) {
			traced.addCustomAttribute("WorkURI", id.getURI(context));
		}
		short stat = target.getLifeCycleStatus(context);
		traced.addCustomAttribute("LifeCycleStatus", Utils.getStatus(stat));
		return Weaver.callOriginal();
	}
	
	@Trace
	public Object invokeModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event) {
		String processInstanceName = target.getName(context);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(processInstanceName != null) {
			traced.addCustomAttribute("ProcessInstance", processInstanceName);
		}
		PmWuId id = target.getId();
		if(id != null) {
			traced.addCustomAttribute("WorkURI", id.getURI(context));
		}
		return Weaver.callOriginal();
	}
}
