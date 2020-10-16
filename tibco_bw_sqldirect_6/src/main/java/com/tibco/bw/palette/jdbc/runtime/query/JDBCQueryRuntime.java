package com.tibco.bw.palette.jdbc.runtime.query;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class JDBCQueryRuntime<N> {

	@Weave
	static abstract class JDBCQueryExeutor {
		
		@NewField
		private Token token = null;
		
		@WeaveAllConstructors
		public JDBCQueryExeutor() {
			if(token == null) {
				token = NewRelic.getAgent().getTransaction().getToken();
			}
		}
		
		@Trace(async=true)
		public void run() {
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}
}
