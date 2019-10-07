package com.kabec.catalogos.catalogos.repository;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kabec.catalogos.catalogos.errores.Herrores;
import com.kabec.catalogos.catalogos.util.Variables;

/**
 * 
 * @author KABE40-2
 *
 */
@Repository
public class AprobacionRepository {

	private static final Logger log = LoggerFactory.getLogger(AprobacionRepository.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	Variables variables;

	/**
	 * Se crea el query dinamico para insertar los datos a la BD. Realiza la
	 * comparacion de los diferentes tipos de valores para formar el query.
	 * 
	 * @throws Herrores
	 */
	public void insertarAprobacionDinamico() throws Herrores {
		log.info("Inicio metodo insertarAprobacionDinamico");

		ArrayList<String> list = (ArrayList<String>) variables.getList();
		log.info("Valores de list: " + list);
		
		int columnas;
		int numeroColumnas = variables.getNumeroColumnas();
		String contenido = "";
		String nombreTabla = variables.getSubNombreTabla();
		int tamano = list.size() - numeroColumnas;
		boolean esEntero = false;
		Integer valorArregloEntero = 0;
		boolean esFloat = false;
		Float valorArregloFloat = 0.0f;
		boolean esLong = false;
		Long valorArregloLong = 0L;
		String valorArregloString = "";

		for (int i = numeroColumnas; i <= tamano; i += numeroColumnas) {
			for (int x = 0; x < variables.getNumeroColumnas(); x++) {

				log.info("Comprobar si el valor del arreglo es Entero.");
				try {
					try {
						valorArregloEntero = Integer.parseInt(list.get(i + x));
						esEntero = true;
						log.info("El valor del arreglo es entero. " + esEntero);
					} catch (NumberFormatException e) {
						esEntero = false;
						log.info("El valor del arreglo no es Entero.");
					}
					
					log.info("Compara si el valor del arreglo es Long");
					if (esEntero == false) {
						try {
							valorArregloLong = Long.parseLong(list.get(i + x));
							esLong = true;
							valorArregloString = valorArregloLong.toString();
							log.info("El valor del arreglo es Long." + valorArregloLong);
						} catch (NumberFormatException e) {
							esLong = false;
							log.info("El valor del arreglo no es Long.");
						}
					}
					
					log.info("Compara si el valor del arreglo es Float");
					if (esEntero == false && esLong == false) {
						valorArregloFloat = Float.parseFloat(list.get(i + x));
						esFloat = true;
						log.info("El valor del arreglo es float. " + esFloat);
					}
				} catch (NumberFormatException e) {
					esFloat = false;
				}
				log.info("El valor es String o mediumLong");
				log.info("Se arma el query dependiendo si es Integer, String, Float o Long");

				if (esEntero == true) {
					System.err.println(list.get(i + x));
					contenido += valorArregloEntero + ",";
				}
				if (esFloat == true) {
					contenido += valorArregloFloat + ",";
				}
				if (esLong == true) {
					contenido += "\"" + valorArregloString + "\"" + ",";
				}
				if (esEntero != true && esFloat != true && esLong != true) {
					System.err.println(list.get(i + x));
					contenido += "\"" + list.get(i + x) + "\"" + ",";
				} else {
					log.info("El dato no es del tipo Integer o String.");
					System.err.println("El dato no es del tipo Integer o String.");
				}

			}
			log.info("Query primer formato: " + contenido);
			contenido = 0 + "," + contenido.substring(0, contenido.length() - 1);
			log.info("Nombre de la tabla para el query..." + nombreTabla);
			log.info("Valores del Qyery... " + contenido);
			String query = "insert into " + nombreTabla + " values (" + contenido + ");";
			log.info("Query formado: " + query);
			try {
				columnas = this.jdbcTemplate.update(query);
				log.info("Número de columnas afectadas... " + columnas);
			} catch (Exception e) {
				log.info("Error en la inserccion de datos...");
				e.getStackTrace();
				throw new Herrores("Números fuera del intervalo");
			}

			log.info("Contenido: " + contenido);
			log.info("Cadena que representa los valores a guardar: " + contenido);
			contenido = "";
		}
	}

}
