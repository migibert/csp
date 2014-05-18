package com.numergy.blog.csp.domain;

public class VirtualVolume {
	private String name;
	private int capacity;
	private int usedCapacity;

	public VirtualVolume(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

}
