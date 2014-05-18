package com.numergy.blog.csp.problems.compute;

import java.util.List;

import solver.Solver;
import solver.variables.IntVar;
import solver.variables.VF;

import com.numergy.blog.csp.domain.ComputeServer;
import com.numergy.blog.csp.domain.VirtualMachine;

public class ComputeCSPFactory {
	public static ComputeCSP build(List<VirtualMachine> vms, List<ComputeServer> servers) {
		ComputeCSP csp = new ComputeCSP();
		Solver solver = new Solver();
		
		int[] vmCpus = new int[vms.size()];
		int[] vmRams = new int[vms.size()];
		
		for(int i=0; i<vms.size(); i++) {
			vmCpus[i] = vms.get(i).getVcpu();
			vmRams[i] = vms.get(i).getVram();			
		}
		
		IntVar[] physicalComputeAffectation = new IntVar[vms.size()];
		for(int i=0; i<physicalComputeAffectation.length; i++) {
			physicalComputeAffectation[i] = VF.bounded("Emplacement " + vms.get(i).getName(), 0, servers.size(), solver);
		}
		
		IntVar[] physicalComputeRamLoad = new IntVar[servers.size()];
		IntVar[] physicalComputeCpuLoad = new IntVar[servers.size()];		
		for(int i=0; i<physicalComputeRamLoad.length; i++) {
			ComputeServer server = servers.get(i);					
			physicalComputeRamLoad[i] = VF.bounded("RAM consommée : " + server.getName(), 0, server.getRam(), solver);
			physicalComputeCpuLoad[i] = VF.bounded("CPU consommé : " + server.getName(), 0, server.getCpu(), solver);
		}	
		
		csp.setCpuVms(vmCpus);
		csp.setRamVms(vmRams);
		csp.setPhysicalComputeAffectation(physicalComputeAffectation);
		csp.setPhysicalComputeCpuLoad(physicalComputeCpuLoad);
		csp.setPhysicalComputeRamLoad(physicalComputeRamLoad);
		csp.setSolver(solver);
		return csp;
	}	
	
	public static void buildResult(ComputeCSP csp, List<ComputeServer> servers, List<VirtualMachine> vms) {
		IntVar[] physicalComputeAffectation = csp.getPhysicalComputeAffectation();		
		
		for(ComputeServer server : servers) {
			server.getVms().clear();
		}
		
		for(int i=0; i<physicalComputeAffectation.length; i++) {
			servers.get(physicalComputeAffectation[i].getValue()).getVms().add(vms.get(i));						
		}		

	}	
}
