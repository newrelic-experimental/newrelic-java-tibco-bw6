package com.tibco.bw.palette.http.runtime.processors;

import org.genxdm.ProcessingContext;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.runtime.ActivityContext;
import com.tibco.bw.sharedresource.http.api.TransportMessage;

@Weave
public abstract class HTTPServerRequestProcessor<N> {

	@Trace(dispatcher=true)
	public void partialReply(N paramN, ProcessingContext<N> paramProcessingContext, ActivityContext<N> activityContext) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void reply(N paramN, ProcessingContext<N> paramProcessingContext, ActivityContext<N> activityContext) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void sendLastMessage(TransportMessage transportMessage, boolean paramBoolean) {
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	public void sendMessage(TransportMessage transportMessage, boolean paramBoolean) {
		Weaver.callOriginal();
		
	}
}
