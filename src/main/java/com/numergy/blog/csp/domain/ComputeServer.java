package com.numergy.blog.csp.domain;

import java.util.ArrayList;
import java.util.List;

public class ComputeServer {
	private List<VirtualMachine> vms = new ArrayList<>();
	private String name;
	private int ram;
	private int cpu;

	public ComputeServer(String name, int ram, int cpu) {
		this.name = name;
		this.ram = ram;
		this.cpu = cpu;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public List<VirtualMachine> getVms() {
		return vms;
	}

	public void setVms(List<VirtualMachine> vms) {
		this.vms = vms;
	}
	
	public int getRamLoad() {
		int load = 0;
		for(VirtualMachine vm : vms) {
			load += vm.getVram();
		}
		return load;
	}
	
	public int getCpuLoad() {
		int load = 0;
		for(VirtualMachine vm : vms) {
			load += vm.getVcpu();
		}
		return load;
	}
}
