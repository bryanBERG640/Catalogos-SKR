package com.kabec.catalogos.catalogos.errores;

import org.springframework.stereotype.Component;


public class Herrores extends Exception{

	private static final long serialVersionUID = 1L;
	
	public Herrores(String msg) {
		super(msg);
	}

	
}
