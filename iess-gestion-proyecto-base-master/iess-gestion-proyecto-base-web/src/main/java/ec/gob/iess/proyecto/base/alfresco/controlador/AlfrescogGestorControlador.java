/*
 * Copyright 2024 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */

package ec.gob.iess.proyecto.base.alfresco.controlador;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.net.util.Base64;
import org.audit4j.core.util.Log;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;

import ec.gob.iess.componente.autorizador.jsf.utilitario.UtilitarioJsf;
import ec.gob.iess.componente.cliente.restful.cliente.AlfrescoGestorCliente;
import ec.gob.iess.componente.cliente.restful.excepcion.ClienteRestFulExcepcion;
import ec.gob.iess.proyecto.base.alfresco.bean.GestorDocumentalBean;
import ec.gob.iess.proyecto.base.alfresco.dto.AlfrescoDto;
import ec.gob.iess.ws.pojo.gestor.alfresco.AlfrescoEntrada;
import ec.gob.iess.ws.pojo.gestor.alfresco.AlfrescoRespuesta;
import ec.gob.iess.ws.pojo.gestor.alfresco.AlfrescoSalida;
import ec.gob.proyecto.base.alfresco.enumeracion.AlfrescoEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * <b> Clase del tipo controlador para la implementación de la funcionalidd
 * Alfresco. </b>
 * 
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: francisco.gagliardo $, $Date: 22 ene. 2024 $]
 *          </p>
 */
@Named
@ViewScoped
public class AlfrescogGestorControlador extends UtilitarioJsf implements Serializable {

	/**
	 * Seriabilización de la clase
	 */
	private static final long serialVersionUID = -6726924779257988855L;

	@Setter
	@Getter
	private transient UploadedFile uploadedFile;

	@Setter
	@Getter
	private transient UploadedFiles uploadedfiles;

	@Setter
	@Getter
	private String nombreArchivo;

	@Setter
	@Getter
	private List<AlfrescoDto> listaAlfrescoDto;

	@Setter
	@Getter
	private List<AlfrescoDto> listaSelectAlfrescoDto;

	@Setter
	@Getter
	private transient StreamedContent streamedContentArchivo;

	@Setter
	@Getter
	private AlfrescoRespuesta alfrescoRespuesta;

	@Setter
	@Getter
	private AlfrescoEntrada alfrescoEntrada;
	
	@Setter
	@Getter
	private List<AlfrescoDto> lista;

	@Setter
	@Getter
	private GestorDocumentalBean gestorDocumentalBean;

	@EJB
	private AlfrescoGestorCliente alfrescoGestorCliente;

	@PostConstruct
	public void init() {
		lista = new ArrayList<>();
		gestorDocumentalBean = new GestorDocumentalBean();

		gestorDocumentalBean.setMostrarUploadFileSimple(Boolean.FALSE);
		gestorDocumentalBean.setMostrarUploadFileMultiple(Boolean.FALSE);

		listaSelectAlfrescoDto = new ArrayList<>();

		alfrescoEntrada = new AlfrescoEntrada();
		alfrescoRespuesta = new AlfrescoRespuesta();

	}

	/**
	 * <b> Metodo para valiadar los directorios. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 6 mar. 2024]
	 * </p>
	 *
	 * @param alfrescoEntrada objeto de entrad de parámetros
	 * @return retorna un objeto dle tipo AlfrescoRespuesta
	 * @throws ClienteRestFulExcepcion Excepcion dle tipo cliente
	 */
	public AlfrescoRespuesta validarDirectorio(AlfrescoEntrada alfrescoEntrada) throws ClienteRestFulExcepcion {
		return alfrescoGestorCliente.validarDirectorio(alfrescoEntrada);
	}

	/**
	 * <b> Metodo para la creacion del directorio. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 6 mar. 2024]
	 * </p>
	 *
	 * @param alfrescoEntrada objeto de entrad de parámetros
	 * @return retorna un objeto dle tipo AlfrescoRespuesta
	 * @throws ClienteRestFulExcepcion Excepcion dle tipo cliente
	 */
	public AlfrescoRespuesta crearDirectorio(AlfrescoEntrada alfrescoEntrada) throws ClienteRestFulExcepcion {
		return alfrescoGestorCliente.crearDirectorio(alfrescoEntrada);
	}

