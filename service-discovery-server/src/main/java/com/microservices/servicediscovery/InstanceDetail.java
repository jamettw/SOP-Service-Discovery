package com.microservices.servicediscovery;

import java.util.HashMap;

public class InstanceDetail {
	private String instanceName;
	private String instanceStatus;
	
	
	
	public InstanceDetail(String instanceName, String instanceStatus) {
		super();
		this.instanceName = instanceName;
		this.instanceStatus = instanceStatus;
	}



	public String getInstanceName() {
		return instanceName;
	}



	public String getInstanceStatus() {
		return instanceStatus;
	}


	
}
