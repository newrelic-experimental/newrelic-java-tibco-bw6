package com.tibco.bx.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bx.api.services.BxInvocationInfo;
import com.tibco.bx.api.services.BxReplyEndpointReference;
import com.tibco.pvm.api.PmProcessDefinition;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.PmTask;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.dataexch.xml.message.PmxWSDLMessage;

@Weave(type=MatchType.ExactClass)
public abstract class BxDispatcherImpl {

	@Trace(dispatcher=true)
	public <T> void onDispatch(String fullServiceName, String operationName, QName messageName, T[] _message, BxReplyEndpointReference replyHandler, BxInvocationInfo invokeInfo) {
		if(replyHandler.token == null) {
			replyHandler.token = NewRelic.getAgent().getTransaction().getToken();
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BxDispatcherImpl",fullServiceName,operationName});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	protected PmProcessInstance startJob(long serviceId, PmTask receive, BxEvalCorrelationSets evalCorrelations, PmProcessDefinition definition, PmContext context, PmxWSDLMessage message, BxReplyEndpointReference endpointReference, BxInvocationInfo invokeInfo, boolean doImmediateReply) throws Exception {
		if(endpointReference.token == null) {
			endpointReference.token = NewRelic.getAgent().getTransaction().getToken();
		}
		List<String> nameList = new ArrayList<String>();
		nameList.add("Custom");
		nameList.add("BxDispatcherImpl");
		nameList.add("startJob");
		String temp = invokeInfo.getAppName();
		if(temp != null && !temp.trim().isEmpty()) {
			nameList.add(temp);
		}
		temp = invokeInfo.getProcessName();
		if(temp != null && !temp.trim().isEmpty()) {
			nameList.add(temp);
		}
		temp = invokeInfo.getComponentModuleName();
		if(temp != null && !temp.trim().isEmpty()) {
			nameList.add(temp);
		}
		temp = invokeInfo.getOperationName();
		if(temp != null && !temp.trim().isEmpty()) {
			nameList.add(temp);
		}
		String[] names = new String[nameList.size()];
		nameList.toArray(names);
		NewRelic.getAgent().getTracedMethod().setMetricName(names);
		return Weaver.callOriginal();
	}
}
