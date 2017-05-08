package br.com.amarosystem.compiladores.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class AsList<generic> extends ArrayList<generic> {
	private static final long serialVersionUID = 1106436142726609626L;
	
	public AsList() {
		super();
	}
	
	public AsList(Collection<? extends generic> list) {
		super(list);
	}
	
	public boolean equals(AsList<generic> list) {
		if(this.size() > list.size() || this.size() < list.size()) {
			return false;
		}
		
		for(int i = 0; i < this.size(); i++) {
			generic obj1 = this.get(i);
			generic obj2 = list.get(i);
			
			if(!obj1.equals(obj2)) {
				return false;
			}
		}
		return true;
	}
	
	public AsList<generic> where(Predicate<generic> predicate) {
		AsList<generic> list = new AsList<generic>();
		for(generic obj : this) {
			if(predicate.test(obj)) {
				list.add(obj);
			}
		}
		return list;
	}
}
