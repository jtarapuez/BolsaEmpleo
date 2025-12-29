/*
 * Copyright 2024 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */

package ec.gob.iess.proyecto.base.alfresco.dto;

import java.io.Serializable;

import lombok.Data;

/** 
 * <b>
 * Clase del tipo Dto para visualizacion de alfesco.
 * </b>
 *  
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $ <p>[$Author: francisco.gagliardo $, $Date: 23 ene. 2024 $]</p>
*/
@Data
public class AlfrescoDto implements Serializable {

	/**
	* Seriabilizacion de la clase AlfrescoDto
	*/ 
	private static final long serialVersionUID = -9016080997746546141L;

	private String archivoId;
	
	private String nombreArchivo;
	
	private String contenido;
	
	private String tipoContenido;

	/**
	* Construtor de la clase AlfrescoDto
	*/ 
	public AlfrescoDto() {
		super();
	}

	/**
	* @param archivoId
	* @param nombreArchivo
	* @param contenido
	* @param tipoContenido
	*/ 
	public AlfrescoDto(String archivoId, String nombreArchivo, String contenido, String tipoContenido) {
		super();
		this.archivoId = archivoId;
		this.nombreArchivo = nombreArchivo;
		this.contenido = contenido;
		this.tipoContenido = tipoContenido;
	}
	
	
		
}
