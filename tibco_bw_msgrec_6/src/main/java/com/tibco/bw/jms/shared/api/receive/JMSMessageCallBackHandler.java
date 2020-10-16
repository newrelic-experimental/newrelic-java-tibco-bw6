package com.tibco.bw.jms.shared.api.receive;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.expertservices.tibco.jms.InboundWrapper;
import com.newrelic.expertservices.tibco.jms.TibcoUtils;

@Weave(type=MatchType.Interface)
public abstract class JMSMessageCallBackHandler<N> {

	@Trace(dispatcher=true)
	 public void onMessage(N paramN, String paramString, Message message, ManualAcknowledgeSupport manualAcknowledgeSupport) throws JMSException {
		Destination dest = message.getJMSDestination();
		InboundWrapper wrapper = new InboundWrapper(message);
		
		MessageConsumeParameters params = MessageConsumeParameters.library("TibcoJMS").destinationType(TibcoUtils.getDestinationType(dest)).destinationName(TibcoUtils.getDestinationName(dest)).inboundHeaders(wrapper).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		String metricName = TibcoUtils.nameConsumerMetric(dest);
		TibcoUtils.nameTransaction(dest);
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {metricName});
		Weaver.callOriginal();
	 }
}
