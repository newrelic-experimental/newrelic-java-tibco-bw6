package com.tibco.bx.core.behaviors.process;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmEvent;
import com.tibco.pvm.api.event.PmEventError;
import com.tibco.pvm.api.event.PmLifeCycleEvent;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.api.util.PmLifeCycle;

@Weave
public abstract class BxProcessBehavior {
	
	@NewField
	Token token = null;

	@Trace(async=true)
	public Object enter(PmContext context, PmProcessInstance process, PmModelEvent event) {
		if(token != null) {
			token.link();
		}
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","enter",processName});
		NewRelic.getAgent().getLogger().log(Level.FINE, "BxProcessBehavior.enter() for process {0}, instance {1} and class instance {2}", new Object[] {processName,process,this});
		return Weaver.callOriginal();
	}

	@Trace(async=true)
	public Object exit(PmContext context, PmProcessInstance process, PmModelEvent event) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","exit",processName});
		NewRelic.getAgent().getLogger().log(Level.FINE, "BxProcessBehavior.exit() for process {0}, instance {1} and class instance {2}", new Object[] {processName,process,this});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public PmLifeCycle.Action handleEventError(PmContext context, PmProcessInstance workingObj, PmEventError eventError) {
		String processName = workingObj.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","handleEventError",processName});
		NewRelic.noticeError(eventError.getCause());
		return Weaver.callOriginal();
	}
	
	@Trace
	public boolean handleLifeCycleEvent(PmContext context, PmProcessInstance process, PmLifeCycleEvent event) {
		if(token == null) {
			token = NewRelic.getAgent().getTransaction().getToken();
		}
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","handleLifeCycleEvent",processName});
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public Object handleModelEvent(PmContext context, PmProcessInstance process, PmModelEvent event) {
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","handleModelEvent",processName});
		return Weaver.callOriginal();
	}
	
	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public void handleUncaughtException(PmContext context, PmProcessInstance process, PmWorkUnit target, PmEvent event, Exception unhandledException) {
		String processName = process.getName(context);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BXProcessBehavior","handleUncaughtException",processName});
		NewRelic.noticeError(unhandledException);
		Weaver.callOriginal();
	}
}
