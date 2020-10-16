package org.apache.axis2.client;

import javax.xml.namespace.QName;

import org.apache.axis2.client.async.AxisCallback;
import org.apache.axiom.om.OMElement;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class ServiceClient {

	@Trace(excludeFromTransactionTrace=true)
	public void sendReceiveNonBlocking(QName operation, OMElement elem, AxisCallback callback) {
		if(callback != null && callback.token == null) {
			callback.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
}
