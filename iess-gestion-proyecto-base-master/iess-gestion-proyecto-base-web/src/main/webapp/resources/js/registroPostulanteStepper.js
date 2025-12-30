/**
 * JavaScript para sincronizar stepper independiente con wizard
 * Archivo: registroPostulanteStepper.js
 * 
 * NOTA: La variable currentStep se inicializa desde el XHTML usando EL:
 * var currentStep = #{registroPostulanteBean.pasoActual};
 */

// Variables globales (se inicializan desde el XHTML)
var currentStep = 1; // Se sobrescribe desde el XHTML
var totalSteps = 4;

// Mapeo de pasos a IDs de tabs
var stepToTab = {
    1: 'infoBasica',
    2: 'formacionAcademica',
    3: 'experienciaLaboral',
    4: 'cursosConocimientos'
};

// Mapeo inverso: tab ID a número de paso
var tabToStep = {
    'infoBasica': 1,
    'formacionAcademica': 2,
    'experienciaLaboral': 3,
    'cursosConocimientos': 4
};

// Función mejorada para detectar paso actual
function detectCurrentStep() {
    var wizard = PF('stepperWizard');
    
    // Método 1: Usar getStep() del wizard (más confiable)
    if (wizard) {
        try {
            var currentTab = wizard.getStep();
            if (currentTab) {
                var step = tabToStep[currentTab];
                if (step) {
                    return step;
                }
            }
        } catch (e) {
            // Ignorar errores
        }
    }
    
    // Método 2: Buscar el tab activo en el DOM por ID
    var formId = 'formRegistroPostulanteStepper';
    for (var tabId in tabToStep) {
        var fullTabId = formId + ':' + tabId;
        var tabElement = document.getElementById(fullTabId);
        if (tabElement) {
            var style = window.getComputedStyle(tabElement);
            if (style.display !== 'none' && style.visibility !== 'hidden') {
                return tabToStep[tabId];
            }
        }
    }
    
    // Método 3: Buscar por clase y visibilidad
    var tabs = document.querySelectorAll('.custom-wizard-stepper .ui-wizard-content .ui-tabview-panel');
    for (var i = 0; i < tabs.length; i++) {
        var tab = tabs[i];
        var style = window.getComputedStyle(tab);
        if (style.display !== 'none' && style.visibility !== 'hidden') {
            // Intentar extraer el ID del tab
            var tabId = tab.id;
            if (tabId) {
                var parts = tabId.split(':');
                var cleanTabId = parts[parts.length - 1];
                var step = tabToStep[cleanTabId];
                if (step) return step;
            }
            // Si no podemos extraer el ID, usar el índice
            return i + 1;
        }
    }
    
    return currentStep || 1;
}

// Ir a un paso específico
function goToStep(step) {
    if (step < 1 || step > totalSteps) return;
    
    var wizard = PF('stepperWizard');
    if (!wizard) {
        // Si el wizard aún no está listo, esperar un poco
        setTimeout(function() { goToStep(step); }, 100);
        return;
    }
    
    // Navegar al tab correspondiente
    var tabId = stepToTab[step];
    if (tabId) {
        wizard.loadStep(tabId);
        updateStepper(step);
    }
}

// Siguiente paso - MEJORADO
function nextStep() {
    var wizard = PF('stepperWizard');
    if (!wizard) return;
    
    // Guardar el paso actual antes de cambiar
    var oldStep = currentStep;
    
    // Si estamos en el último paso (4) y se hace clic en "Finalizar"
    if (oldStep === totalSteps) {
        // Marcar el paso 4 como completado (verde)
        markStepAsCompleted(totalSteps);
        return;
    }
    
    // Llamar al método next del wizard
    wizard.next();
    
    // Actualizar stepper inmediatamente (optimista)
    var newStep = Math.min(oldStep + 1, totalSteps);
    updateStepper(newStep);
    
    // Sincronizar después de que el wizard actualice (confirmación)
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 100);
    
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 300);
    
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 600);
}

// Marcar un paso como completado (verde)
function markStepAsCompleted(step) {
    if (step < 1 || step > totalSteps) return;
    
    var steps = document.querySelectorAll('.custom-stepper-step');
    var progress = document.getElementById('stepperProgress');
    
    // Marcar todos los pasos hasta el indicado como completados
    steps.forEach(function(stepElement, index) {
        var stepNum = index + 1;
        stepElement.classList.remove('active', 'completed');
        
        if (stepNum <= step) {
            stepElement.classList.add('completed');
        }
    });
    
    // Actualizar línea de progreso al 100%
    if (progress) {
        progress.style.width = '100%';
    }
    
    // Actualizar currentStep
    currentStep = step;
}

// Paso anterior - MEJORADO
function previousStep() {
    var wizard = PF('stepperWizard');
    if (!wizard) return;
    
    // Guardar el paso actual antes de cambiar
    var oldStep = currentStep;
    
    // Llamar al método back del wizard
    wizard.back();
    
    // Actualizar stepper inmediatamente (optimista)
    var newStep = Math.max(oldStep - 1, 1);
    updateStepper(newStep);
    
    // Sincronizar después de que el wizard actualice (confirmación)
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 100);
    
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 300);
    
    setTimeout(function() {
        var detectedStep = detectCurrentStep();
        updateStepper(detectedStep);
    }, 600);
}

// Sincronizar stepper con el estado actual del wizard
function syncStepperWithWizard() {
    var detectedStep = detectCurrentStep();
    if (detectedStep !== currentStep) {
        updateStepper(detectedStep);
    }
}

