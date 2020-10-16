package org.apache.axis2.client;

import org.apache.axis2.client.async.AxisCallback;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class OperationClient {

	
	public final void setCallback(AxisCallback callback) {
		if(callback.token == null) {
			callback.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
	
	@Trace
	public abstract void executeImpl(boolean b); 
}
