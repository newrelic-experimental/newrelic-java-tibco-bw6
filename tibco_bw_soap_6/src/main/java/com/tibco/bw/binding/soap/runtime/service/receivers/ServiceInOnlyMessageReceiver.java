package com.tibco.bw.binding.soap.runtime.service.receivers;

import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.binding.soap.runtime.service.ServiceRequestHandler;

@Weave
public abstract class ServiceInOnlyMessageReceiver {

	@Trace
	protected  void invokeBusinessLogic(MessageContext paramMessageContext) {
		ServiceRequestHandler localServiceRequestHandler = (ServiceRequestHandler)paramMessageContext.getAxisService().getParameterValue("requestHandler");
		if(localServiceRequestHandler != null) {
			String endpoint = localServiceRequestHandler.getEndpointName();
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","ServiceInOnlyMessageReceiver","invokeBusinessLogic",endpoint});
		}
		Weaver.callOriginal();
	}

}
