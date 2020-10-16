package com.tibco.bw.runtime;

import java.io.Serializable;
import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class AsyncActivityCompletionNotifier {

	@NewField
	public Token token = null;

	public void setReady(Serializable paramSerializable) {
		if(token !=  null) {
			token.linkAndExpire();
			token = null;
			NewRelic.getAgent().getLogger().log(Level.FINE, "Linked Token in AsyncActivityCompletionNotifier", new Object[0]);
		} else {
			NewRelic.getAgent().getLogger().log(Level.FINE, "No Token to link in AsyncActivityCompletionNotifier", new Object[0]);
		}
		Weaver.callOriginal();
	}
}
