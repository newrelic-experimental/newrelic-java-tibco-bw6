package com.tibco.pvm.im.rt.xm.util.work;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.support.work.Work;

@Weave
public abstract class ImxSTWorkRunnable {
	
	
	@NewField
	private Token token = null;

	public ImxSTWorkRunnable(PmContext context, Work work, int priority, int engineStepCount) {
		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			token = t;
		} else if(t != null) {
			t.expire();
			t = null;
		}
	}
	
	@Trace(async = true)
	public void run() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace
	public boolean doWorkBlock() {
		boolean b = Weaver.callOriginal();
		return b;
	}
}
