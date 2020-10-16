package com.tibco.bw.binding.soap.runtime.reference;

import javax.xml.namespace.QName;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.core.runtime.api.MessageContext;
import com.tibco.bw.core.runtime.api.RequestContext;

@Weave
public abstract class ReferenceRequestHandler {

	public abstract String getEndpointName();
	
	@Trace
	public <N> void invoke(MessageContext msgCtx, N[] paramArrayOfN) {
		
		RequestContext requestCtx = msgCtx.getRequestContext();
		QName opName;
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if (requestCtx != null) {
			opName = requestCtx.getOperationName();
			String contextID = requestCtx.getContextID();
			if (contextID != null) {
				NewRelic.addCustomParameter("Context ID",contextID);
			}
			traced.setMetricName(new String[] {"Custom/ReferenceRequestHandler",opName.getLocalPart()});
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "ReferenceRequestHandler", new String[] {"ReferenceRequestHandler",getEndpointName(),opName.getLocalPart()});
		}
				
		Weaver.callOriginal();
	}
}
