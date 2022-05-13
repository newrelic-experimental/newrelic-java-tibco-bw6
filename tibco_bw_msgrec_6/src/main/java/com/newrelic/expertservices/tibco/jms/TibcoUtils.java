package com.newrelic.expertservices.tibco.jms;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;

import com.newrelic.api.agent.DestinationType;
import com.newrelic.api.agent.MessageConsumeParameters;
import com.newrelic.api.agent.MessageProduceParameters;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;

public class TibcoUtils {

	public static String nameConsumerMetric(Destination dest) {
		return nameMetric(dest, "Consume");
	}

	public static String nameProducerMetric(Destination dest) {
		return nameMetric(dest, "Produce");
	}

	public static void saveMessageParameters(Message msg) {
		if (msg != null) {
			Map<String,String> map = getMessageParameters(msg);
			Set<String> keys = map.keySet();
			for(String key: keys) {
				String value = map.get(key);
				if(value != null) {
					NewRelic.addCustomParameter(key, value);
				}
			}
		}
	}

	public static Map<String, String> getMessageParameters(Message msg)
	{
		Map<String,String> result = new LinkedHashMap<String, String>(1);
		try
		{
			Enumeration<?> parameterEnum = msg.getPropertyNames();
			if ((parameterEnum == null) || (!parameterEnum.hasMoreElements())) {
				return Collections.emptyMap();
			}

			while (parameterEnum.hasMoreElements()) {
				String key = (String)parameterEnum.nextElement();
				Object val = msg.getObjectProperty(key);

				result.put(key, val == null ? null : val.toString());
			}
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Unable to capture JMS message property", new Object[0]);
		}

		return result;
	}

	public static void nameTransaction(Destination dest) {
		try {
			if ((dest instanceof Queue)) {
				Queue queue = (Queue)dest;
				if ((queue instanceof TemporaryQueue)) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Message", new String[] { "JMS/Queue/Temp" });
				}
				else {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Message", new String[] { "JMS/Queue/Named", queue.getQueueName() });
				}
			}
			else if ((dest instanceof Topic)) {
				Topic topic = (Topic)dest;
				if ((topic instanceof TemporaryTopic)) {
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Message", new String[] { "JMS/Topic/Temp" });
				}
				else
					NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Message", new String[] { "JMS/Topic/Named", topic.getTopicName() });
			}
			else
			{
				NewRelic.getAgent().getLogger().log(Level.FINEST, "Error naming JMS transaction: Invalid Message Type.", new Object[0]);
				NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Message", new String[] { "JMS", "Unknown Destination" });
				
			}
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error naming JMS transaction", new Object[0]);
		}

	}

	public static void nameTransaction(Destination dest,Transaction transaction) {
		try {
			if ((dest instanceof Queue)) {
				Queue queue = (Queue)dest;
				if ((queue instanceof TemporaryQueue)) {
					transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Message", new String[] { "JMS/Queue/Temp" });
				}
				else {
					transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Message", new String[] { "JMS/Queue/Named", queue.getQueueName() });
				}
			}
			else if ((dest instanceof Topic)) {
				Topic topic = (Topic)dest;
				if ((topic instanceof TemporaryTopic)) {
					transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "Message", new String[] { "JMS/Topic/Temp" });
				}
				else
					transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, false, "Message", new String[] { "JMS/Topic/Named", topic.getTopicName() });
			}
			else
			{
				NewRelic.getAgent().getLogger().log(Level.FINEST, "Error naming JMS transaction: Invalid Message Type.", new Object[0]);
			}
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Error naming JMS transaction", new Object[0]);
		}

	}

	static String nameMetric(Destination dest, String operation) {
		String metricName = null;

		if(dest instanceof Queue) {
			Queue queue = (Queue)dest;
			try {
				if(queue instanceof TemporaryQueue) {
					metricName = MessageFormat.format("TIBCO/JMS/{0}/{1}/Temp", new Object[] {"Queue", operation});
				} else {
					metricName = MessageFormat.format("TIBCO/JMS/{0}/{1}/Named/{2}", new Object[] { "Queue", operation, queue.getQueueName() });
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} else if(dest instanceof Topic) {
			Topic topic = (Topic)dest;
			try {
				if ((topic instanceof TemporaryTopic)) {
					metricName = MessageFormat.format("TIBCO/JMS/{0}/{1}/Temp", new Object[] { "Topic", operation });
				} else {
					metricName = MessageFormat.format("TIBCO/JMS/{0}/{1}/Named/{2}", new Object[] { "Topic", operation, topic.getTopicName() });
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		} 
		return metricName;
	}

	public static void processSendMessage(Message message, Destination dest, TracedMethod tracer) {
		if(message != null && tracer != null) {
			
			DestinationType destinationType = getDestinationType(dest);
			String destinationName = getDestinationName(dest);
			MessageProduceParameters params = MessageProduceParameters.library("TibcoJMS").destinationType(destinationType).destinationName(destinationName).outboundHeaders(new OutboundWrapper(message)).build();
			NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
			String metricName = nameProducerMetric(dest);
			tracer.setMetricName(metricName);
		}
	}

	public static DestinationType getDestinationType(Destination dest) {
		if ((dest instanceof Queue)) {
			Queue queue = (Queue)dest;
			if ((queue instanceof TemporaryQueue)) {
				return DestinationType.TEMP_QUEUE;
			}
			else {
				return DestinationType.NAMED_QUEUE;
			}
		}
		else if ((dest instanceof Topic)) {
			Topic topic = (Topic)dest;
			if ((topic instanceof TemporaryTopic)) {
				return DestinationType.TEMP_TOPIC;
			}
			else
				return DestinationType.NAMED_TOPIC;
		}
		else
		{
			return null;
		}
		
	}


	public static String getDestinationName(Destination dest) {
		String destName = "Unknown JMS Destination";
		try {
			if(Queue.class.isInstance(dest)) {
				destName = ((Queue)dest).getQueueName();
			} else if(Topic.class.isInstance(dest)) {
				destName = ((Topic)dest).getTopicName();
			}
		} catch (JMSException e) {
			NewRelic.getAgent().getLogger().log(Level.FINEST, e, "Unable to get the JMS message destination name. ({0})", new Object[]{dest});
		}
		return destName;
	}
	
	public static void processInboundResponseHeaders(Message message,Destination dest, TracedMethod tracer)
	{
		MessageConsumeParameters params = MessageConsumeParameters.library("TibcoJMS").destinationType(getDestinationType(dest)).destinationName(getDestinationName(dest)).inboundHeaders(new InboundWrapper(message)).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
	}
}
