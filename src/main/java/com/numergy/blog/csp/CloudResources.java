package com.numergy.blog.csp;
import solver.Solver;
import solver.constraints.Constraint;
import solver.constraints.ICF;
import solver.constraints.LCF;
import solver.variables.BoolVar;
import solver.variables.IntVar;
import solver.variables.VF;
import util.tools.ArrayUtils;

public class CloudResources {
	
	private static final int nbServers = 4;
	private static final int nbRamMax = 32;
	private static final int nbCpuMax = 8;
	private static final int nbVM = 18;

	
	public static void main(String[] args) {
		
		Solver solver = new Solver();	
			
		IntVar[] physicalServerAffectation = VF.boundedArray("Emplacement VM", nbVM, 0, 4, solver);
		IntVar[] physicalServerRamLoad = VF.boundedArray("Charge serveur RAM", nbServers, 0, nbRamMax, solver);
		IntVar[] physicalServerCpuLoad = VF.boundedArray("Charge serveur CPU", nbServers, 0, nbCpuMax, solver);
		int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 8, 8, 8, 16, 16 };
		int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4 };
		
		solver.post(bin_packing(physicalServerAffectation, vmsRam, vmsCpu, physicalServerRamLoad, physicalServerCpuLoad, solver));				
		
		solver.findSolution();
		
		for(int i=0; i<physicalServerAffectation.length; i++) {
			System.out.println(physicalServerAffectation[i].getName() + " = " + physicalServerAffectation[i].getValue());
		}
		
		for(int i=0; i<physicalServerRamLoad.length; i++) {
			System.out.println(physicalServerRamLoad[i].getName() + " = " + physicalServerRamLoad[i].getValue());
		}
		
		for(int i=0; i<physicalServerCpuLoad.length; i++) {
			System.out.println(physicalServerCpuLoad[i].getName() + " = " + physicalServerCpuLoad[i].getValue());
		}
	}	
	
	public static Constraint[] bin_packing(IntVar[] ITEM_BIN, int[] ITEM_SIZE_RAM, int[] ITEM_SIZE_CPU, IntVar[] BIN_LOAD_RAM, IntVar[] BIN_LOAD_CPU, Solver solver){
		int nbBins = BIN_LOAD_RAM.length;
		int nbItems= ITEM_BIN.length;
		
		BoolVar[][] xbi = VF.boolMatrix("xbi", nbBins, nbItems, solver);
		BoolVar[][] xbj = VF.boolMatrix("xbj", nbBins, nbItems, solver);
		
		int sum_items_ram_size = 0;
		for(int item_size : ITEM_SIZE_RAM) {
			sum_items_ram_size += item_size;
		}
		IntVar sumRamView = VF.fixed(sum_items_ram_size, solver);
		
		int sum_items_cpu_size = 0;
		for(int item_size : ITEM_SIZE_CPU) {
			sum_items_cpu_size += item_size;
		}
		IntVar sumCpuView = VF.fixed(sum_items_cpu_size, solver);
	
		Constraint[] constraints = new Constraint[2*nbItems+nbBins+2];
		
		for(int i=0; i<nbItems; i++) {
			constraints[i] = ICF.boolean_channeling(ArrayUtils.getColumn(xbi,i), ITEM_BIN[i], 0);
		}
		
		for(int j=0; j<nbItems; j++) {
			constraints[nbItems + j] = ICF.boolean_channeling(ArrayUtils.getColumn(xbj, j), ITEM_BIN[j], 0);
		}
		
		for(int b=0; b<nbBins; b++) {
			constraints[2 * nbItems + b] = LCF.and(ICF.scalar(xbi[b], ITEM_SIZE_RAM, BIN_LOAD_RAM[b]), ICF.scalar(xbj[b], ITEM_SIZE_CPU, BIN_LOAD_CPU[b]));
		}
		
		constraints[2 * nbItems + nbBins] = ICF.sum(BIN_LOAD_RAM, sumRamView);
		constraints[2 * nbItems + nbBins + 1] = ICF.sum(BIN_LOAD_CPU, sumCpuView);
		
		return constraints;
	}
}
