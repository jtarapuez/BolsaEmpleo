# 🔧 COMANDOS GIT - GUÍA DE USO

**Repositorio:** https://github.com/jtarapuez/BolsaEmpleo.git  
**Última actualización:** 29 de Diciembre de 2025

Esta guía contiene todos los comandos necesarios para trabajar con Git en el proyecto BolsaEmpleo.

---

## 📋 COMANDOS BÁSICOS

### Ver Estado del Repositorio

```bash
# Ver estado de archivos modificados, agregados, etc.
git status

# Ver estado en formato corto
git status -s

# Ver diferencias en archivos modificados (sin agregar al staging)
git diff

# Ver diferencias de archivos ya agregados al staging
git diff --staged
```

---

## 📤 SUBIR CAMBIOS MANUALMENTE (PASO A PASO)

### Método 1: Proceso Completo (Recomendado)

```bash
# 1. Ir al directorio del proyecto
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo

# 2. Ver qué archivos han cambiado
git status

# 3. Ver los cambios específicos (opcional)
git diff

# 4. Agregar archivos específicos al staging
git add nombre-archivo.md
git add docs/
git add README.md

# O agregar todos los archivos modificados
git add .

# 5. Verificar qué se va a commitear
git status

# 6. Hacer commit con mensaje descriptivo
git commit -m "Descripción clara de los cambios realizados"

# 7. Subir cambios a GitHub
git push

# O si es la primera vez en esta rama
git push -u origin main
```

### Método 2: Comando Rápido (Todo en uno)

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo
git add .
git commit -m "Descripción de los cambios"
git push
```

---

## 📝 EJEMPLOS DE USO COMUNES

### Subir Cambios en Documentación

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo

# Agregar solo archivos de documentación
git add docs/
git add README.md

# Commit con mensaje descriptivo
git commit -m "Actualizar documentación: agregar nuevas guías y correcciones"

# Subir
git push
```

### Subir Cambios en el Código del Proyecto

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo

# Agregar cambios en el proyecto base
git add iess-gestion-proyecto-base-master/

# Commit
git commit -m "Agregar nueva funcionalidad: [descripción]"

# Subir
git push
```

### Subir Cambios en Scripts

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo

# Agregar scripts modificados
git add .iniciar_jboss.sh
git add build_and_deploy.sh

# Commit
git commit -m "Mejorar scripts de despliegue: agregar validaciones"

# Subir
git push
```

---

## 🔍 COMANDOS DE CONSULTA

### Ver Historial de Commits

```bash
# Ver últimos commits (formato corto)
git log --oneline

# Ver últimos 10 commits con detalles
git log -10

# Ver commits con gráfico de ramas
git log --oneline --graph --all

# Ver cambios de un commit específico
git show <hash-del-commit>
```

### Ver Cambios Específicos

```bash
# Ver diferencias entre working directory y último commit
git diff

# Ver diferencias de un archivo específico
git diff README.md

# Ver diferencias entre dos commits
git diff <commit1> <commit2>

# Ver qué archivos cambiaron en un commit
git show --name-only <hash-del-commit>
```

### Ver Estado de Archivos

```bash
# Ver archivos modificados
git status

# Ver archivos ignorados
git status --ignored

# Ver archivos sin seguimiento
git ls-files --others --exclude-standard
```

---

## 🔄 COMANDOS DE SINCRONIZACIÓN

### Obtener Cambios del Repositorio Remoto

```bash
# Descargar cambios sin fusionar
git fetch origin

# Descargar y fusionar cambios
git pull

# Descargar y fusionar de una rama específica
git pull origin main
```

### Subir Cambios al Repositorio Remoto

```bash
# Subir cambios a la rama actual
git push

# Subir a una rama específica
git push origin main

# Subir y configurar upstream (primera vez)
git push -u origin main

# Forzar subida (¡CUIDADO! Solo si es necesario)
git push --force
```

---

## 🗂️ GESTIÓN DE ARCHIVOS

### Agregar Archivos al Staging

```bash
# Agregar un archivo específico
git add nombre-archivo.md

# Agregar todos los archivos modificados
git add .

# Agregar todos los archivos de un directorio
git add docs/

# Agregar archivos con patrón
git add *.md
git add docs/**/*.md

# Agregar archivos interactivamente
git add -i
```

### Quitar Archivos del Staging

```bash
# Quitar un archivo del staging (sin eliminar cambios)
git reset nombre-archivo.md

# Quitar todos los archivos del staging
git reset

# Quitar archivo del staging y eliminar cambios (¡CUIDADO!)
git checkout -- nombre-archivo.md
```

### Deshacer Cambios

```bash
# Deshacer cambios en un archivo (antes de agregar al staging)
git checkout -- nombre-archivo.md

# Deshacer cambios en todos los archivos
git checkout -- .

# Deshacer último commit (manteniendo cambios)
git reset --soft HEAD~1

# Deshacer último commit (eliminando cambios)
git reset --hard HEAD~1
```

---

## 📦 GESTIÓN DE COMMITS

### Crear Commit

```bash
# Commit con mensaje corto
git commit -m "Mensaje del commit"

# Commit con mensaje largo (abre editor)
git commit

# Commit agregando todos los archivos modificados
git commit -am "Mensaje del commit"

# Modificar último commit (agregar más cambios)
git commit --amend

# Modificar mensaje del último commit
git commit --amend -m "Nuevo mensaje"
```

### Ver Commits

```bash
# Ver último commit
git show

# Ver commit específico
git show <hash>

# Ver commits de un archivo
git log -- nombre-archivo.md

# Ver commits con estadísticas
git log --stat

# Ver commits con cambios
git log -p
```

