/*
 * Copyright 2025 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */

package ec.gob.iess.proyecto.base.alfresco.controlador;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.gob.iess.componente.autorizador.jsf.utilitario.UtilitarioJsf;
import ec.gob.iess.componente.comun.logs.Log;
import ec.gob.iess.componente.otp.OTPApi;
import ec.gob.iess.componente.otp.constante.EstadoOTP;
import ec.gob.iess.componente.otp.constante.TipoAppOTP;
import ec.gob.iess.componente.otp.core.ExtraOTP;
import ec.gob.iess.componente.otp.dto.EmailOTPDTO;
import ec.gob.iess.componente.otp.dto.SemillaUsuarioDTO;
import ec.gob.iess.componente.otp.dto.ValidaDTO;
import ec.gob.iess.componente.otp.excepcion.OTPExcepcion;
import lombok.Data;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <b> Clase del tipo controlador para la implementación del código OTP. </b>
 * 
 * @author francisco.gagliardo
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: francisco.gagliardo $, $Date: 16 ene. 2025 $]
 *          </p>
 */
@Named
@ViewScoped
@Data
public class CodigoOTPControlador extends UtilitarioJsf implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -6113373426151644214L;

	@Inject
	private OTPApi otpApi;

	private SemillaUsuarioDTO semillaUsuario;

	private EmailOTPDTO emailUser;

	private String codApli = "IESS-ARQ";

	private String userId = "1713953964";

	private String ipCliente;

	private String trxCodigo;

	private String otpCodigo = "";

	private Calendar tiempo;

	private String generaOTPBtn;

	private String tiempoRestante;

	private String paisValue;

	private String valorForma = "frmOTP:pnlOTP";

	/**
	 * <b> Metodo para inicializar el funcionamiento del OTP. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	@PostConstruct
	public void inicializar() {
		this.obtenerSemilla();
		this.generaCodTrx();
		this.emailUser = new EmailOTPDTO();
		/* Información del remitente según configuración del negocio */
		this.emailUser.setCorreoEmisor("notificaciones@iess.gob.ec");
		this.emailUser.setNombreEmisor("Notificaciones seguridad IESS");
		/* Información del receptor debe ser el usuario logueado de la aplicación */
		this.emailUser.setCorreoReceptor("francisco.gagliardo@iess.gob.ec");
		this.emailUser.setNombresReceptor("Francisco Gagliardo");
		/* Nombre de la transacción u opción de menú que genera el código OTP */
		this.emailUser.setNombreTransaccion("MENFR_20190402153605_033_P");
		/* Validación de bloqueos activos en el sistema */
		try {

			boolean validar = this.validaBloqueo(this.otpApi.verificaBloqueo(this.semillaUsuario));

			if (validar) {
				generaOTPBtn = "G";
			}
		} catch (OTPExcepcion excepcion) {
			Log.error(CodigoOTPControlador.class.getName(), "Error al validar bloqueo", null);
		}
	}

	/**
	 * <b> Metodo para obtener el valor de la IP. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	public void extraValuesIp() {
		this.ipCliente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ipUser");
	}

	/**
	 * <b> Metodo para obtener el valor del País. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	public void extraValuesPais() {
		this.paisValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pais");
	}

	/**
	 * <b> Metodo para obtener la semilla OTP. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	private void obtenerSemilla() {
		try {
			this.semillaUsuario = this.otpApi.obtenerSemilla(TipoAppOTP.WEB_APP, codApli, userId,
					this.ipCliente == null ? ExtraOTP.ipServer() : this.ipCliente);
		} catch (OTPExcepcion | UnknownHostException excepcion) {
			Log.error(CodigoOTPControlador.class.getName(), "Error al obtener la semilla para el código OTP", null);
		}
	}

	/**
	 * <b> Metodo para generar la transacción TRX. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	private void generaCodTrx() {
		this.trxCodigo = this.otpApi.generaTrxOTP(this.codApli);
		this.otpCodigo = "";
	}

	/**
	 * <b> Metodo para validar los bloqueos. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 * @param bloqueo objeto del tipo dto ValidaDTO
	 * @return retorna un valor booleano
	 */
	private boolean validaBloqueo(ValidaDTO bloqueo) {
		if (bloqueo.getValido().equals(EstadoOTP.OTP_BLOQUEADO)) {
			this.tiempo = Calendar.getInstance();
			this.tiempo.setTime(bloqueo.getTiempoBloqueo());
			this.generaOTPBtn = "B";
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", bloqueo.getMensaje());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			PrimeFaces.current().ajax().update("frmOTP");
			PrimeFaces.current().ajax().update("msgOTP");
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * <b> Metodo para validar el código OTP. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	public void validaOTP() {
		try {
			ValidaDTO validacion = this.otpApi.validaOTP(semillaUsuario, this.otpCodigo, this.trxCodigo, emailUser);
			if (validacion.getValido().equals(EstadoOTP.OTP_EXITOSO)) {
				this.generaCodTrx();
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", validacion.getMensaje());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else if (!validacion.getValido().equals(EstadoOTP.OTP_BLOQUEADO)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", validacion.getMensaje());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				this.generaCodTrx();
				this.validaBloqueo(validacion);
				return;
			}
			this.generaOTPBtn = "G";
			this.otpCodigo = "";
			PrimeFaces.current().ajax().update("msgOTP");
			PrimeFaces.current().ajax().update(valorForma);
		} catch (OTPExcepcion excepcion) {
			Log.error(CodigoOTPControlador.class.getName(), "Error al validar el código OTP", null);
		}
	}

	/**
	 * <b> Metodo para generar el código OTP. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	public void generaOTP() {
		try {
			/* Valida si existe un bloqueo activo */
			if (this.validaBloqueo(this.otpApi.verificaBloqueo(this.semillaUsuario))) {
				this.otpApi.generarCodigoOTP(this.semillaUsuario, this.trxCodigo, "generaOTPValidación()", null, null,
						this.ipCliente == null ? ExtraOTP.ipServer() : this.ipCliente, emailUser);
				this.tiempo = Calendar.getInstance();
				this.tiempo.set(Calendar.HOUR, 0);
				this.tiempo.set(Calendar.MINUTE, 3);
				this.tiempo.set(Calendar.SECOND, 0);
				this.generaOTPBtn = "V";
			}
			PrimeFaces.current().ajax().update(valorForma);
		} catch (OTPExcepcion | UnknownHostException excepcion) {
			Log.error(CodigoOTPControlador.class.getName(), "Error al generar para el código OTP", null);
		}
	}

	/**
	 * <b> Metodo para generar la cuneta regresiva. </b>
	 * <p>
	 * [Author: francisco.gagliardo, Date: 17 ene. 2025]
	 * </p>
	 *
	 */
	public void cuentaRegresiva() {
		this.tiempo.set(Calendar.SECOND, this.tiempo.get(Calendar.SECOND) - 1);
		if (this.tiempo.get(Calendar.MINUTE) == 0 && this.tiempo.get(Calendar.SECOND) == 0) {
			this.validaOTP();
			this.generaOTPBtn = "G";
			PrimeFaces.current().ajax().update(valorForma);
		} else {
			this.tiempoRestante = new SimpleDateFormat("mm:ss").format(this.tiempo.getTime());
			PrimeFaces.current().ajax().update("frmOTP:txtCount");
		}
	}
}
