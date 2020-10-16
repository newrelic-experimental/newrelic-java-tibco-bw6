package org.apache.axis2.engine;

import java.util.logging.Level;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;

import com.newrelic.api.agent.Logger;
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
		Logger logger = NewRelic.getAgent().getLogger();
		logger.log(Level.FINE, "In Phase: {0}", new Object[] {getClass().getCanonicalName()});
		logger.log(Level.FINE, "Name: {0}", new Object[] {getName()});
		logger.log(Level.FINE, "Phase - phase name: {0}", new Object[] {getPhaseName()});

		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom",getName(),msgContext.getSoapAction(),getPhaseName()});
		return Weaver.callOriginal();
	}
	
}
