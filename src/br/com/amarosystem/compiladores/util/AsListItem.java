package br.com.amarosystem.compiladores.util;

import java.io.Serializable;

public class AsListItem implements Serializable {
	private static final long serialVersionUID = 1294005025801155408L;
	
	private String nome;
	private String valor;
	private Object object;
	
	public AsListItem() {
		this.nome = "";
		this.valor = "";
		this.object = new Object();
	}
	public AsListItem(String nome) {
		this.nome = nome;
		this.valor = "";
		this.object = new Object();
	}
	public AsListItem(String nome, String valor) {
		this.nome = nome;
		this.valor = valor;
		this.object = new Object();
	}
	public AsListItem(String nome, String valor, Object object) {
		this.nome = nome;
		this.valor = valor;
		this.object = object;
	}
	
	@Override
	public String toString() {
		return this.valor;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
