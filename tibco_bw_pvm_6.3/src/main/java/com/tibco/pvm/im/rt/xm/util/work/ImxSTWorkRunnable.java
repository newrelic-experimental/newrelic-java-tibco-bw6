package com.tibco.pvm.im.rt.xm.util.work;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.api.session.PmContext;
import com.tibco.pvm.infra.services.engine.impl.STWorkRunnable;
import com.tibco.pvm.infra.support.work.Work;

@Weave
public abstract class ImxSTWorkRunnable extends STWorkRunnable  {

	
	public ImxSTWorkRunnable(PmContext context, Work work, int priority, int engineStepCount) {
		super(context, work, priority);
	}

	@Trace(async=true)
	public boolean doWorkBlock() {
		if(m_work.token != null) {
			m_work.token.linkAndExpire();
			m_work.token = null;
		}
		NewRelic.getAgent().getLogger().log(Level.INFO, "Enter {0}.doWorkBlock", getClass().getName());
		boolean b = Weaver.callOriginal();
		return b;
	}
}
