package com.tibco.bx.management;

import java.util.ArrayList;
import java.util.List;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class BxManagementAideImpl {

	@Trace(dispatcher=true)
	public String createProcessInstance(String appName, String appVersion, String moduleName, String moduleVersion, String serviceName, String operationName, String[] message, boolean listenForResponse) {
		List<String> list = new ArrayList<String>();
		list.add("Custom");
		list.add("BxManagementAideImpl");
		list.add("createProcessInstance");
		if(appName != null && !appName.isEmpty()) {
			list.add(appName);
		}
		if(moduleName != null && !moduleName.isEmpty()) {
			list.add(moduleName);
		}
		if(serviceName != null && !serviceName.isEmpty()) {
			list.add(serviceName);
		}
		if(operationName != null && !operationName.isEmpty()) {
			list.add(operationName);
		}
		String[] names = new String[list.size()];
		list.toArray(names);
		NewRelic.getAgent().getTracedMethod().setMetricName(names);
		return Weaver.callOriginal();
	}
}
