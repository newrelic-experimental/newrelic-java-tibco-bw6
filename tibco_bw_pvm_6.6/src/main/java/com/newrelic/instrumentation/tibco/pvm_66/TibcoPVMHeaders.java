package com.newrelic.instrumentation.tibco.pvm_66;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.Headers;

public class TibcoPVMHeaders implements Headers {
	
	private Map<String, String> attributes = new HashMap<>();

	@Override
	public void addHeader(String name, String value) {
		attributes.put(name, value);
	}

	@Override
	public boolean containsHeader(String name) {
		return getHeaderNames().contains(name);
	}

	@Override
	public String getHeader(String name) {
		return attributes.get(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return attributes.keySet();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.MESSAGE;
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> list = new ArrayList<>();
		String value = getHeader(name);
		if(value != null && !value.isEmpty()) list.add(value);
		return list;
	}

	@Override
	public void setHeader(String name, String value) {
		attributes.put(name, value);
	}

}
