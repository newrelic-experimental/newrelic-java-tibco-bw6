package com.tibco.pvm.infra.api.util.director;
        
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransportType;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.tibco.pvm_66.TibcoPVMHeaders;
import com.tibco.pvm.api.PmProcess;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmWorkUnit;
import com.tibco.pvm.api.event.PmLifeCycleEvent;
import com.tibco.pvm.api.event.PmModelEvent;
import com.tibco.pvm.api.event.PmObjectEvent;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.im.rt.xm.ImxInstProcess;
import com.tibco.pvm.infra.api.event.IpmLifeCycleEvent;

@Weave(type=MatchType.Interface)
public abstract class IpmEventDirector {

	@SuppressWarnings("rawtypes")
	@Trace
	public Object handleEvent(PmContext context, PmWorkUnit target, PmObjectEvent event) {
		
		HashMap<String, Object> attributes = new HashMap<>();
		
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			} else {
				iProcess.headers = new TibcoPVMHeaders();
			}
			
		}
		String name = target != null ? target.getName(context) : "null";
		attributes.put("Target-Name", name != null ? name : "null");
		PmProcess process = target != null ? target.getProcess(context) : null;
		
		String processName = process != null ? process.getName(context) : "null";
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"handleEvent"});
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}

	@Trace
	public void scheduleLifeCycleEvent(PmContext context, PmProcessInstance target, IpmLifeCycleEvent event) {
		HashMap<String, Object> attributes = new HashMap<>();
				
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleLifeCycleEvent"});
		Weaver.callOriginal();
	}


	@Trace
	public void scheduleModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event, short paramShort, Boolean paramBoolean) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"scheduleModelEvent"});
		Weaver.callOriginal();
	}

	@Trace
	public Object invokeModelEvent(PmContext context, PmWorkUnit target, PmModelEvent event) {
		HashMap<String, Object> attributes = new HashMap<>();
		
		if(target instanceof ImxInstProcess) {
			ImxInstProcess iProcess = (ImxInstProcess)target;
			if(iProcess.headers != null) {
				NewRelic.getAgent().getTransaction().acceptDistributedTraceHeaders(TransportType.Other, iProcess.headers);
			}
		}
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"invokeModelEvent"});
		return Weaver.callOriginal();

	}


	@Trace
	public boolean afterLifeCycleEvent(PmContext context, PmProcessInstance target, PmLifeCycleEvent lifeCycleEvent) {
		HashMap<String, Object> attributes = new HashMap<>();
		String name = target.getName(context);
		attributes.put("Name", name);
		PmProcess process = target.getProcess(context);

		String processName = process.getName(context);
		attributes.put("ProcessName", processName);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getClass().getSimpleName(),"afterLifeCycleEvent",processName,name});
		return Weaver.callOriginal();
	}

}
