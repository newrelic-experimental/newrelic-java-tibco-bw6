package com.tibco.pvm.infra.api.impl.session;

import com.newrelic.api.agent.weaver.Weave;
import com.tibco.pvm.api.session.PmSession;

@Weave
public abstract class IpmContextImpl {

	IpmContextImpl(PmSession session, IpmSysContextImpl sysContext) {
	}
}
