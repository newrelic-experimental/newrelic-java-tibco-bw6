package org.apache.axis2.engine;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class Phase {

	public abstract String getName();
	public abstract String getPhaseName();
	
	@Trace(dispatcher=true)
	public Handler.InvocationResponse invoke(MessageContext msgContext) throws AxisFault {

		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getName(),msgContext.getSoapAction(),getPhaseName()});
		return Weaver.callOriginal();
	}
	
}
