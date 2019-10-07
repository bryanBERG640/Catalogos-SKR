package com.kabec.catalogos.catalogos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kabec.catalogos.catalogos.util.Variables;

/**
 * 
 * @author KABE40-2
 *
 */
@Service
public class LeerArchivos {

	private static final Logger log = LoggerFactory.getLogger(LeerArchivos.class);

	@Autowired
	Variables variables;

	/**
	 * Realiza la lectura del archivo y almacena la información en una lista.
	 * 
	 * @param nombreArchivo
	 */
	@SuppressWarnings({ "resource", "rawtypes" })
	public void leerArchivos(String nombreArchivo) {
		log.info("Inicio del metodo leerArchivos()");
		log.info("El nombre del archivo es: " + nombreArchivo);

		List<String> lista = new ArrayList<String>();
		int auxiliar = 0;

		try {
			String rutaArchivoExcel = "./src/main/resources/files/" + nombreArchivo;
			log.info("La ruta del archivo es: " + rutaArchivoExcel);
			log.info("Se crea un objeto inputStream que almacena al archivo en la ruta especificada.");
			FileInputStream inputStream = new FileInputStream(new File(rutaArchivoExcel));
			log.info("Se crea un libro de trabajo.");
			Workbook libroDeTrabajo = new XSSFWorkbook(inputStream);
			log.info("Se obienen la primera hoja del archivo Excel.");
			Sheet primeraHoja = libroDeTrabajo.getSheetAt(0);
			log.info("Se crea un iterador para el recorrido de la hoja.");
			Iterator iterator = primeraHoja.iterator();
			DataFormatter formateador = new DataFormatter();
			log.info("Se guarda la información del archivo en una lista.");
			while (iterator.hasNext()) {
				Row siguienteFila = (Row) iterator.next();
				Iterator celdaIterator = siguienteFila.cellIterator();
				log.info("Valor de auxiliar: " + auxiliar);
				auxiliar = 0;
				while (celdaIterator.hasNext()) {
					auxiliar++;
					Cell celda = (Cell) celdaIterator.next();
					System.err.println("-------Celda: " + celda);
					String contenidoCelda = formateador.formatCellValue(celda);
					System.out.println("celda: " + contenidoCelda);
					lista.add(contenidoCelda);
				}
			}
			variables.setNumeroColumnas(auxiliar);
			log.info("Numbero de columnas:" + auxiliar);
			log.info("Fin del guardado de la información de la lista.");
			variables.setNumeroFilas(auxiliar);
			log.info("El numero de filas de la lista es: " + variables.getNumeroFilas());
			variables.setList(lista);
			log.info("Valores de la lista: " + variables.getList());

			log.info("Se recorre la lista.");
			recorrerLista();
		} catch (Exception e) {
			log.info("Error");
			e.printStackTrace();
		}
	}

	/**
	 * Realiza la impresion de la lista para visualizar sus datos.
	 */
	private void recorrerLista() {
		for (int i = 0; i < variables.getList().size(); i++) {
			System.out.println(variables.getList().get(i));
		}
		log.info("Fin del recorrido de la lista.");
	}

}
