# 🚀 Resumen Rápido: Hacer Cambios y Desplegar

## 📝 Flujo Completo para Hacer Cambios

### 1️⃣ Hacer Cambios en el Código
- Edita cualquier archivo del proyecto (XHTML, Java, propiedades, etc.)
- Guarda los cambios

### 2️⃣ Compilar y Generar EAR

**Opción A: Todo Automático (Recomendado)**
```
Cmd + Shift + P → Tasks: Run Task → 🚀 Build & Deploy (Script Completo)
```
✅ Esto hace TODO: compila, genera EAR, detiene JBoss, copia EAR, inicia JBoss

**Opción B: Paso a Paso**
```
1. Cmd + Shift + P → Tasks: Run Task → 📦 Generar EAR
2. Cmd + Shift + P → Tasks: Run Task → 📋 Copiar EAR a JBoss
3. (Si JBoss está corriendo, detectará el cambio automáticamente)
```

### 3️⃣ Verificar Cambios
- Abre: http://localhost:8081/iess-gestion-proyecto-base-web/public/pagina.jsf
- O la URL de tu aplicación

---

## ⚡ Atajo Rápido

**Para compilar rápidamente:**
```
Cmd + Shift + B
```
Esto ejecuta la compilación por defecto y genera el EAR.

---

## 📋 Comandos Disponibles (Tareas)

| Tarea | Descripción |
|-------|-------------|
| 🔨 **Maven: Clean** | Limpia compilaciones anteriores |
| 🔨 **Maven: Compile (sin tests)** | Compila y genera EAR (por defecto) |
| 📦 **Generar EAR** | Limpia, compila y genera EAR |
| 🚀 **Build & Deploy (Script Completo)** | Todo el proceso automático |
| 📋 **Copiar EAR a JBoss** | Copia el EAR generado a deployments |
| 🛑 **Detener JBoss** | Detiene el servidor |
| ▶️ **Iniciar JBoss** | Inicia el servidor |

---

## 💡 Tips

1. **Si JBoss está corriendo**: Solo necesitas copiar el EAR, JBoss lo detectará y redesplegará automáticamente
2. **Si hay errores**: Revisa los logs en `EAP-7.2.0/standalone/log/server.log`
3. **Para ver cambios rápidos**: Usa `Cmd + Shift + B` para compilar rápido

---

## 📁 Archivos Modificados en esta Prueba

- `iess-gestion-proyecto-base-web/src/main/webapp/public/pagina.xhtml`
  - Título cambiado a "Online Service - IESS"
  - Banner "ONLINE SERVICE" agregado
  - Títulos de sección actualizados

---

**¡Listo para trabajar!** 🎉



