package org.apache.axis2.transport.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class AxisServlet {

	@Trace(dispatcher=true)
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Axis", new String[] {uri});
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Axis", new String[] {uri});
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher=true)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Axis", new String[] {uri});
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_HIGH, true, "Axis", new String[] {uri});
		Weaver.callOriginal();
	}

}
