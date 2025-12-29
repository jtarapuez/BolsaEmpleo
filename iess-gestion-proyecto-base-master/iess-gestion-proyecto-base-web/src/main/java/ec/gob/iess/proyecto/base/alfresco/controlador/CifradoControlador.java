/*
 * Copyright 2025 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */

package ec.gob.iess.proyecto.base.alfresco.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import ec.gob.iess.componente.autorizador.jsf.dto.UsuarioSesionDto;
import ec.gob.iess.componente.autorizador.jsf.utilitario.UtilitarioJsf;
import ec.gob.iess.componente.cifrado.enumeracion.Tipo;
import ec.gob.iess.componente.cifrado.excepcion.CifradoExcepcion;
import ec.gob.iess.componente.cifrado.modelo.LlaveSimetrica;
import ec.gob.iess.componente.cifrado.servicio.CifradoServicio;
import ec.gob.iess.componente.cifrado.servicio.CifradoTipo;
import ec.gob.iess.componente.comun.excepciones.ServicioExcepcion;
import ec.gob.iess.componente.comun.logs.Log;
import ec.gob.iess.componente.repositorio.excepcion.DAOExcepcion;
import ec.gob.iess.componente.seguridad.ejb.entidad.ConexionNativaEntidad;
import ec.gob.iess.componente.seguridad.ejb.entidad.LlavePorServicioEntidad;
import ec.gob.iess.componente.seguridad.ejb.entidad.ModuloEntidad;
import ec.gob.iess.componente.seguridad.ejb.servicio.ConexionNativaServicio;
import ec.gob.iess.componente.seguridad.ejb.servicio.LlaveCifradoServicio;
import ec.gob.iess.componente.seguridad.ejb.servicio.ModuloServicio;
import ec.gob.iess.componente.transversal.ejb.dto.pcocat.CatalogoDetalle;
import ec.gob.iess.componente.transversal.ejb.servicio.CatalogoServicio;
import lombok.Getter;
import lombok.Setter;

/**
 * <b> Clase del tipo Controlador para el cifrado de cadenas y conexiones. </b>
 * 
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: francisco.gagliardo $, $Date: 10 abr. 2025 $]
 *          </p>
 */
@Getter
@Setter
@Named
@ViewScoped
public class CifradoControlador extends UtilitarioJsf implements Serializable {

	private static final String EQUIPO = "ARQ_EQUIP";
	private static final String APLICACION = "ARQ_APLIC";
	
	/**
	* Seriabilizacion de la clase CifradoControlador
	*/
	private static final long serialVersionUID = -3674450519345232227L;
	


	@Inject
	@CifradoTipo(Tipo.Aes256)
	private CifradoServicio cifradoAes256;

	@EJB
	private ConexionNativaServicio conexionNativaServicio;
	
	@EJB
	private LlaveCifradoServicio llaveCifradoServicio;

	@Getter
	@Setter
	private String llavePublica;

	@Getter
	@Setter
	private String salt;
	
	@Getter
	@Setter
	private String llavePublicaAux;

	@Getter
	@Setter
	private String saltAux;

	@Getter
	@Setter
	private String cadenaCifrado;

	@Getter
	@Setter
	private String cadenaDescifrado;

	@Getter
	@Setter
	private String resultadoCifrado;

	@Getter
	@Setter
	private String resultadoDescifrado;

	@Getter
	@Setter
	private boolean mostrarLlave;

	@Getter
	@Setter
	private boolean mostrarCifrado;

	@Getter
	@Setter
	private boolean mostrarDescifrado;

	@Getter
	@Setter
	private boolean mostrarConexionNativa;

	@Getter
	@Setter
	private boolean mostrarBotonConexionNativa;

	@Getter
	@Setter
	private transient List<ModuloEntidad> listaModulo;

	@Getter
	@Setter
	private String moduloId;

	@Getter
	@Setter
	private ModuloEntidad modulo;

	@Getter
	@Setter
	private String equipoDesarrollo;

	@Getter
	@Setter
	private transient List<CatalogoDetalle> listaEquipoDesarrollo;

	@Getter
	@Setter
	private String codigoAplicacion;

	@Getter
	@Setter
	private transient List<CatalogoDetalle> listaAplicacion;

	@Getter
	@Setter
	private String url;

	@Getter
	@Setter
	private String usuario;

	@Getter
	@Setter
	private String clave;

	@Getter
	@Setter
	private String observacion;

