package com.tibco.pvm.infra.api.util.director;
        
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcess;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmLifeCycleEvent;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave(type=MatchType.Interface)
public abstract class IpmEventDirector {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public Object handleEvent(PmContext context, PmWorkUnit target, PmObjectEvent event) {
		String name = target.getName(context);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		Exception e = new Exception("call to IpmEventDirector.handleEvent");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "call to {0}.handleEvent, process: {1}, name: {2}",getClass().getName(),processName,name);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"handleEvent",processName,name});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance target, IpmLifeCycleEvent event) {
		String name = target.getName(context);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleLifeCycleEvent",processName,name});
		Weaver.callOriginal();
	}


	@Trace(dispatcher=true)
	public void scheduleModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event, short paramShort, Boolean paramBoolean) {
		String name = target.getName(context);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleModelEvent",processName,name});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Object invokeModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event) {
		String name = target.getName(context);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"invokeModelEvent",processName,name});
		return Weaver.callOriginal();

	}


	@Trace(dispatcher=true)
	public boolean afterLifeCycleEvent(PmContext context, PmProcessInstance target, PmLifeCycleEvent lifeCycleEvent) {
		String name = target.getName(context);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"afterLifeCycleEvent",processName,name});
		return Weaver.callOriginal();
	}

}
