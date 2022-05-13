package com.tibco.pvm.im.rt.xm.util.work;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.pvm.infra.support.work.Work;

@Weave
public abstract class ImxWorkQueueManager {

	@Trace
	public void scheduleWork(Work work, short priority, short requestType) {
		Weaver.callOriginal();
	}
}
