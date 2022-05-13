package com.newrelic.instrumentation.tibco.pvm_66;

import com.tibco.pvm.api.util.PmLifeCycle.Status;

public class Utils {

	public static final short INFORMATIVE = 1000;

	public static String getStatus(short status) {
		
		switch(status) {
		case Status.ACTIVE:
			return "Active";
		case Status.CANCELLED:
			return "Cancelled";
		case Status.CANCELLING:
			return "Cancelling";
		case Status.COMPLETED:
			return "Completed";
		case Status.COMPLETING:
			return "Completing";
		case Status.FAILED:
			return "Failed";
		case Status.FAILING:
			return "Failing";
			
		case Status.NOT_STARTED:
			return "Not Started";
		
		case Status.STARTING:
			return "Starting";

		case Status.SUSPENDED:
			return "Suspended";
			
		case Status.SUSPENDING:
			return "Suspending";
			
		case Status.HALTED:
			return "Halted";

		case Status.HALTING:
			return "Halting";

		case Status.RESUMING:
			return "Resuming";
			
		case Status.RESTARTING:
			return "Restarting";
			
		case Status.INFORMATIVE:
		return "Informative";
			
		}
		return "Unknown";
	}
}
