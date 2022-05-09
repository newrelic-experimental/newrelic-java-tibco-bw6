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
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),processName,name,"eval"});
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Behavior", new String[] {processName,name});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object enter(PmContext context, PmTask workingTask, PmModelEvent event) {
		Logger logger = NewRelic.getAgent().getLogger();
		String classname = getClass().getSimpleName();
		logger.log(Level.FINE, "Enter {0}.enter", classname);
		logger.log(Level.FINE, "PmContext instance: {0}", context);
		logger.log(Level.FINE, "PmTask instance: {0}", workingTask);
		logger.log(Level.FINE, "PmModelEvent instance: {0}", event);
		String name = workingTask.getName(context);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),"enter",processName,name,"enter"});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object exit(PmContext context, PmTask workingTask, PmModelEvent event) {
		String name = workingTask.getName(context);
		PmProcess process = workingTask.getProcess(context);
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BxActivityBaseBehavior",getClass().getSimpleName(),"enter",processName,name,"exit"});
		return Weaver.callOriginal();
	}
}
