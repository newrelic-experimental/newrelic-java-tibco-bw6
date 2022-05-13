package com.tibco.bx.api.services;

import java.util.Map;

import javax.security.auth.Subject;

import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.nr.instrumentation.bx.TibcoBXHeaders;

@Weave
public abstract class BxInvocationInfo {
	
	@NewField
	public TibcoBXHeaders headers = new TibcoBXHeaders();

	public abstract String getOperationName();
	
	public abstract Map<String, Object> getInputContextVariables();
	
	public abstract void setInputContextVariable(final String paramName, final Object value);
	
	public abstract String getAppName();
	
	public abstract String getProcessName();
	
	public abstract String getComponentModuleName();
	
	public BxInvocationInfo() {
	}

	public BxInvocationInfo(String operationName, BxMessageType messageType) {
	}

	public BxInvocationInfo(String operationName, BxMessageType messageType, String appName, String appVersion,
			String componentModuleName, String componentModuleVersion) {
	}

	public BxInvocationInfo(String operationName, Subject subject, String correlationId, String contextId,
			String parentContextId, BxMessageType messageType) {
	}
}
