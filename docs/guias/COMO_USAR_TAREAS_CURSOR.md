# 🎯 Cómo Generar EAR desde Cursor (Forma Gráfica)

## ✅ Tareas Configuradas

He creado tareas en Cursor que te permiten ejecutar comandos desde el menú, de forma más "gráfica".

## 📋 Cómo Usar las Tareas

### Método 1: Paleta de Comandos (Recomendado)

1. **Presiona:** `Cmd + Shift + P` (Mac) o `Ctrl + Shift + P` (Windows/Linux)
2. **Escribe:** `Tasks: Run Task`
3. **Selecciona** la tarea que quieres ejecutar:
   - 🔨 **Maven: Clean** - Limpia compilaciones anteriores
   - 🔨 **Maven: Compile (sin tests)** - Compila y genera EAR (RECOMENDADO)
   - 🔨 **Maven: Compile (con tests)** - Compila con tests
   - 📦 **Generar EAR** - Limpia, compila y genera EAR
   - 🚀 **Build & Deploy (Script Completo)** - Todo el proceso automático
   - 📋 **Copiar EAR a JBoss** - Copia el EAR generado a deployments
   - 🛑 **Detener JBoss** - Detiene el servidor
   - ▶️ **Iniciar JBoss** - Inicia el servidor

### Método 2: Menú Terminal

1. Ve a **Terminal** → **Run Task...**
2. Selecciona la tarea que quieres ejecutar

### Método 3: Atajo de Teclado

1. **Presiona:** `Cmd + Shift + B` (Mac) o `Ctrl + Shift + B` (Windows/Linux)
2. Esto ejecutará la tarea por defecto: **Maven: Compile (sin tests)**

## 🚀 Flujo Recomendado para Generar EAR

### Opción A: Todo Automático (Más Fácil)
```
Cmd + Shift + P → Tasks: Run Task → 🚀 Build & Deploy (Script Completo)
```
Esto hace todo: compila, genera EAR, detiene JBoss, copia EAR, inicia JBoss.

### Opción B: Paso a Paso (Más Control)
1. **Generar EAR:**
   ```
   Cmd + Shift + P → Tasks: Run Task → 📦 Generar EAR
   ```

2. **Copiar a JBoss:**
   ```
   Cmd + Shift + P → Tasks: Run Task → 📋 Copiar EAR a JBoss
   ```

3. **Reiniciar JBoss:**
   ```
   Cmd + Shift + P → Tasks: Run Task → 🛑 Detener JBoss
   Cmd + Shift + P → Tasks: Run Task → ▶️ Iniciar JBoss
   ```

## 📝 Notas Importantes

- Las tareas están configuradas con las rutas correctas de Java 8 y Maven
- El EAR se genera en: `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/target/`
- El EAR se copia a: `EAP-7.2.0/standalone/deployments/`
- Los logs aparecen en el panel de Terminal de Cursor

## 🔍 Ver Resultados

Después de ejecutar una tarea:
- Los resultados aparecen en el panel **Terminal** de Cursor
- Puedes ver el progreso en tiempo real
- Si hay errores, se mostrarán en el panel

## ⚡ Atajo Rápido

Para compilar rápidamente:
```
Cmd + Shift + B  (o Ctrl + Shift + B)
```
Esto ejecuta la compilación por defecto y genera el EAR.

---

**💡 Tip:** Guarda este documento para referencia rápida.
