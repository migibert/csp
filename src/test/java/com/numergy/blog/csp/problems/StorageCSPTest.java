package com.numergy.blog.csp.problems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.numergy.blog.csp.domain.StorageServer;
import com.numergy.blog.csp.domain.VirtualVolume;
import com.numergy.blog.csp.problems.storage.StorageCSP;
import com.numergy.blog.csp.problems.storage.StorageCSPFactory;
import com.numergy.blog.csp.util.StorageFactory;

public class StorageCSPTest {
	@Test
	public void testStoragePacking_nominal() {
		final int[] serversStorageCapacities = { 1000, 1000, 1000, 1000 };	
		final int[] volumesCapacities = { 100, 100, 100, 250, 250, 450, 450, 600, 600 };
		
		
		List<VirtualVolume> volumes = StorageFactory.buildVirtualVolumes(volumesCapacities);
		List<StorageServer> servers = StorageFactory.buildStorageServer(serversStorageCapacities);
		
		StorageCSP csp = StorageCSPFactory.build(volumes, servers);
		boolean feasible = csp.packStorageResources();
		assertTrue(feasible);
		
		StorageCSPFactory.buildResult(csp, servers, volumes);
		
		int totalVirtualCapacity = 0;
		for(int volumeCapacity : volumesCapacities) {
			totalVirtualCapacity += volumeCapacity;			
		}
			
		int totalServerStorageCapacity = 0;
		for(StorageServer server : servers) {
			assertTrue(server.getStorageLoad() <= server.getCapacity());
			totalServerStorageCapacity += server.getStorageLoad();
		}
		
		assertEquals(totalVirtualCapacity, totalServerStorageCapacity);
		
		System.out.println(csp);
	}
	
	@Test
	public void testStoragePacking_surcontrainte_storage() {
		final int[] serversStorageCapacities = { 1000, 1000, 1000, 1000 };	
		final int[] volumesCapacities = { 100, 100, 100, 250, 250, 450, 450, 600, 600, 600, 750 };
		
		
		List<VirtualVolume> volumes = StorageFactory.buildVirtualVolumes(volumesCapacities);
		List<StorageServer> servers = StorageFactory.buildStorageServer(serversStorageCapacities);
		
		StorageCSP csp = StorageCSPFactory.build(volumes, servers);
		boolean feasible = csp.packStorageResources();
		assertFalse(feasible);
	}
	
}
