# 📚 Documentación - Stepper Personalizado

## Cómo Agregar Más Pasos al Stepper

Esta documentación explica cómo modificar el stepper para agregar más pasos (actualmente tiene 4 pasos).

---

## 📋 Pasos para Agregar un Nuevo Paso

### 1. **Actualizar el Bean Java** (`RegistroPostulanteBean.java`)

```java
// Cambiar el total de pasos
private static final int TOTAL_PASOS = 5; // Cambiar de 4 a 5 (o el número que necesites)

// Agregar el nuevo paso en el método onFlowProcess()
public String onFlowProcess(FlowEvent event) {
    String newStep = event.getNewStep();
    if ("infoBasica".equals(newStep)) {
        pasoActual = 1;
    } else if ("formacionAcademica".equals(newStep)) {
        pasoActual = 2;
    } else if ("experienciaLaboral".equals(newStep)) {
        pasoActual = 3;
    } else if ("cursosConocimientos".equals(newStep)) {
        pasoActual = 4;
    } else if ("nuevoPaso".equals(newStep)) { // ← AGREGAR ESTA LÍNEA
        pasoActual = 5; // ← AGREGAR ESTA LÍNEA
    }
    return newStep;
}
```

### 2. **Actualizar el JavaScript en `registroPostulanteStepper.xhtml`**

#### 2.1. Cambiar el total de pasos:
```javascript
var totalSteps = 5; // Cambiar de 4 a 5 (o el número que necesites)
```

#### 2.2. Agregar el nuevo paso al mapeo `stepToTab`:
```javascript
var stepToTab = {
    1: 'infoBasica',
    2: 'formacionAcademica',
    3: 'experienciaLaboral',
    4: 'cursosConocimientos',
    5: 'nuevoPaso' // ← AGREGAR ESTA LÍNEA
};
```

#### 2.3. Agregar el nuevo paso al mapeo inverso `tabToStep`:
```javascript
var tabToStep = {
    'infoBasica': 1,
    'formacionAcademica': 2,
    'experienciaLaboral': 3,
    'cursosConocimientos': 4,
    'nuevoPaso': 5 // ← AGREGAR ESTA LÍNEA
};
```

### 3. **Agregar el HTML del Nuevo Paso en el Stepper Visual**

En la sección del stepper visual (dentro de `<div class="custom-stepper-container">`):

```xhtml
<!-- Agregar después del paso 4 -->
<div class="custom-stepper-step" data-step="5" onclick="goToStep(5)">
    <div class="custom-stepper-circle">
        <span class="custom-stepper-number">5</span>
    </div>
    <div class="custom-stepper-label">Nombre del Nuevo Paso</div>
</div>
```

### 4. **Agregar el Tab del Wizard en PrimeFaces**

Dentro del componente `<p:wizard>`, agregar el nuevo tab:

```xhtml
<!-- PASO 5: Nuevo Paso -->
<p:tab id="nuevoPaso" title="Nuevo Paso">
    <div class="ui-g ui-fluid" style="margin-top: 20px;">
        <div class="ui-g-12">
            <h3>Paso 5: Nuevo Paso</h3>
            <p>Descripción del nuevo paso.</p>
            <!-- Aquí va el contenido del formulario -->
        </div>
    </div>
</p:tab>
```

### 5. **Actualizar el Método `getPorcentajeProgreso()` en el Bean (Opcional)**

Si quieres ajustar el porcentaje de progreso:

```java
public int getPorcentajeProgreso() {
    if (pasoActual == 1) {
        return 0;
    } else if (pasoActual == 2) {
        return 20; // Ajustar según el número de pasos
    } else if (pasoActual == 3) {
        return 40;
    } else if (pasoActual == 4) {
        return 60;
    } else if (pasoActual == 5) {
        return 80;
    } else {
        return 100;
    }
}
```

**Fórmula para calcular porcentajes:**
- Paso 1: 0%
- Paso 2: (1 / (totalPasos - 1)) * 100 = 25% (para 5 pasos)
- Paso 3: (2 / (totalPasos - 1)) * 100 = 50%
- Paso 4: (3 / (totalPasos - 1)) * 100 = 75%
- Paso 5: 100%

---

## 📝 Ejemplo Completo: Agregar un Paso 5

### Archivo: `registroPostulanteStepper.xhtml`

