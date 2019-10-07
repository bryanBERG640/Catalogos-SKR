package com.kabec.catalogos.catalogos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kabec.catalogos.catalogos.errores.Herrores;
import com.kabec.catalogos.catalogos.repository.AprobacionRepository;
import com.kabec.catalogos.catalogos.util.Respuesta;
import com.kabec.catalogos.catalogos.util.Variables;

/**
 * 
 * @author KABE40-2
 *
 */
@Service
public class CargarArchivosService {

	private static final Logger log = LoggerFactory.getLogger(CargarArchivosService.class);

	@Autowired
	LeerArchivos leerArchivos;
	@Autowired
	Variables variables;
	@Autowired
	AprobacionRepository aprobacionRepository;
	@Autowired
	Respuesta respuesta;

	private String cargar_folder = "./src/main/resources/files/";

	/**
	 * Realiza la carga del archivo y es almacenado en el path del valor de la
	 * variable cargar_folder.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void guardarArchivo(MultipartFile file) throws Exception {

		log.info("Inicio metodo guarderArchivo()");

		variables.setNombreTabla(file.getOriginalFilename().toLowerCase());
		log.info("Se obtienen el nombre del archivo en minusculas... " + variables.getNombreTabla());

		if (!file.isEmpty()) {
			if (subNombre(variables.getNombreTabla())) {

				log.info("Se obtiene el nombre de la tabla sin extencion." + variables.getSubNombreTabla());
				byte[] bytes = file.getBytes();

				log.info("Nombre de la tabla para pasarlo al path y leer el archivo desde su ruta...");
				Path path = Paths.get(cargar_folder + variables.getNombreTabla());

				Files.write(path, bytes);

				// Realiza la lectura del archivo.
				log.info("Se llama al metodo leerArchivos()");
				leerArchivos.leerArchivos(variables.getNombreTabla());
				// Llamada al metodo.
				insertar();
			} else {
				throw new Exception();
			}
		}
		log.info("Fin del metodo guardarArchivo()");
	}

	/**
	 * Realiza la llamda al metodo de de la clase aprobacionRepository del paquete
	 * repository.
	 * 
	 * @throws Herrores
	 */
	public void insertar() throws Herrores {

		aprobacionRepository.insertarAprobacionDinamico();
	}

	/**
	 * Realiza la comparacion del nombre del archivo sin extención con los valores
	 * que representan los nombre de cada una de las tablas de la BD. NOTA: Se pede
	 * mejorar si los nombres de las tablas se almacenan enun ArrayList y se recorre
	 * para hacer la comparación
	 * 
	 * @param nombre
	 * @return
	 */
	private boolean subNombre(String nombre) {
		boolean coincideNombre = false;
		log.info("Inicio metodo subNombre()");
		String[] subNombres = nombre.split("\\.");
		variables.setSubNombreTabla(subNombres[0]);
		// Se puede mejorar la comparacion si los nombres de las tablas se guarda en un
		// arreglo y se recorre el arreglo para saver si hay alguna coincidencia.
		switch (variables.getSubNombreTabla().toLowerCase()) {
		case "aprobacion":
			coincideNombre = true;
			log.info("Coincide nombre con aprobacion: " + variables.getNombreTabla());
			break;
		case "carrera":
			coincideNombre = true;
			break;
		case "cita":
			coincideNombre = true;
			break;
		case "cliente":
			coincideNombre = true;
			break;
		case "entrevista":
			coincideNombre = true;
			break;
		case "escuela":
			coincideNombre = true;
			break;
		case "estatus_cita":
			coincideNombre = true;
			break;
		case "estatus_cv":
			coincideNombre = true;
			break;
		case "estatus_postulante":
			coincideNombre = true;
			break;
		case "estatus_titulacion":
			coincideNombre = true;
			break;
		case "examen":
			coincideNombre = true;
			break;
		case "perfil":
			coincideNombre = true;
			break;
		case "postulante_b":
			coincideNombre = true;
			break;
		case "postulante_c":
			coincideNombre = true;
			break;
		case "seccion":
			coincideNombre = true;
			break;
		case "sexo":
			coincideNombre = true;
			break;
		case "tipo_entrevista":
			coincideNombre = true;
			break;
		case "tipo_examen":
			coincideNombre = true;
			break;
		}
		log.info("Nombre del archivo sin extencion... " + variables.getSubNombreTabla());

		return coincideNombre;
	}
}
