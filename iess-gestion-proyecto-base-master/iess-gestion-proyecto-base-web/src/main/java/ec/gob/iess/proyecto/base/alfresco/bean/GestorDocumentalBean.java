/*
 * Copyright 2024 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */ 

package ec.gob.iess.proyecto.base.alfresco.bean;

import java.io.Serializable;

import org.primefaces.model.file.UploadedFile;

import lombok.Data;

/** 
 * <b>
 * Clase del tipo Bean para declaración de variables.
 * </b>
 *  
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $ <p>[$Author: francisco.gagliardo $, $Date: 22 ene. 2024 $]</p>
*/
@Data
public class GestorDocumentalBean implements Serializable {
	
	/**
	* Seriabilizacion de la clase GestorDocumentalBean
	*/ 
	private static final long serialVersionUID = 6503241315935947458L;
	
	private String url;
	
	private String urlTemporal;
	
	private String opcion;
	
	private String parametro;
	
	private transient UploadedFile uploadedFile;
	
	private boolean mostrarUploadFileSimple;
	
	private boolean mostrarUploadFileMultiple; 

}