	@Getter
	@Setter
	private ConexionNativaEntidad conexionNativa;

	@Getter
	@Setter
	private ConexionNativaEntidad conexionNativaAux;

	@Getter
	@Setter
	private List<ConexionNativaEntidad> listaConexionNativa;

	@Getter
	@Setter
	private boolean mostrarDataConexionNativa;

	@Getter
	@Setter
	private boolean mostrarBotonDesenciptar;
	
	@Getter
	@Setter
	private List<LlavePorServicioEntidad> listaLlavePorServicio;
	
	@Getter
	@Setter
	private LlavePorServicioEntidad llavePorServicioEntidad;
	
	@Getter
	@Setter
	private String codigoServicio;

	@Inject
	private ModuloServicio moduloServicio;
	
	@Inject
	private CatalogoServicio catalogoServicio;

	@Inject
	@ManagedProperty("#{menuControlador.loginControladorBb.usuarioSesion}")
	private UsuarioSesionDto usuarioSesion;

	
	/**
	 * Carga de los parametros de inicio de la clase
	 */
	@PostConstruct
	public void inicio() {
		mostrarLlave = Boolean.TRUE;
		mostrarCifrado = Boolean.FALSE;
		mostrarDescifrado = Boolean.FALSE;
		mostrarConexionNativa = Boolean.FALSE;
		mostrarBotonConexionNativa = Boolean.FALSE;
		mostrarDataConexionNativa = Boolean.FALSE;
		mostrarBotonDesenciptar = Boolean.FALSE;
		cargarLlave();
	}

	/**
	 * <b> Metodo de limpieza de datos del metodo validarLlaveCifrado</b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void validarLlaveCifrado() {
		mostrarLlave = Boolean.FALSE;
		mostrarCifrado = Boolean.TRUE;
		mostrarDescifrado = Boolean.TRUE;
		mostrarConexionNativa = Boolean.FALSE;
		mostrarBotonConexionNativa = Boolean.TRUE;
	}

	/**
	 * <b> Metodo de limpieza de datos del metodo validarConexionNativa </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void validarConexionNativa() {
		mostrarLlave = Boolean.FALSE;
		mostrarCifrado = Boolean.FALSE;
		mostrarDescifrado = Boolean.FALSE;
		mostrarConexionNativa = Boolean.TRUE;
		mostrarBotonConexionNativa = Boolean.FALSE;
		cargarModulo();
		cargarEquipo();
		cargarAplicacion();
	}

	/**
	 * <b> Metodo de encriptacion de datos del metodo cifradoCadenaAes256 </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void cifradoCadenaAes256() {
		resultadoCifrado = encriptarCadenaConexionNativa(cadenaCifrado);
	}

	/**
	 * <b> Metodo de desencriptacion de datos del metodo descifradoCadenaAes256 </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void descifradoCadenaAes256() {
		resultadoDescifrado = desenciptarCadenaConexionNativa(cadenaDescifrado);
	}
	
	
	public List<CatalogoDetalle> cargarDetalleCatalogo(String codigoCatalogo) throws ServicioExcepcion {
		return catalogoServicio.listarDetallePorCatalago(codigoCatalogo);
	}


	/**
	 * <b> Metodo para cargar los modulos del seguridades KSSEGTMODULOS. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void cargarModulo() {
		try {
			listaModulo = moduloServicio.consultarModulo();
			Log.info(CifradoControlador.class.getName(), String.valueOf(listaModulo.size()));
		} catch (ServicioExcepcion e) {
			ponerMensajeError("No se pudo obtener los módulos desde la base de datos.");
		}
	}

	/**
	 * <b> Metodo para cargar los equipos de desarrollo. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void cargarEquipo() {
		try {
			listaEquipoDesarrollo = cargarDetalleCatalogo(EQUIPO);
		} catch (ServicioExcepcion e) {
			ponerMensajeError("No se pudo obtener los equipos de desarrollo desde la base de datos.");
		}
	}

	/**
	 * <b> Metodo para carga codigo de aplicacion de las web app. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void cargarAplicacion() {
		try {
			listaAplicacion =  cargarDetalleCatalogo(APLICACION);
		} catch (ServicioExcepcion e) {
			ponerMensajeError("No se pudo obtener las códigos de las aplicaciones desde la base de datos.");
		}
	}
	
	/**
	 * <b>
	 * Metodo para cargar las llaves de encriptyacion.
	 * </b>
	 * <p>[Author: francisco.gagliardo, Date: 8 may. 2025]</p>
	 *
	 */ 
	public void cargarLlave() {
		try {
			listaLlavePorServicio = llaveCifradoServicio.obtenerLlave();
		} catch (ServicioExcepcion e) {
			ponerMensajeError(e.getMessage());
		}
	}
	