---

## 🌿 GESTIÓN DE RAMAS

### Ver Ramas

```bash
# Ver ramas locales
git branch

# Ver ramas remotas
git branch -r

# Ver todas las ramas
git branch -a

# Ver rama actual
git branch --show-current
```

### Crear y Cambiar de Rama

```bash
# Crear nueva rama
git branch nombre-rama

# Cambiar a una rama
git checkout nombre-rama

# Crear y cambiar a nueva rama
git checkout -b nombre-rama

# Cambiar a rama main
git checkout main
```

### Fusionar Ramas

```bash
# Cambiar a rama destino (ej: main)
git checkout main

# Fusionar otra rama
git merge nombre-rama

# Fusionar y crear commit de merge
git merge --no-ff nombre-rama
```

---

## 🔐 CONFIGURACIÓN

### Ver Configuración

```bash
# Ver configuración global
git config --global --list

# Ver configuración local
git config --local --list

# Ver configuración de usuario
git config user.name
git config user.email
```

### Configurar Usuario

```bash
# Configurar usuario global
git config --global user.name "jtarapuez"
git config --global user.email "tarapuez9@gmail.com"

# Configurar usuario solo para este repositorio
git config user.name "jtarapuez"
git config user.email "tarapuez9@gmail.com"
```

### Configurar Remote

```bash
# Ver remotes configurados
git remote -v

# Agregar remote
git remote add origin https://github.com/jtarapuez/BolsaEmpleo.git

# Cambiar URL del remote
git remote set-url origin https://github.com/jtarapuez/BolsaEmpleo.git

# Eliminar remote
git remote remove origin
```

---

## 🚨 SOLUCIÓN DE PROBLEMAS

### Si hay conflictos al hacer pull

```bash
# Ver archivos en conflicto
git status

# Resolver conflictos manualmente en el editor
# Luego agregar archivos resueltos
git add archivo-resuelto.md

# Completar merge
git commit
```

### Si quieres descartar cambios locales

```bash
# Descartar cambios en un archivo
git checkout -- nombre-archivo.md

# Descartar todos los cambios locales
git checkout -- .

# Descartar cambios y volver al último commit
git reset --hard HEAD
```

### Si quieres actualizar desde remoto

```bash
# Obtener últimos cambios
git fetch origin

# Ver diferencias
git diff main origin/main

# Fusionar cambios
git pull origin main
```

### Si el push fue rechazado

```bash
# Primero obtener cambios remotos
git pull origin main

# Resolver conflictos si los hay
# Luego volver a intentar
git push
```

---

## 📋 FLUJO DE TRABAJO RECOMENDADO

### Flujo Diario de Trabajo

```bash
# 1. Al empezar el día: obtener últimos cambios
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo
git pull

# 2. Trabajar en el proyecto (hacer cambios)

# 3. Ver qué cambió
git status
git diff

# 4. Agregar cambios
git add .

# 5. Hacer commit
git commit -m "Descripción clara de los cambios"

# 6. Subir cambios
git push
```

### Flujo para Nueva Funcionalidad

```bash
# 1. Crear nueva rama
git checkout -b feature/nueva-funcionalidad

# 2. Trabajar en la funcionalidad
# ... hacer cambios ...

# 3. Commit de cambios
git add .
git commit -m "Agregar nueva funcionalidad: [descripción]"

# 4. Subir rama al remoto
git push -u origin feature/nueva-funcionalidad

# 5. Cuando esté lista, fusionar a main
git checkout main
git pull
git merge feature/nueva-funcionalidad
git push
```

---

## 💡 CONSEJOS Y MEJORES PRÁCTICAS

### Mensajes de Commit

**✅ BUENOS mensajes:**
```bash
git commit -m "Agregar documentación de URLs del proyecto"
git commit -m "Corregir error de memoria en JBoss: aumentar MetaspaceSize"
git commit -m "Actualizar README con información de despliegue"
```

**❌ MALOS mensajes:**
```bash
git commit -m "cambios"
git commit -m "fix"
git commit -m "update"
```

### Frecuencia de Commits

- ✅ Hacer commits frecuentes y pequeños
- ✅ Un commit por funcionalidad o corrección
- ✅ No acumular muchos cambios en un solo commit

### Antes de Hacer Push

1. ✅ Verificar que todo compila: `mvn validate`
2. ✅ Revisar cambios: `git diff`
3. ✅ Verificar estado: `git status`
4. ✅ Hacer pull primero: `git pull`
5. ✅ Luego hacer push: `git push`

---

## 🔗 COMANDOS RÁPIDOS DE REFERENCIA

```bash
# Ver estado
git status

# Agregar todo
git add .

# Commit
git commit -m "Mensaje"

# Subir
git push

# Bajar cambios
git pull

# Ver historial
git log --oneline -10

# Ver cambios
git diff
```

---

## 📚 RECURSOS ADICIONALES

- **Documentación oficial Git:** https://git-scm.com/doc
- **GitHub Guides:** https://guides.github.com/
- **Repositorio del proyecto:** https://github.com/jtarapuez/BolsaEmpleo

---

## ⚠️ NOTAS IMPORTANTES

1. **Nunca hacer `git push --force`** a menos que sea absolutamente necesario
2. **Siempre hacer `git pull`** antes de `git push` si trabajas en equipo
3. **Revisar cambios** con `git diff` antes de hacer commit
4. **Usar mensajes descriptivos** en los commits
5. **No subir archivos sensibles** (contraseñas, tokens, etc.)

---

**Última actualización:** 29 de Diciembre de 2025

