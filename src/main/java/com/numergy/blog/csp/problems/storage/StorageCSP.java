package com.numergy.blog.csp.problems.storage;

import solver.Solver;
import solver.constraints.ICF;
import solver.variables.IntVar;

public class StorageCSP {	
	private IntVar[] physicalVolumeAffectation;
	private IntVar[] physicalStorageLoad;	
	private int[] virtualVolumesCapacities;
	
	private Solver solver;

	public IntVar[] getPhysicalStorageLoad() {
		return physicalStorageLoad;
	}
	
	public void setPhysicalStorageLoad(IntVar[] physicalStorageLoad) {
		this.physicalStorageLoad = physicalStorageLoad;
	}
	
	public IntVar[] getPhysicalVolumeAffectation() {
		return physicalVolumeAffectation;
	}
	
	public void setPhysicalVolumeAffectation(IntVar[] physicalVolumeAffectation) {
		this.physicalVolumeAffectation = physicalVolumeAffectation;
	}
	
	public int[] getVirtualVolumesCapacities() {
		return virtualVolumesCapacities;
	}
	
	public void setVirtualVolumesCapacities(int[] virtualVolumesCapacities) {
		this.virtualVolumesCapacities = virtualVolumesCapacities;
	}
	
	public Solver getSolver() {
		return solver;
	}
	
	public void setSolver(Solver solver) {
		this.solver = solver;
	}
	
	public boolean packStorageResources() {	
		solver.post(ICF.bin_packing(physicalVolumeAffectation, virtualVolumesCapacities, physicalStorageLoad, 0));
		return solver.findSolution();
	}	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(IntVar var : physicalVolumeAffectation) {
			builder.append(var.getName() + " = " + var.getValue() + "\n");
		}
		
		for(IntVar var : physicalStorageLoad) {
			builder.append(var.getName() + " = " + var.getValue() + "\n");
		}
	
		return builder.toString();
	}
}
