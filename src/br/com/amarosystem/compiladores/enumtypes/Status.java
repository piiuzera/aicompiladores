package br.com.amarosystem.compiladores.enumtypes;

import javax.swing.ImageIcon;

public enum Status {
	ERROR(0), SUCCESS(1);
	
	private int value;
	
	Status(int value) {
		this.value = value;
	}
	
	public static Status getEnumByValue(int value) {
		for(Status objStatus : values()) {
			if(objStatus.getValue() == value) {
				return objStatus;
			}
		}
		return null;
	}
	
	public ImageIcon getIcon() {
		if(this == Status.ERROR) {
			return new ImageIcon(this.getClass().getResource("/busy-status.png"));
		} else if(this == Status.SUCCESS) {
			return new ImageIcon(this.getClass().getResource("/online-status.png"));
		}
		return null;
	}
	
	public int getValue() {
		return value;
	}
}
