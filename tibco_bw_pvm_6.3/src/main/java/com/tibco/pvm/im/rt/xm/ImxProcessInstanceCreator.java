package com.tibco.pvm.im.rt.xm;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.PmModule;
import com.tibco.pvm.api.PmProcessDefinition;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.session.PmContext;

@Weave
public abstract class ImxProcessInstanceCreator {

	@Trace(dispatcher = true)
	public PmProcessInstance createInstance(PmContext context, PmModule ownerModule, PmProcessDefinition definition,
			String name) {
		PmProcessInstance returned = Weaver.callOriginal();
		
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(((ImxInstProcess)returned).headers);
		return returned;
	}
	
	@Trace(dispatcher = true)
	public ImxInstProcess createInstance(PmContext context, PmModule ownerModule, PmProcessDefinition definition,
			String name, ImxProcessInfo processInfo) {
		ImxInstProcess returned = Weaver.callOriginal();
		NewRelic.getAgent().getTransaction().insertDistributedTraceHeaders(returned.headers);
		return returned;
	}
}
