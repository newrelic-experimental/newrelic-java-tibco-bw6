package com.tibco.ftl;

import java.util.concurrent.TimeUnit;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class EventQueue {

	@Trace(dispatcher=true)
	public abstract void dispatch() throws FTLException;

	@Trace(dispatcher=true)
	public abstract void dispatch(long paramLong, TimeUnit paramTimeUnit) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void dispatch(double paramDouble) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void dispatchNow() throws FTLException;

}
