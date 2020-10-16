package com.tibco.bx.core.behaviors.activity;

import java.util.logging.Level;

import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcess;
import com.tibco.pvm.api.PmTask;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.session.PmContext;

@Weave(type=MatchType.BaseClass)
public abstract class BxActivityBaseBehavior {

	@Trace(dispatcher=true)
	public Object eval(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),processName,name});
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Behavior", new String[] {processName,name});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object enter(PmContext context, PmTask workingTask, PmModelEvent event) {
		Logger logger = NewRelic.getAgent().getLogger();
		String classname = getClass().getSimpleName();
		logger.log(Level.FINE, "Enter {0}.enter", classname);
		logger.log(Level.FINE, "PmContext class: {0}", context.getClass().getName());
		logger.log(Level.FINE, "PmTask class: {0}", workingTask.getClass().getName());
		logger.log(Level.FINE, "PmModelEvent class: {0}", event.getClass().getName());
		String name = workingTask.getName(context);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"enter",processName,name});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object exit(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"enter",processName,name});
		return Weaver.callOriginal();
	}
}
