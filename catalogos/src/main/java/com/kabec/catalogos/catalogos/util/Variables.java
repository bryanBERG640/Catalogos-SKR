package com.kabec.catalogos.catalogos.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Variables {

	private String nombreTabla;
	private List<String> list;
	private int numeroFilas;
	private String subNombreTabla;
	private final String APROBACION = "aprobacion";
	private int numeroColumnas;

	public int getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(int numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}

	public String getAPROBACION() {
		return APROBACION;
	}

	public String getSubNombreTabla() {
		return subNombreTabla;
	}

	public void setSubNombreTabla(String subNombreTabla) {
		this.subNombreTabla = subNombreTabla;
	}

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public int getNumeroFilas() {
		return numeroFilas;
	}

	public void setNumeroFilas(int numeroFilas) {
		this.numeroFilas = numeroFilas;
	}

}
