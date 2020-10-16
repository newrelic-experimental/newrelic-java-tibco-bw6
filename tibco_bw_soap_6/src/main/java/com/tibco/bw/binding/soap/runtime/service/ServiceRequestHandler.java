package com.tibco.bw.binding.soap.runtime.service;

import java.util.logging.Level;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.MessageContext;
import com.tibco.bw.core.runtime.api.RequestContext;

@Weave(type=MatchType.ExactClass)
public abstract class ServiceRequestHandler {

	public abstract String getEndpointName();

	@Trace(dispatcher=true)
	public <N> void invoke(ServiceMessageContext serviceMessageContext)  {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Enter ServiceRequestHandler:invoke(ServiceMessageContext)", new Object[0]);
		String operation = serviceMessageContext.getAxisInMessageContext().getOperationContext().getOperationName();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "ServiceRequestHandler", new String[] {"ServiceRequestHandler",getEndpointName(),operation});
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ServiceRequestHandler","ServiceMessageContext",getEndpointName(),operation});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public <N> void invoke(MessageContext messageContext, N[] requestMessage) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Enter ServiceRequestHandler:invoke(MessageContext)", new Object[0]);
		
		String operation = "Unknown";
		RequestContext requestCtx = messageContext.getRequestContext();
		if(requestCtx != null) {
			QName opName = requestCtx.getOperationName();
			operation = opName.getLocalPart();
		}
		
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.setMetricName(new String[] {"Custom","ServiceRequestHandler","MessageContext",getEndpointName(),operation});
		traced.addRollupMetricName(new String[] {"Custom","ServiceRequestHandler",getEndpointName()});
		
		Weaver.callOriginal();
	}

}
