/*
 * Copyright 2024 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */ 

package ec.gob.proyecto.base.alfresco.enumeracion;

import lombok.Getter;

/** 
 * <b>
 * Clase del tipo enumeracion para los codigos de aplicacion.
 * </b>
 *  
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $ <p>[$Author: francisco.gagliardo $, $Date: 7 mar. 2024 $]</p>
*/
@Getter
public enum AlfrescoEnum {
	
	CODIGO_APLIC_PRJ("IESS-GES-DICDATOS");
	
	private String codigoAplicacion;
	
	private AlfrescoEnum(String codigoAplicacion) {
		this.codigoAplicacion  = codigoAplicacion;
	}
}
