package com.numergy.blog.csp.domain;

import java.util.ArrayList;
import java.util.List;

public class VirtualMachine {
	private String name;
	private int vram;
	private int vcpu;
	private List<VirtualVolume> volumes = new ArrayList<>();

	public VirtualMachine(String name, int vram, int vcpu) {
		this.name = name;
		this.vram = vram;
		this.vcpu = vcpu;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVram() {
		return vram;
	}

	public void setVram(int vram) {
		this.vram = vram;
	}

	public int getVcpu() {
		return vcpu;
	}

	public void setVcpu(int vcpu) {
		this.vcpu = vcpu;
	}
	
	public List<VirtualVolume> getVolumes() {
		return volumes;
	}
	
	public void addVolume(VirtualVolume volume) {
		volumes.add(volume);
	}
}
