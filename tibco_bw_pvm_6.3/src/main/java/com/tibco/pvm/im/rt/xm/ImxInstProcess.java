package com.tibco.pvm.im.rt.xm;

import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.tibco.pvm_63.TibcoPVMHeaders;
import com.tibco.pvm.api.PmExecMode;
import com.tibco.pvm.api.PmModule;
import com.tibco.pvm.api.PmProcessDefinition;
import com.tibco.pvm.api.PmProcessInstance;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.compiled.IpmCompiledProcess;

@Weave(type = MatchType.BaseClass)
public abstract class ImxInstProcess implements PmProcessInstance {

	@NewField
	public TibcoPVMHeaders headers = new TibcoPVMHeaders();
	
	
	public ImxInstProcess(ImxProcessInfo processInfo, IpmCompiledProcess definition, PmModule ownerModule,
			long primaryId, boolean inline, String uriPrefix) {
		
	}
	
	public PmProcessInstance createSubProcess(PmContext context, PmExecMode execMode, PmModule ownerModule,
			PmProcessDefinition definition, String name, boolean inline) {
		PmProcessInstance returnValue = Weaver.callOriginal();
		((ImxInstProcess)returnValue).headers = headers;
		
		return returnValue;
	}
}
