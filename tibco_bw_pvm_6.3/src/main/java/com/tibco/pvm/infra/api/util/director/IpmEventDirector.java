package com.tibco.pvm.infra.api.util.director;
        
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.tibco.pvm_63.TibcoPVMHeaders;
import com.tibco.pvm.api.PmProcess;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmLifeCycleEvent;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.im.rt.xm.ImxInstControl;
import com.tibco.pvm.im.rt.xm.ImxInstProcess;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave(type=MatchType.Interface)
public abstract class IpmEventDirector {

	@SuppressWarnings("rawtypes")
	@Trace(dispatcher=true)
	public Object handleEvent(PmContext context, PmWorkUnit target, PmObjectEvent event) {
		
		HashMap<String, Object> attributes = new HashMap<>();
		
		attributes.put("Target-Class", target != null ? target.getClass().getName() : "null");
		attributes.put("Event-Class", event != null ? event.getClass().getName() : "null");
		
		ImxInstProcess instProcess = null;
		if(target instanceof ImxInstProcess) {
			instProcess = (ImxInstProcess)target;
		} else if(target instanceof ImxInstControl) {
			instProcess = (ImxInstProcess) ((ImxInstControl)target).getProcess(context);
		}
		
		if(instProcess != null) {
			ImxInstProcess iProcess = instProcess;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
				attributes.put("Headers-Size",iProcess.headers.getHeaderNames().size());
			} else {
				iProcess.headers = new TibcoPVMHeaders();
				attributes.put("Headers-Size",iProcess.headers.getHeaderNames().size());
			}
			attributes.put("Is ImxInstProcess", true);
			
		} else {
			attributes.put("Is-ImxInstProcess", false);
		}
		String name = target != null ? target.getName(context) : "null";
		attributes.put("Target-Name", name != null ? name : "null");
		PmProcess process = target != null ? target.getProcess(context) : null;
		
		String processName = process != null ? process.getName(context) : "null";
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"handleEvent",processName,name});
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance target, IpmLifeCycleEvent event) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		attributes.put("Target", target);
		attributes.put("Event", event);
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
			attributes.put("Is ImxInstProcess", true);
		} else {
			attributes.put("Is ImxInstProcess", false);
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleLifeCycleEvent",processName,name});
		Weaver.callOriginal();
	}


	@Trace(dispatcher=true)
	public void scheduleModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event, short paramShort, Boolean paramBoolean) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		attributes.put("Target", target);
		attributes.put("Event", event);
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
			attributes.put("Is ImxInstProcess", true);
		} else {
			attributes.put("Is ImxInstProcess", false);
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleModelEvent",processName,name});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public Object invokeModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		attributes.put("Target", target);
		attributes.put("Event", event);
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
			attributes.put("Is ImxInstProcess", true);
		} else {
			attributes.put("Is ImxInstProcess", false);
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
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
