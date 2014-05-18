package com.numergy.blog.csp.util;

import java.util.ArrayList;
import java.util.List;

import com.numergy.blog.csp.domain.StorageServer;
import com.numergy.blog.csp.domain.VirtualVolume;

public class StorageFactory {
	public static List<VirtualVolume> buildVirtualVolumes(int[] volumesCapacities) {		
		List<VirtualVolume> volumes = new ArrayList<>(volumesCapacities.length);
		
		for(int i=0; i<volumesCapacities.length; i++) {
			volumes.add(new VirtualVolume("Volume " + i, volumesCapacities[i]));
		}
		
		return volumes;
	}
	
	public static List<StorageServer> buildStorageServer(int[] capacities) {		
		List<StorageServer> servers = new ArrayList<>(capacities.length);
		
		for(int i=0; i<capacities.length; i++) {
			servers.add(new StorageServer("Server " + i, capacities[i]));
		}
		
		return servers;
	}
}
