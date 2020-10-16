package org.apache.axis2.transport.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public class AbstractAgent {

	@Trace(dispatcher=true)
	public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Weaver.callOriginal();
	}
}