	/**
	 * <b> Metodo para crear el directorio. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 6 mar. 2024]
	 * </p>
	 *
	 */
	public void generarDirectorioAlfresco() {
		alfrescoEntrada.setUrl(gestorDocumentalBean.getUrl());
		alfrescoEntrada.setCodigoAplicacion(AlfrescoEnum.CODIGO_APLIC_PRJ.getCodigoAplicacion());
		try {
			alfrescoRespuesta = alfrescoGestorCliente.crearDirectorio(alfrescoEntrada);
			gestorDocumentalBean.setUrlTemporal(alfrescoRespuesta.getAlfrescoSalida().getUrl());
			FacesMessage message = new FacesMessage("Éxito ", "El directorio ha sido creado!");
			FacesContext.getCurrentInstance().addMessage(null, message);
			gestorDocumentalBean.setMostrarUploadFileSimple(Boolean.TRUE);
			gestorDocumentalBean.setMostrarUploadFileMultiple(Boolean.TRUE);
		} catch (ClienteRestFulExcepcion e1) {
			FacesMessage message = new FacesMessage("Error", "El directorio no pudo ser creado!");
			FacesContext.getCurrentInstance().addMessage(null, message);
			limpiarInformacion();
		}
	}

	/**
	 * <b> Metodo para subir el archivo simple. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 6 mar. 2024]
	 * </p>
	 *
	 */
	public void crearArchivoSimple() {
		if (uploadedFile != null) {
			alfrescoEntrada.setUrl(gestorDocumentalBean.getUrlTemporal());
			alfrescoEntrada.setNombreArchivo(uploadedFile.getFileName());
			alfrescoEntrada.setArchivo(uploadedFile.getContent());
			alfrescoEntrada.setTipoContenido(uploadedFile.getContentType());
			alfrescoEntrada.setTitulo("Buzón de Datos");
			alfrescoEntrada.setCodigoAplicacion(AlfrescoEnum.CODIGO_APLIC_PRJ.getCodigoAplicacion());
			alfrescoEntrada.setTipoOperacion("S");
			try {
				alfrescoRespuesta = alfrescoGestorCliente.subirArchivo(alfrescoEntrada);
				FacesMessage message = new FacesMessage("Éxito ", "El archivo: "
						+ alfrescoRespuesta.getAlfrescoSalida().getNombreArchivo() + "\n ha sido cargado.");
				FacesContext.getCurrentInstance().addMessage(null, message);
				limpiarInformacion();
			} catch (ClienteRestFulExcepcion e) {
				ponerMensajeError(e.getMessage());
				limpiarInformacion();
			}
		} else {
			FacesMessage message = new FacesMessage("Error", "El archivo no pudo ser cargado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			limpiarInformacion();
		}
	}

	/**
	 * <b> Metodo para subir el archivo multiple. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 7 mar. 2024]
	 * </p>
	 *
	 */
	public void crearArchivoMultiple() {
		if (uploadedfiles != null) {

			for (UploadedFile archivo : uploadedfiles.getFiles()) {

				Map<String, byte[]> mapArchivoMultiple = new HashMap<>();

				mapArchivoMultiple.put(archivo.getFileName(), archivo.getContent());

				alfrescoEntrada.setMapArchivoMultiple(mapArchivoMultiple);
				alfrescoEntrada.setUrl(gestorDocumentalBean.getUrlTemporal());
				alfrescoEntrada.setTipoContenido(archivo.getContentType());
				alfrescoEntrada.setCodigoAplicacion(AlfrescoEnum.CODIGO_APLIC_PRJ.getCodigoAplicacion());
				alfrescoEntrada.setTipoOperacion("M");
				try {
					alfrescoRespuesta = alfrescoGestorCliente.subirArchivo(alfrescoEntrada);
				} catch (ClienteRestFulExcepcion e) {
					ponerMensajeError(e.getMessage());
					limpiarInformacion();
				}
			}

			String mensaje = "El archivo se ha creado: " + alfrescoRespuesta.getMensaje();
			FacesMessage message = new FacesMessage("Exito ", mensaje);
			FacesContext.getCurrentInstance().addMessage(null, message);

			limpiarInformacion();

		} else {
			FacesMessage message = new FacesMessage("Error ", "Por favor seleccione un archivo!");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	/**
	 * <b> Metodo para buscar los archivos por criterio. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 8 mar. 2024]
	 * </p>
	 *
	 */
	public void buscarArchivo() {
		alfrescoEntrada.setUrl(gestorDocumentalBean.getUrlTemporal());
		alfrescoEntrada.setTipoOperacion(gestorDocumentalBean.getOpcion());
		try {
			alfrescoRespuesta = ejecutarBusqueda(alfrescoEntrada, gestorDocumentalBean.getParametro());

			FacesMessage message = new FacesMessage("Ëxito ", alfrescoRespuesta.getMensaje());
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (ClienteRestFulExcepcion e) {
			ponerMensajeError(e.getMessage());
		}
	}

	/**
	 * <b> Metodo para ejecurtar la busqueda de los archivos. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 8 mar. 2024]
	 * </p>
	 *
	 * @param alfrescoEntrada objeto del tipo AlfrescoEntrada
	 * @param parametro       para metro de busqueda
	 * @return retoirna un objeto del tipo AlfrescoRespuesta
	 * @throws ClienteRestFulExcepcion Excepcion del tipo cliente
	 */
	public AlfrescoRespuesta ejecutarBusqueda(AlfrescoEntrada alfrescoEntrada, String parametro)
			throws ClienteRestFulExcepcion {
		switch (alfrescoEntrada.getTipoOperacion()) {
		case "I":
			alfrescoEntrada.setId(parametro);
			alfrescoRespuesta = alfrescoGestorCliente.buscarArchivo(alfrescoEntrada);
			break;
		case "U":
			parametro = alfrescoRespuesta.getAlfrescoSalida().getUrl();
			alfrescoEntrada.setUrl(parametro);
			alfrescoRespuesta = alfrescoGestorCliente.buscarArchivo(alfrescoEntrada);
			break;
		case "N":
			alfrescoEntrada.setNombreArchivo(parametro);
			alfrescoRespuesta = alfrescoGestorCliente.buscarArchivo(alfrescoEntrada);
			break;
		default:
			FacesMessage message = new FacesMessage("Busqueda de Archivo", "Error al ejecutar la búsqueda");
			FacesContext.getCurrentInstance().addMessage(null, message);
		    break;
		}
		return alfrescoRespuesta;
	}

	/**
	 * <b> Metodo para realizar la descarga de archivos simples. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 8 mar. 2024]
	 * </p>
	 *
	 * @return retorna un objeto del tipo StreamedContent
	 */
	public StreamedContent descargarArchivoSimple() {
		return DefaultStreamedContent.builder().name(alfrescoRespuesta.getAlfrescoSalida().getNombreArchivo())
				.contentType(alfrescoRespuesta.getAlfrescoSalida().getTipoContenido())
				.stream(() -> new ByteArrayInputStream(
						Base64.decodeBase64(alfrescoRespuesta.getAlfrescoSalida().getCadenaArchivo())))
				.build();
	}

	/**
	 * <b> Metodo para realizar la descarga de archivos multiples. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 8 mar. 2024]
	 * </p>
	 *
	 * @param listaAlfrescoSalida un objeto del tipo List<AlfrescoSalida>
	 * @return un objeto del tipo StreamedContent
	 */
	public StreamedContent descargarArchivoMultiple(List<AlfrescoSalida> listaAlfrescoSalida) {
		byte[] byteArchivo = procesarZipStream(listaAlfrescoSalida);
		return DefaultStreamedContent.builder().name("archivoStream.zip").contentType("application/zip")
				.stream(() -> new ByteArrayInputStream(byteArchivo)).build();
	}

	/**
	 * <b> Metodo para descargar archivos. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 11 mar. 2024]
	 * </p>
	 *
	 * @return retorna un obejto del tipo StreamedContent
	 */
	public StreamedContent descargarArchivo() {
		if ("U".equals(gestorDocumentalBean.getOpcion())) {
			return descargarArchivoMultiple(alfrescoRespuesta.getListaAlfrescoSalida());
		} else {
			return descargarArchivoSimple();
		}
	}

	/**
	 * <b> Metodo para validar el directorio. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 11 mar. 2024]
	 * </p>
	 *
	 */
	public void validarDirectorio() {

		generarDirectorioAlfresco();
		
		try {
			alfrescoEntrada.setTipoOperacion("U");
			alfrescoEntrada.setUrl(gestorDocumentalBean.getUrlTemporal());
			alfrescoRespuesta = alfrescoGestorCliente.buscarArchivo(alfrescoEntrada);
			
			 List<String> listaCadena = recuperarListaId(alfrescoRespuesta.getListaAlfrescoSalida());
			
			 alfrescoEntrada.setTipoOperacion("I");
			 alfrescoRespuesta.setListaAlfrescoSalida(procesarBusquedaArchivo(alfrescoEntrada, listaCadena));

			FacesMessage message = new FacesMessage("Ëxito ", alfrescoRespuesta.getMensaje());
			FacesContext.getCurrentInstance().addMessage(null, message);

		} catch (ClienteRestFulExcepcion e) {
			ponerMensajeError(e.getMessage());
		}
	}
	
	/**
	 * <b> Metodo para recuperar los identiifcadores del alfresco. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 11 mar. 2024]
	 * </p>
	 *
	 * @param lista objeto del tipo List<AlfrescoSalida>
	 * @return retoran una lista del tipo List<String>
	 */
	public List<String> recuperarListaId(List<AlfrescoSalida> lista) {
		List<String> listaCadenaAux = new ArrayList<>();
		for (AlfrescoSalida salida : lista) {
			listaCadenaAux.add(salida.getArchivoId());
		}
		return listaCadenaAux;
	}

	/**
	 * <b> Metodo para procesar los identificadores a una lista
	 * List<AlfrescoSalida>. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 11 mar. 2024]
	 * </p>
	 *
	 * @param alfrescoEntrada objeto del tipo AlfrescoEntrada
	 * @param listaCadena     lista del tipo cadena
	 * @return retorna un objeto del tipo List<AlfrescoSalida>
	 * @throws ClienteRestFulExcepcion Excepcion del tipo Cliente
	 */
	public List<AlfrescoSalida> procesarBusquedaArchivo(AlfrescoEntrada alfrescoEntrada, List<String> listaCadena)
			throws ClienteRestFulExcepcion {
		AlfrescoSalida alfrescoSalida = null;
		List<AlfrescoSalida> listaAlfrescoSalida = new ArrayList<>();

		for (String id : listaCadena) {
			alfrescoEntrada.setId(id);
			alfrescoSalida = alfrescoGestorCliente.buscarArchivo(alfrescoEntrada).getAlfrescoSalida();
			listaAlfrescoSalida.add(new AlfrescoSalida(alfrescoSalida.getArchivoId(), alfrescoSalida.getUrl(),
					alfrescoSalida.getNombreArchivo(), alfrescoSalida.getCadenaArchivo()));
		}

		return listaAlfrescoSalida;
	}

	/**
	 * <b> Metodo para la descarga de archivos multiples. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 11 mar. 2024]
	 * </p>
	 *
	 * @return retorna un objeto del tipo StreamedContent
	 */
	public StreamedContent descargarArchivoMultiple() {
		try {
			streamedContentArchivo = descargarArchivoMultiple(alfrescoRespuesta.getListaAlfrescoSalida());
		} catch (Exception e) {
			ponerMensajeError(e.getMessage());
		}
		return streamedContentArchivo;
	}

	public byte[] procesarZipStream(List<AlfrescoSalida> listaAlfrescoSalida) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		DataInputStream documento = null;
		try {
			for (AlfrescoSalida salida : listaAlfrescoSalida) {
				byte[] byteArray = null;
				byteArray = Base64.decodeBase64(salida.getCadenaArchivo());
				documento = new DataInputStream(new ByteArrayInputStream(byteArray));
				ZipEntry zipEntry = new ZipEntry(salida.getNombreArchivo());
				zos.putNextEntry(zipEntry);
				zos.write(escribirArregloByte(documento));
			}
			zos.close();
		} catch (IOException e) {
			Log.error(e);
		}
		return baos.toByteArray();
	}

	public static byte[] escribirArregloByte(InputStream inputStream) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		byte[] result = null;
		int len;
		// Lectura de bytes desde el objeto imputStream y su almacenamiento en buffer
		try {
			while ((len = inputStream.read(buffer)) != -1) {
				// Escritura de bytes desde el buffer adentro del OutputStream
				os.write(buffer, 0, len);
			}
			result = os.toByteArray();
		} catch (Exception e) {
			Log.error(e);
		} finally {
			try {
				if (os.size() > 0) {
					os.close();
				}
			} catch (Exception e) {
				Log.error(e);
			}
		}
		return result;
	}

	/**
	 * <b> Metodo para limpiar la informacion de la forma web. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 8 mar. 2024]
	 * </p>
	 *
	 */
	public void limpiarInformacion() {
		alfrescoEntrada = new AlfrescoEntrada();
		alfrescoRespuesta = new AlfrescoRespuesta();
		gestorDocumentalBean.setUrl(null);
		gestorDocumentalBean.setMostrarUploadFileSimple(Boolean.FALSE);
		gestorDocumentalBean.setMostrarUploadFileMultiple(Boolean.FALSE);
	}

}
