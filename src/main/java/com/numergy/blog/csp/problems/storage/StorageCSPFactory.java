package com.numergy.blog.csp.problems.storage;

import java.util.List;

import solver.Solver;
import solver.variables.IntVar;
import solver.variables.VF;

import com.numergy.blog.csp.domain.StorageServer;
import com.numergy.blog.csp.domain.VirtualVolume;

public class StorageCSPFactory {
	public static StorageCSP build(List<VirtualVolume> volumes, List<StorageServer> servers) {
		Solver solver = new Solver();
		StorageCSP csp = new StorageCSP();
		
		int[] virtualVolumesCapacities = new int[volumes.size()];
		for(int i=0; i<virtualVolumesCapacities.length; i++) {
			virtualVolumesCapacities[i] = volumes.get(i).getCapacity();
		}
		
		IntVar[] physicalVolumeAffectation = new IntVar[volumes.size()];
		for(int i=0; i<physicalVolumeAffectation.length; i++) {
			physicalVolumeAffectation[i] = VF.bounded("Emplacement " + volumes.get(i).getName(), 0, servers.size(), solver);
		}
		
		IntVar[] physicalStorageLoad = new IntVar[servers.size()];				
		for(int i=0; i<physicalStorageLoad.length; i++) {
			StorageServer server = servers.get(i);					
			physicalStorageLoad[i] = VF.bounded("Stockage consommé : " + server.getName(), 0, server.getCapacity(), solver);			
		}		
		
		csp.setPhysicalStorageLoad(physicalStorageLoad);
		csp.setPhysicalVolumeAffectation(physicalVolumeAffectation);
		csp.setVirtualVolumesCapacities(virtualVolumesCapacities);
		csp.setSolver(solver);
		
		return csp;
	}	
	
	public static void buildResult(StorageCSP csp, List<StorageServer> servers, List<VirtualVolume> volumes) {
		IntVar[] physicalVolumeAffectation = csp.getPhysicalVolumeAffectation();		
		
		for(StorageServer server : servers) {
			server.getVolumes().clear();
		}
		
		for(int i=0; i<physicalVolumeAffectation.length; i++) {
			servers.get(physicalVolumeAffectation[i].getValue()).getVolumes().add(volumes.get(i));						
		}		

	}	
}
