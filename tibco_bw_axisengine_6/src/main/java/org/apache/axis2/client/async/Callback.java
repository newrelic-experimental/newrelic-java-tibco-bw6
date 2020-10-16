package org.apache.axis2.client.async;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Callback {

	@NewField
	public com.newrelic.api.agent.Token token = null;

	@SuppressWarnings("deprecation")
	@Trace(async=true)
	public void onComplete(AsyncResult paramAsyncResult) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public void onError(Exception paramException) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

}
