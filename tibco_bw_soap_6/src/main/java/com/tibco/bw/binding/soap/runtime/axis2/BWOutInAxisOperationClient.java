package com.tibco.bw.binding.soap.runtime.axis2;

import org.apache.axis2.client.async.AxisCallback;
import org.apache.axis2.client.async.Callback;
import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@SuppressWarnings("deprecation")
@Weave
abstract class BWOutInAxisOperationClient {

	@Weave
	final private static class _o {
		
		@NewField
		private Token token;
		
		@SuppressWarnings({ "unused" })
		public _o(Callback paramMessageContext, MessageContext paramAxisCallback, AxisCallback arg4) {
			token = NewRelic.getAgent().getTransaction().getToken();
		}
		
		@Trace(async=true)
		public void run() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}
}
