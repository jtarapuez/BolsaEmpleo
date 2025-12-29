/*
 * Copyright 2025 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR 
 * Todos los derechos reservados
 */

package ec.gob.iess.proyecto.base.bolsaEmpleo.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FlowEvent;

import lombok.Getter;
import lombok.Setter;

/**
 * <b>
 * Clase del tipo Bean para el registro de postulante en la Bolsa de Empleo.
 * </b>
 * 
 * @author IESS
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: IESS $, $Date: 29 dic. 2025 $]
 *          </p>
 */
@Getter
@Setter
@Named
@ViewScoped
public class RegistroPostulanteBean implements Serializable {

    /**
     * Serialización de la clase RegistroPostulanteBean
     */
    private static final long serialVersionUID = 1L;

    /**
     * Total de pasos
     */
    private static final int TOTAL_PASOS = 4;

    /**
     * Paso actual del stepper (1-4)
     */
    private int pasoActual = 1;

    /**
     * <b>
     * Método que controla la navegación entre pasos del wizard.
     * </b>
     * <p>
     * [Author: IESS, Date: 29 dic. 2025]
     * </p>
     *
     * @param event Evento del flujo del wizard
     * @return String con el nombre del siguiente paso
     */
    public String onFlowProcess(FlowEvent event) {
        // Actualizar paso actual basado en el paso nuevo
        String newStep = event.getNewStep();
        if ("infoBasica".equals(newStep)) {
            pasoActual = 1;
        } else if ("formacionAcademica".equals(newStep)) {
            pasoActual = 2;
        } else if ("experienciaLaboral".equals(newStep)) {
            pasoActual = 3;
        } else if ("cursosConocimientos".equals(newStep)) {
            pasoActual = 4;
        }
        return newStep;
    }

    /**
     * Avanzar al siguiente paso
     */
    public void siguientePaso() {
        if (pasoActual < TOTAL_PASOS) {
            pasoActual++;
        }
    }

    /**
     * Retroceder al paso anterior
     */
    public void pasoAnterior() {
        if (pasoActual > 1) {
            pasoActual--;
        }
    }

    /**
     * Verificar si un paso está completado
     */
    public boolean isPasoCompletado(int paso) {
        return paso < pasoActual;
    }

    /**
     * Verificar si un paso está activo
     */
    public boolean isPasoActivo(int paso) {
        return paso == pasoActual;
    }

    /**
     * Obtener el porcentaje de progreso para la línea del stepper
     * Calcula el porcentaje basado en los pasos completados
     */
    public int getPorcentajeProgreso() {
        if (pasoActual == 1) {
            return 0;
        } else if (pasoActual == 2) {
            return 33;
        } else if (pasoActual == 3) {
            return 66;
        } else {
            return 100;
        }
    }
}

