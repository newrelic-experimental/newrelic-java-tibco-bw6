package com.tibco.bw.palette.restjson.runtime;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.palette.restjson.model.restjson.RestInvokeActivity;
import com.tibco.bw.runtime.AsyncActivityController;
import com.tibco.bw.runtime.ProcessContext;

@Weave
public class RestInvoke<N> {
	
	public RestInvokeActivity model = Weaver.callOriginal();

	public void execute(N input, ProcessContext<N> pc, AsyncActivityController controller) {
		if(model.token == null) {
			model.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}

	@Weave
	public static class RestInvokeRunnable {
		
		private final RestInvokeActivity model = Weaver.callOriginal();
		
		@Trace
		public void run() {
			if(model.token != null) {
				model.token.linkAndExpire();
				model.token = null;
			}
			Weaver.callOriginal();
		}
	}
}
