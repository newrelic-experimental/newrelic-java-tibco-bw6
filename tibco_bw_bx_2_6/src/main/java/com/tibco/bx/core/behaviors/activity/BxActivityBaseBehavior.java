package com.tibco.bx.core.behaviors.activity;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcess;
import com.tibco.pvm.api.PmTask;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.session.PmContext;

@Weave(type=MatchType.BaseClass)
public abstract class BxActivityBaseBehavior {

	@Trace
	public Object eval(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Name", name);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		traced.addCustomAttribute("ProcessName", processName);
		traced.setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),"eval"});
		
		return Weaver.callOriginal();
	}
	
	@Trace
	public Object enter(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Name", name);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		traced.addCustomAttribute("ProcessName", processName);
		traced.setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),"enter"});
		return Weaver.callOriginal();
	}
	
	@Trace
	public Object exit(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Name", name);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		traced.addCustomAttribute("ProcessName", processName);
		traced.setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),"exit"});
		return Weaver.callOriginal();
	}
}
