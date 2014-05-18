package com.numergy.blog.csp.problems;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import com.numergy.blog.csp.domain.ComputeServer;
import com.numergy.blog.csp.domain.VirtualMachine;
import com.numergy.blog.csp.problems.compute.ComputeCSP;
import com.numergy.blog.csp.problems.compute.ComputeCSPFactory;
import com.numergy.blog.csp.util.ComputeFactory;

public class ComputeCSPTest {
	
	@Test
	public void testComputePacking_nominal() {
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		
		final int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 8, 8, 8, 16, 16 };
		final int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4 };
		
		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu);
		
		ComputeCSP csp = ComputeCSPFactory.build(vms, servers);
		boolean feasible = csp.packComputeResources();
		assertTrue(feasible);
		
		ComputeCSPFactory.buildResult(csp, servers, vms);
		
		int totalVmRam = 0;
		for(int vmRam : vmsRam) {
			totalVmRam += vmRam;			
		}
		
		int totalVmCpu = 0;
		for(int vmCpu : vmsCpu) {
			totalVmCpu += vmCpu;
		}
		
		int totalServerRam = 0;
		int totalServerCpu = 0;
		for(ComputeServer server : servers) {
			assertTrue(server.getCpuLoad() <= server.getCpu());
			assertTrue(server.getRamLoad() <= server.getRam());
			totalServerRam += server.getRamLoad();
			totalServerCpu += server.getCpuLoad();
		}
		
		assertEquals(totalVmCpu, totalServerCpu);
		assertEquals(totalVmRam, totalServerRam);
		
		System.out.println(csp);
	}
	
	@Test
	public void testComputePacking_surcontrainte_ram() {
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		
		final int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 8, 8, 8, 16, 16, 32, 32 };
		final int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4 };
		
		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu);
		
		ComputeCSP csp = ComputeCSPFactory.build(vms, servers);
		boolean feasible = csp.packComputeResources();
		assertFalse(feasible);	
	}
	
	@Test
	public void testComputePacking_surcontrainte_cpu() {
		final int[] serversRam = { 32, 32, 32, 32 };
		final int[] serversCpu = { 8, 8, 8, 8 };
		
		final int[] vmsRam = { 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 8, 8, 8, 16, 16 };
		final int[] vmsCpu = { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 4, 4, 6, 6, 8, 8 };
		
		List<VirtualMachine> vms = ComputeFactory.buildVirtualMachines(vmsRam, vmsCpu);
		List<ComputeServer> servers = ComputeFactory.buildComputeServer(serversRam, serversCpu);
		
		ComputeCSP csp = ComputeCSPFactory.build(vms, servers);
		boolean feasible = csp.packComputeResources();
		assertFalse(feasible);	
	}
	
}