// Actualizar visualización del stepper
function updateStepper(activeStep) {
    if (!activeStep || activeStep < 1 || activeStep > totalSteps) {
        activeStep = 1;
    }
    
    // Validar que activeStep esté en rango válido (1-4)
    if (activeStep < 1) activeStep = 1;
    if (activeStep > totalSteps) activeStep = totalSteps;
    
    currentStep = activeStep;
    
    var steps = document.querySelectorAll('.custom-stepper-step');
    var progress = document.getElementById('stepperProgress');
    
    if (!steps || steps.length === 0) {
        return;
    }
    
    // Actualizar clases de los pasos
    steps.forEach(function(step, index) {
        var stepNum = index + 1;
        step.classList.remove('active', 'completed');
        
        if (stepNum < activeStep) {
            step.classList.add('completed');
        } else if (stepNum === activeStep) {
            step.classList.add('active');
        }
    });
    
    // Actualizar línea de progreso
    if (progress) {
        // Si es el paso 4, la línea debe llegar al 100%
        var percentage = activeStep === 1 ? 0 : ((activeStep - 1) / (totalSteps - 1)) * 100;
        // Asegurar que el paso 4 muestre 100%
        if (activeStep === totalSteps) {
            percentage = 100;
        }
        progress.style.width = percentage + '%';
    }
    
    // Actualizar texto del botón "Siguiente"
    updateNextButtonText(activeStep);
}

// Actualizar texto del botón "Siguiente" a "Finalizar" en el último paso
function updateNextButtonText(step) {
    var btnSiguiente = document.getElementById('formRegistroPostulanteStepper:btnSiguiente');
    if (!btnSiguiente) {
        // Intentar buscar por clase si no se encuentra por ID
        var buttons = document.querySelectorAll('.stepper-button-next');
        if (buttons.length > 0) {
            btnSiguiente = buttons[0];
        }
    }
    
    if (btnSiguiente) {
        if (step === totalSteps) {
            // Último paso: cambiar a "Finalizar"
            btnSiguiente.value = 'Finalizar';
            if (btnSiguiente.textContent !== undefined) {
                btnSiguiente.textContent = 'Finalizar';
            }
            // También actualizar el span interno si existe
            var span = btnSiguiente.querySelector('span');
            if (span) {
                span.textContent = 'Finalizar';
            }
        } else {
            // No es el último paso: mantener "Siguiente"
            btnSiguiente.value = 'Siguiente';
            if (btnSiguiente.textContent !== undefined) {
                btnSiguiente.textContent = 'Siguiente';
            }
            // También actualizar el span interno si existe
            var span = btnSiguiente.querySelector('span');
            if (span) {
                span.textContent = 'Siguiente';
            }
        }
    }
}

// Interceptar eventos del wizard de PrimeFaces
function setupWizardListeners() {
    var wizard = PF('stepperWizard');
    if (!wizard) {
        setTimeout(setupWizardListeners, 200);
        return;
    }
    
    // Interceptar next() y back() del wizard
    if (wizard.next) {
        var originalNext = wizard.next.bind(wizard);
        wizard.next = function() {
            originalNext();
            // Sincronizar después de múltiples delays
            setTimeout(syncStepperWithWizard, 50);
            setTimeout(syncStepperWithWizard, 200);
            setTimeout(syncStepperWithWizard, 500);
        };
    }
    
    if (wizard.back) {
        var originalBack = wizard.back.bind(wizard);
        wizard.back = function() {
            originalBack();
            // Sincronizar después de múltiples delays
            setTimeout(syncStepperWithWizard, 50);
            setTimeout(syncStepperWithWizard, 200);
            setTimeout(syncStepperWithWizard, 500);
        };
    }
}

// Inicializar cuando el DOM esté listo
jQuery(document).ready(function() {
    // Inicializar con el paso del bean
    updateStepper(currentStep);
    
    // Configurar listeners del wizard
    setupWizardListeners();
    
    // Sincronizar después de que PrimeFaces cargue todo
    setTimeout(function() {
        syncStepperWithWizard();
    }, 500);
    
    setTimeout(function() {
        syncStepperWithWizard();
    }, 1000);
    
    // Sincronización periódica más frecuente
    setInterval(function() {
        syncStepperWithWizard();
    }, 300);
});

// Sincronizar después de actualizaciones AJAX de PrimeFaces
if (window.PrimeFaces && window.PrimeFaces.ajax) {
    if (typeof PrimeFaces.ajax.addOnUpdateCallback === 'function') {
        PrimeFaces.ajax.addOnUpdateCallback(function() {
            setTimeout(syncStepperWithWizard, 300);
        });
    }
}

// MutationObserver mejorado para detectar cambios en el DOM
if (window.MutationObserver) {
    var observer = new MutationObserver(function(mutations) {
        var shouldSync = false;
        mutations.forEach(function(mutation) {
            if (mutation.type === 'attributes' && mutation.attributeName === 'style') {
                var target = mutation.target;
                if (target.classList.contains('ui-tabview-panel')) {
                    shouldSync = true;
                }
            }
            if (mutation.type === 'childList') {
                var target = mutation.target;
                if (target.classList.contains('ui-wizard-content')) {
                    shouldSync = true;
                }
            }
        });
        
        if (shouldSync) {
            setTimeout(syncStepperWithWizard, 100);
        }
    });
    
    jQuery(document).ready(function() {
        setTimeout(function() {
            var wizardContent = document.querySelector('.custom-wizard-stepper .ui-wizard-content');
            if (wizardContent) {
                observer.observe(wizardContent, {
                    attributes: true,
                    attributeFilter: ['style', 'class'],
                    childList: true,
                    subtree: true
                });
            }
        }, 1000);
    });
}

// Función global para que el flowListener pueda llamarla
window.updateStepperFromBean = function(step) {
    if (step >= 1 && step <= totalSteps) {
        updateStepper(step);
    }
};

