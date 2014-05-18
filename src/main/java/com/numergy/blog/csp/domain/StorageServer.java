package com.numergy.blog.csp.domain;

import java.util.ArrayList;
import java.util.List;

public class StorageServer {
	private String name;
	private int capacity;
	private List<VirtualVolume> volumes = new ArrayList<>();
	
	public StorageServer(String name, int capacity) {
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
	
	public List<VirtualVolume> getVolumes() {
		return volumes;
	}
	
	public void setVolumes(List<VirtualVolume> volumes) {
		this.volumes = volumes;
	}
	
	public int getStorageLoad() {
		int load = 0;
		for(VirtualVolume volume : volumes) {
			load += volume.getCapacity();
		}
		return load;
	}
}
