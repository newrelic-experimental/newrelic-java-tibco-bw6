package com.tibco.ftl;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class Publisher {

	@Trace(dispatcher=true)
	public abstract void send(Message paramMessage) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void send(Message[] paramArrayOfMessage) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void send(Message[] paramArrayOfMessage, int paramInt) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void sendToInbox(Inbox paramInbox, Message paramMessage) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void send_eFTLRequest(Inbox paramInbox, Message paramMessage) throws FTLException;

	@Trace(dispatcher=true)
	public abstract void send_eFTLReply(Message paramMessage1, Message paramMessage2) throws FTLException;

}