	/**
	 * <b>
	 * Metodo carga las llaves publicas y sal desde las base  de datos.
	 * </b>
	 * <p>[Author: francisco.gagliardo, Date: 8 may. 2025]</p>
	 *
	 */ 
	public void cargarLlaveEncriptacion() {
		if (codigoServicio != null)  {
			try {
				llavePorServicioEntidad = llaveCifradoServicio.consultarLlaveCifradoPorCodigo(codigoServicio);
				llavePublica = llavePorServicioEntidad.getLlavePublica();
				salt = llavePorServicioEntidad.getSalt();
			} catch (DAOExcepcion e) {
				ponerMensajeError("No se pudo obtener las códigos de servicio desde la base de datos.");
			}
			validarLlaveCifrado();
		}
	}

	/**
	 * <b> Metodo para el registro de la conexion nativa. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void registroConexion() {
		listaConexionNativa = new ArrayList<>();
		ConexionNativaEntidad conexionNativaEntidad = cargarConexion();
		if (conexionNativaEntidad != null) {
			try {
				conexionNativaServicio.insertarConexionNativa(conexionNativaEntidad);
				listaConexionNativa = conexionNativaServicio.consultarConexionNativa();
				ponerMensajeInfo("La conexión nativa se ha registrado exitosamente!");
				mostrarConexionNativa = Boolean.FALSE;
				mostrarDataConexionNativa = Boolean.TRUE;
			} catch (ServicioExcepcion e) {
				ponerMensajeError(e.getMessage());
				mostrarDataConexionNativa = Boolean.FALSE;
			}
		}
	}

	/**
	 * <b> Metodo para realizar la carga de informacion de la conexion nativa. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 * @return retorn un objeto del tipo ConexionNativaEntidad
	 */
	public ConexionNativaEntidad cargarConexion() {
		ConexionNativaEntidad conexion = new ConexionNativaEntidad();
		modulo = new ModuloEntidad();
		modulo.setId(moduloId);
		conexion.setModulo(modulo);
		conexion.setCodigoAplicacion(codigoAplicacion.trim());
		conexion.setUrl(encriptarCadenaConexionNativa(url.trim()));
		conexion.setUsuario(encriptarCadenaConexionNativa(usuario.trim()));
		conexion.setClave(encriptarCadenaConexionNativa(clave.trim()));
		conexion.setObservacion(observacion.trim());
		conexion.setEquipo(equipoDesarrollo);
		conexion.setLlavePorServicioEntidad(llavePorServicioEntidad);
		conexion.setEstado("A");
		conexion.setFechaRegistro(new Date());
		conexion.setUsuarioCreacion(usuarioSesion.getUsuarioLogin());
		conexion.setIp(obtenerIpHost());
		return conexion;
	}

	/**
	 * <b> Metodo par encriptar la conexion nativa Aes256. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 * @param cadenaCifrado cadean a cifrar
	 * @return retorna una cadena encriptada
	 */
	public String encriptarCadenaConexionNativa(String cadenaCifrado) {
		String cadena = null;
		LlaveSimetrica llaveSimetrica = new LlaveSimetrica(llavePublica, salt);
		try {
			cadena = cifradoAes256.cifrarAES256(llaveSimetrica, cadenaCifrado);
		} catch (CifradoExcepcion e1) {
			ponerMensajeError("Error al cifrar en formato Aes256 la cadena: " + cadenaCifrado);
			Log.error(CifradoControlador.class.getName(), "Error al cifrar en formato Aes256", null);
		}
		return cadena;
	}

