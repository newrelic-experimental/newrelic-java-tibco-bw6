package com.tibco.bw.palette.restjson.model.restjson;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class RestInvokeActivity {

	@NewField
	public Token token;
	
	
}
