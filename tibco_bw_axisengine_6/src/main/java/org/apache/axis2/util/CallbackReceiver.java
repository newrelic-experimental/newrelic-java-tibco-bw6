package org.apache.axis2.util;

import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.client.async.Callback;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class CallbackReceiver {

	public void addCallback(String msgID, AxisCallback callback) {
		if(callback.token == null) {
			callback.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
	
	public void addCallback(String msgID, Callback callback) {
		if(callback.token == null) {
			callback.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
}
