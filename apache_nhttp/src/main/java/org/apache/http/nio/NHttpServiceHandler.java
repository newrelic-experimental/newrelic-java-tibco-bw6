package org.apache.http.nio;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.apache.nhttp.InboundWrapper;
import com.nr.instrumentation.apache.nhttp.OutboundWrapper;

@Weave(type=MatchType.Interface)
public abstract class NHttpServiceHandler {
	
	@NewField
	private Token token = null;

	@SuppressWarnings("deprecation")
	@Trace(dispatcher=true)
	public void requestReceived(NHttpServerConnection nHttpServerConnection) {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		transaction.convertToWebTransaction();
		if(token == null) {
			token = transaction.getToken();
		}
		HttpRequest request = nHttpServerConnection.getHttpRequest();
		if(request != null) {
			InboundWrapper wrapper = new InboundWrapper(request);
			
			AgentBridge.getAgent().getTransaction().provideHeaders(wrapper);
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public void responseReady(NHttpServerConnection nHttpServerConnection) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		HttpResponse response = nHttpServerConnection.getHttpResponse();
		if(response != null) {
			OutboundWrapper wrapper = new OutboundWrapper(response);
			NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		}
		Weaver.callOriginal();
	}


}