#### 1. HTML del Stepper Visual:
```xhtml
<div class="custom-stepper-container" id="customStepper">
    <div class="custom-stepper-progress" id="stepperProgress"></div>
    
    <!-- Pasos existentes 1-4 -->
    <!-- ... -->
    
    <!-- NUEVO PASO 5 -->
    <div class="custom-stepper-step" data-step="5" onclick="goToStep(5)">
        <div class="custom-stepper-circle">
            <span class="custom-stepper-number">5</span>
        </div>
        <div class="custom-stepper-label">Verificación Final</div>
    </div>
</div>
```

#### 2. JavaScript:
```javascript
var totalSteps = 5; // ← CAMBIAR

var stepToTab = {
    1: 'infoBasica',
    2: 'formacionAcademica',
    3: 'experienciaLaboral',
    4: 'cursosConocimientos',
    5: 'verificacionFinal' // ← AGREGAR
};

var tabToStep = {
    'infoBasica': 1,
    'formacionAcademica': 2,
    'experienciaLaboral': 3,
    'cursosConocimientos': 4,
    'verificacionFinal': 5 // ← AGREGAR
};
```

#### 3. Tab del Wizard:
```xhtml
<p:wizard showNavBar="false" ...>
    <!-- Tabs existentes 1-4 -->
    <!-- ... -->
    
    <!-- NUEVO TAB 5 -->
    <p:tab id="verificacionFinal" title="Verificación Final">
        <div class="ui-g ui-fluid" style="margin-top: 20px;">
            <div class="ui-g-12">
                <h3>Paso 5: Verificación Final</h3>
                <p>Revise toda la información antes de finalizar.</p>
                <!-- Contenido del formulario -->
            </div>
        </div>
    </p:tab>
</p:wizard>
```

---

## ⚠️ Puntos Importantes

1. **Consistencia de IDs**: El `id` del tab debe coincidir con el valor en `stepToTab` y `tabToStep`.

2. **Orden de los Pasos**: Los pasos deben estar en orden secuencial (1, 2, 3, 4, 5...).

3. **Botón "Finalizar"**: El botón cambiará automáticamente a "Finalizar" cuando estés en el último paso.

4. **Línea de Progreso**: Se calcula automáticamente basándose en `totalSteps`.

5. **Responsive**: Los estilos responsive se aplican automáticamente a todos los pasos.

---

## 🔧 Personalización Adicional

### Cambiar Colores de los Pasos

En el CSS, puedes modificar:

```css
/* Paso inactivo */
.custom-stepper-circle {
    background-color: #54c2e9; /* Azul claro */
}

/* Paso activo */
.custom-stepper-step.active .custom-stepper-circle {
    background-color: #3081c7; /* Azul oscuro */
}

/* Paso completado */
.custom-stepper-step.completed .custom-stepper-circle {
    background-color: #4caf50; /* Verde */
}
```

### Cambiar Tamaños

```css
/* Círculos */
.custom-stepper-circle {
    width: 50px;  /* Cambiar tamaño */
    height: 50px;
    font-size: 18px; /* Tamaño del número */
}

/* Textos */
.custom-stepper-label {
    font-size: 11px; /* Tamaño del texto */
    width: 140px;    /* Ancho del texto */
}
```

---

## 📱 Responsive

Los estilos responsive ya están configurados para:
- **Tablets** (max-width: 768px)
- **Móviles** (max-width: 480px)

Se aplican automáticamente a todos los pasos, sin necesidad de cambios adicionales.

---

## ✅ Checklist para Agregar un Nuevo Paso

- [ ] Actualizar `TOTAL_PASOS` en el Bean
- [ ] Agregar el nuevo paso en `onFlowProcess()` del Bean
- [ ] Cambiar `totalSteps` en JavaScript
- [ ] Agregar entrada en `stepToTab`
- [ ] Agregar entrada en `tabToStep`
- [ ] Agregar el HTML del paso en el stepper visual
- [ ] Agregar el tab del wizard en PrimeFaces
- [ ] Probar la navegación entre pasos
- [ ] Verificar que el botón "Finalizar" aparezca en el último paso
- [ ] Probar en dispositivos móviles

---

## 🎯 Resumen Rápido

Para agregar un paso 5:

1. **Bean**: `TOTAL_PASOS = 5` + agregar en `onFlowProcess()`
2. **JavaScript**: `totalSteps = 5` + agregar en `stepToTab` y `tabToStep`
3. **HTML Stepper**: Agregar `<div class="custom-stepper-step" data-step="5">`
4. **HTML Wizard**: Agregar `<p:tab id="nuevoPaso">`

¡Listo! El stepper funcionará automáticamente con el nuevo paso.

---

**Última actualización**: 29 de diciembre de 2025
**Versión del stepper**: 1.0

