package com.numergy.blog.csp.problems.compute;

import java.util.ArrayList;
import java.util.List;

import solver.Solver;
import solver.constraints.Constraint;
import solver.constraints.ICF;
import solver.constraints.LCF;
import solver.variables.BoolVar;
import solver.variables.IntVar;
import solver.variables.VF;
import util.tools.ArrayUtils;

public class ComputeCSP {	
	private IntVar[] physicalComputeAffectation;
	private IntVar[] physicalComputeRamLoad;
	private IntVar[] physicalComputeCpuLoad;
	private int[] ramVms;
	private int[] cpuVms;
	private Solver solver;

	public IntVar[] getPhysicalComputeAffectation() {
		return physicalComputeAffectation;
	}

	public void setPhysicalComputeAffectation(IntVar[] physicalComputeAffectation) {
		this.physicalComputeAffectation = physicalComputeAffectation;
	}

	public IntVar[] getPhysicalComputeRamLoad() {
		return physicalComputeRamLoad;
	}

	public void setPhysicalComputeRamLoad(IntVar[] physicalComputeRamLoad) {
		this.physicalComputeRamLoad = physicalComputeRamLoad;
	}

	public IntVar[] getPhysicalComputeCpuLoad() {
		return physicalComputeCpuLoad;
	}

	public void setPhysicalComputeCpuLoad(IntVar[] physicalComputeCpuLoad) {
		this.physicalComputeCpuLoad = physicalComputeCpuLoad;
	}

	public int[] getRamVms() {
		return ramVms;
	}

	public void setRamVms(int[] ramVms) {
		this.ramVms = ramVms;
	}

	public int[] getCpuVms() {
		return cpuVms;
	}

	public void setCpuVms(int[] cpuVms) {
		this.cpuVms = cpuVms;
	}	

	public void setSolver(Solver solver) {
		this.solver = solver;
	}
	
	public boolean packComputeResources() {		
		solver.post(getComputeConstraints(physicalComputeAffectation, ramVms, cpuVms, physicalComputeRamLoad, physicalComputeCpuLoad, solver));	
		return solver.findSolution();
	}	
	
	private static Constraint[] getComputeConstraints(IntVar[] ITEM_BIN, int[] ITEM_SIZE_RAM, int[] ITEM_SIZE_CPU, IntVar[] BIN_LOAD_RAM, IntVar[] BIN_LOAD_CPU, Solver solver) {
		int nbBins = BIN_LOAD_RAM.length;
		int nbItems= ITEM_BIN.length;
		
		int sum_items_ram_size = 0;
		for(int item_size : ITEM_SIZE_RAM) {
			sum_items_ram_size += item_size;
		}
		
		int sum_items_cpu_size = 0;
		for(int item_size : ITEM_SIZE_CPU) {
			sum_items_cpu_size += item_size;
		}
		
		BoolVar[][] xbr = VF.boolMatrix("xbr", nbBins, nbItems, solver);
		BoolVar[][] xbc = VF.boolMatrix("xbc", nbBins, nbItems, solver);
		IntVar sumRamView = VF.fixed(sum_items_ram_size, solver);
		IntVar sumCpuView = VF.fixed(sum_items_cpu_size, solver);
		
		List<Constraint> computeConstraints = new ArrayList<>();
		
		for(int i=0; i<nbItems; i++) {
			computeConstraints.add(ICF.boolean_channeling(ArrayUtils.getColumn(xbr,i), ITEM_BIN[i], 0));
		}
		
		for(int j=0; j<nbItems; j++) {
			computeConstraints.add(ICF.boolean_channeling(ArrayUtils.getColumn(xbc, j), ITEM_BIN[j], 0));
		}
		
		for(int b=0; b<nbBins; b++) {
			computeConstraints.add(LCF.and(ICF.scalar(xbr[b], ITEM_SIZE_RAM, BIN_LOAD_RAM[b]), ICF.scalar(xbc[b], ITEM_SIZE_CPU, BIN_LOAD_CPU[b])));
		}
		
		computeConstraints.add(ICF.sum(BIN_LOAD_RAM, sumRamView));
		computeConstraints.add(ICF.sum(BIN_LOAD_CPU, sumCpuView));
		
		return computeConstraints.toArray(new Constraint[2*nbItems+nbBins+2]);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(IntVar var : physicalComputeAffectation) {
			builder.append(var.getName() + " = " + var.getValue() + "\n");
		}
		
		for(IntVar var : physicalComputeRamLoad) {
			builder.append(var.getName() + " = " + var.getValue() + "\n");
		}
		
		for(IntVar var : physicalComputeCpuLoad) {
			builder.append(var.getName() + " = " + var.getValue() + "\n");
		}
		
		return builder.toString();
	}
}
