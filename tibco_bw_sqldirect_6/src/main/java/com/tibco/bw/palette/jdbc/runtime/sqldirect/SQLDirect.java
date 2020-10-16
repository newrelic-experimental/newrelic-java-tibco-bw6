package com.tibco.bw.palette.jdbc.runtime.sqldirect;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.tibco.bw.runtime.AsyncActivity;

@Weave
public abstract class SQLDirect<N> extends AsyncActivity<N> {
	
	
	@Weave
	static class SQLDMLExecutor {
		
		@Trace(dispatcher=true)
		public void run() {
			Weaver.callOriginal();
		}
	}
}
