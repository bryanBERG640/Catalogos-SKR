package com.kabec.catalogos.catalogos.controller;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kabec.catalogos.catalogos.errores.Herrores;
import com.kabec.catalogos.catalogos.service.CargarArchivosService;
import com.kabec.catalogos.catalogos.util.Respuesta;

@Controller
public class CargarArchivoController {

	private static final Logger log = LoggerFactory.getLogger(CargarArchivoController.class);

	/**
	 * Se crean inyecciones de dependencia que se utilizan.
	 */
	@Autowired
	CargarArchivosService cargarArchivosService;
	@Autowired
	Respuesta respuesta;

	/**
	 * Metodo principal para mostrar la vista principal de la aplicación con el Phat
	 * "/".
	 * 
	 * @return Tipo de dato String. Este es el nombre de la vista.
	 */
	@GetMapping("/")
	public String index() {
		log.info("Mostrando pagina principal index");
		return "cargarArchivoVista";
	}

	/**
	 * Metodo que recibe el archivo por parte de la peticion de la vista. Realiza la
	 * llamada a los Service. Recibe los diferentes tipos de errores. Retorna la
	 * vista respuesta y manda como parametro la respuesta obtenida o el error.
	 * 
	 * @param archivo
	 * @param modelo
	 * @return
	 */
	@PostMapping("cargar")
	public String cargarArchivo(@RequestParam("file") MultipartFile archivo, Model modelo) {
		log.info("Cargando archivo... Desde uploadFile()");

		if (archivo.isEmpty()) {
			log.info("La carga del archivo es vacía.");
			respuesta.setResibirArchivo("No selecionaste unarchivo. Selecciona unarchivo.");
		} else {
			log.info("La carga del archivo es correcta");
			try {

				log.info("Llamando al metodo guardarArchivo() de la clase CargarArchivosService.class");
				cargarArchivosService.guardarArchivo(archivo);
				respuesta.setResibirArchivo("A funcionado correctamente todo.");
			} catch (NoSuchFileException e) {
				respuesta.setResibirArchivo(
						"La carpeta donde especifica dentro del microservicio no exite, crear la carpeta files en src/main/resource para que se almacenen los archivos que se reciben de la pagina web." + e.getMessage());
			} catch (Herrores e) {
				respuesta.setResibirArchivo(
						"Error al guardar información. Revisar numero y orden de las columnas en el archivo excel." + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				respuesta.setResibirArchivo("La lectura del archivo fallo: " + e.getMessage());
			} catch (Exception e) {
				respuesta.setResibirArchivo(
						"El nombre del archivo no coincie con algun nombre valido. Checa los valores permitidos en PDF." + e.getMessage());
			}
		}
		// Se pasa el valor de la respuesta a la vista Respuesta.
		modelo.addAttribute("respuesta", respuesta);
		return "respuesta";
	}

	/**
	 * Se redirecciona a la pagina principal cuando se realiza la petición de la
	 * vista respuesta.
	 * 
	 * @return
	 */
	@PostMapping("/regresar")
	public String save() {
		return "redirect:/";
	}

	/**
	 * Este metodo realiza lo mismo que el anterior pero al retornar un
	 * ResponseEntity se genera una vista por defaul con el mensaje que se pasa como
	 * parametro al retornar la instancia de ResponseEntity.
	 */
//	@PostMapping("cargar")
//	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile archivo) {
//		
//		log.info("Cargando archivo... Desde uploadFile()");
//
//		if (archivo.isEmpty()) {
//			log.info("La carga del archivo es vacía.");
//			respuesta.setResibirArchivo("No selecionaste unarchivo. Selecciona unarchivo.");
//			return new ResponseEntity<Object>(respuesta.getResibirArchivo(), HttpStatus.OK);
//		}
//		log.info("La carga del archivo es correcta");
//		try {
//			
//			log.info("Llamando al metodo guardarArchivo() de la clase CargarArchivosService.class");
//			cargarArchivosService.guardarArchivo(archivo);
//			respuesta.setResibirArchivo("A funcionado correctamente todo.");
//		}catch (NoSuchFileException e) {
//			respuesta.setResibirArchivo("La carpeta donde especifica dentro del microservicio no exite, crear la carpeta files en src/main/resource para que se almacenen los archivos que se reciben de la pagina web.");
//		}catch(Herrores e) {
//			respuesta.setResibirArchivo("Error al guardar información. Revisar número y orden de las columnas en el archivo excel.");
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}catch(Exception e) {
//			respuesta.setResibirArchivo("El nombre del archivo no coincie con algun nombre valido. Checa en el PDF los nombres validos para las archivos.");
//		}
//
//		return new ResponseEntity<Object>(respuesta.getResibirArchivo(), HttpStatus.OK);
//	}

//	@PostMapping("cargar")
//	public String cargarArchivo(@RequestParam("file") MultipartFile archivo, Model modelo) {
//		log.info("Cargando archivo... Desde uploadFile()");
//
//		if (archivo.isEmpty()) {
//			log.info("La carga del archivo es vacía.");
//			respuesta.setResibirArchivo("No selecionaste unarchivo. Selecciona unarchivo.");
//		}
//		log.info("La carga del archivo es correcta");
//		try {
//			
//			log.info("Llamando al metodo guardarArchivo() de la clase CargarArchivosService.class");
//			cargarArchivosService.guardarArchivo(archivo);
//			respuesta.setResibirArchivo("A funcionado correctamente todo.");
//		}catch (NoSuchFileException e) {
//			respuesta.setResibirArchivo("La carpeta donde especifica dentro del microservicio no exite, crear la carpeta files en src/main/resource para que se almacenen los archivos que se reciben de la pagina web.");
//		}catch(Herrores e) {
//			respuesta.setResibirArchivo("Error al guardar información. Revisar numero y orden de las columnas en el archivo excel.");
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}catch(Exception e) {
//			respuesta.setResibirArchivo("El nombre del archivo no coincie con algun nombre valido. Checa los valores permitidos en PDF.");
//		}
//		
//		if (!respuesta.getResibirArchivo().isEmpty()) {
//			modelo.addAttribute("respuesta",respuesta);
//		}
//		return "redirect:/";
//	}

}
