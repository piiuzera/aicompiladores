package br.com.amarosystem.compiladores.models;

import java.io.Serializable;

import br.com.amarosystem.compiladores.enumtypes.Status;

public class Teste implements Serializable {
	private static final long serialVersionUID = -6646255571615080639L;
	
	private Status status;
	private String codigo;
	
	public Teste() {
		this.status = Status.ERROR;
		this.codigo = "";
	}
	public Teste(Status status, String codigo) {
		this.status = status;
		this.codigo = codigo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Teste)) {
			return false;
		}
		Teste objTeste = (Teste)obj;
		
		if(!objTeste.getCodigo().equals(this.getCodigo())) {
			return false;
		}
		if(objTeste.getStatus().getValue() != this.getStatus().getValue()) {
			return false;
		}
		
		return true;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