	/**
	 * <b> Metodo para decencriptar la conexion nativa Aes256. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 * @param cadenaDescifrado cadena de descifrado
	 * @return retorna una cadena desencriptada
	 */
	public String desenciptarCadenaConexionNativa(String cadenaDescifrado) {
		String cadena = null;
		LlaveSimetrica llaveSimetrica = new LlaveSimetrica(llavePublica, salt);
		try {
			cadena = cifradoAes256.descifrarAES256(llaveSimetrica, cadenaDescifrado);
		} catch (CifradoExcepcion e1) {
			ponerMensajeError("Error al cifrar en formato Aes256 la cadena: " + cadenaCifrado);
			Log.error(CifradoControlador.class.getName(), "Error al descifrar en formato Aes256", null);
		}
		return cadena;
	}
	
	
	/**
	 * <b> Metodo para decencriptar la conexion nativa Aes256 por llave y Salt. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 * @param cadenaDescifrado cadena de descifrado
	 * @return retorna una cadena desencriptada
	 */
	public String desenciptarCadenaPorLlaveYSalt(String llavePublica, String salt, String cadenaDescifrado) {
		String cadena = null;
		LlaveSimetrica llaveSimetrica = new LlaveSimetrica(llavePublica, salt);
		try {
			cadena = cifradoAes256.descifrarAES256(llaveSimetrica, cadenaDescifrado);
		} catch (CifradoExcepcion e1) {
			ponerMensajeError("Error al cifrar en formato Aes256 la cadena: " + cadenaCifrado);
			Log.error(CifradoControlador.class.getName(), "Error al descifrar en formato Aes256", null);
		}
		return cadena;
	}

	/**
	 * <b> Metodo para desenciptar el objeto conexion nativa al seleccionar del datatable. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void desencriptarCadenaSeleccion() {
		if (conexionNativa != null) {

			String llavePublicaAux = conexionNativa.getLlavePorServicioEntidad().getLlavePublica();
			String saltAux = conexionNativa.getLlavePorServicioEntidad().getSalt();

			conexionNativaAux = new ConexionNativaEntidad();
			conexionNativaAux
					.setUrl(desenciptarCadenaPorLlaveYSalt(llavePublicaAux, saltAux, conexionNativa.getUrl().trim()));
			conexionNativaAux.setUsuario(
					desenciptarCadenaPorLlaveYSalt(llavePublicaAux, saltAux, conexionNativa.getUsuario().trim()));
			conexionNativaAux.setClave(
					desenciptarCadenaPorLlaveYSalt(llavePublicaAux, saltAux, conexionNativa.getClave().trim()));
			mostrarBotonDesenciptar = Boolean.FALSE;
		}
	}

	/**
	 * <b> Metodo para seleccionar la conexion nativa de la fila del data table. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 * @param event evento listener
	 */
	public void seleccionarFila(SelectEvent<ConexionNativaEntidad> event) {
		conexionNativaAux = null;
		mostrarBotonDesenciptar = Boolean.TRUE;
		FacesMessage msg = new FacesMessage("Conexión seleccionada", String.valueOf(event.getObject().getConexionId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * <b> Metodo para cerrar la conexion nativa y reseteo de parametros. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void cerrarConexionNativa() {
		conexionNativaAux = null;
	}

	/**
	 * <b> Metodo para limpiar la informacion inicial del proceso de registro de
	 * conexion nativa. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void limpiarInformacion() {
		moduloId = null;
		equipoDesarrollo = null;
		codigoAplicacion = null;
		mostrarLlave = Boolean.TRUE;
		mostrarCifrado = Boolean.FALSE;
		mostrarDescifrado = Boolean.FALSE;
		mostrarConexionNativa = Boolean.FALSE;
		mostrarBotonConexionNativa = Boolean.FALSE;
		mostrarDataConexionNativa = Boolean.FALSE;
		mostrarBotonDesenciptar = Boolean.FALSE;
		llavePublica = null;
		salt = null;
		cadenaCifrado = null;
		cadenaDescifrado = null;
		resultadoCifrado = null;
		resultadoDescifrado = null;
		url = null;
		usuario = null;
		clave = null;
		observacion = null;
		conexionNativa = null;
		conexionNativaAux = null;
		listaModulo = new ArrayList<>();
		listaEquipoDesarrollo = new ArrayList<>();
		listaAplicacion = new ArrayList<>();
		listaConexionNativa = new ArrayList<>();
        listaLlavePorServicio =  new ArrayList<LlavePorServicioEntidad>();
        llavePorServicioEntidad = new LlavePorServicioEntidad();
        codigoServicio = null;
		cargarModulo();
		cargarEquipo();
		cargarAplicacion();
		cargarLlave();
	}

	/**
	 * <b> Metodo para salir del formulario de registro de conexion nativa. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 23 abr. 2025]
	 * </p>
	 *
	 */
	public void salirFormulario() {
		limpiarInformacion();
	}

}
