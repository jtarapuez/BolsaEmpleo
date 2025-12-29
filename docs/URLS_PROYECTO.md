# 🌐 URLs DEL PROYECTO - REFERENCIA RÁPIDA

**Última actualización:** 29 de Diciembre de 2025

Este documento contiene todas las URLs importantes del proyecto para referencia rápida.

---

## 🚀 URLs DE LA APLICACIÓN

### URL Principal de la Aplicación
```
http://localhost:8081/iess-gestion-proyecto-base-web/
```

### Páginas Disponibles

#### Página Principal
```
http://localhost:8081/iess-gestion-proyecto-base-web/pages/principal.jsf
```

#### Páginas de Arquitectura
```
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/cifrado.jsf
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/codigoOTP.jsf
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/transversal.jsf
```

#### Páginas de Alfresco (Gestión Documental)
```
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/alfresco/alfrescoUploadSingle.jsf
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/alfresco/alfrescoUploadMultiple.jsf
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/alfresco/alfrescoDownloadSingleFile.jsf
http://localhost:8081/iess-gestion-proyecto-base-web/pages/arquitectura/alfresco/alfrescoDownloadMultipleFile.jsf
```

#### Página Pública
```
http://localhost:8081/iess-gestion-proyecto-base-web/public/pagina.jsf
```

#### Páginas de Bolsa de Empleo
```
http://localhost:8081/iess-gestion-proyecto-base-web/pages/bolsaEmpleo/registroPostulante.jsf
```

---

## 🖥️ URLs DEL SERVIDOR JBOSS

### Consola de Administración
```
http://localhost:9991
```

### Consola de Administración HTTPS (si está configurado)
```
https://localhost:9993
```

### Puerto HTTP
```
http://localhost:8081
```

### Puerto HTTPS (si está configurado)
```
https://localhost:8443
```

---

## 📦 URLs DE REPOSITORIOS MAVEN

### Repositorio Interno IESS (Releases)
```
http://192.168.29.6:8080/repository/internal/
```

### Repositorio Snapshots IESS
```
http://192.168.29.6:8080/repository/snapshots/
```

### Maven Central (configurado automáticamente)
```
https://repo1.maven.org/maven2/
```

---

## 📁 UBICACIÓN DE CONFIGURACIÓN DE URLs

### Context Root de la Aplicación

**Archivo:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/target/application.xml`

**Línea 13:**
```xml
<context-root>/iess-gestion-proyecto-base-web</context-root>
```

**Nota:** Este archivo se genera automáticamente durante la compilación. El contexto se define en el módulo EAR.

### Puertos del Servidor JBoss

**Archivo:** `EAP-7.2.0/standalone/configuration/standalone.xml`

**Puertos configurados:**
- **HTTP:** 8081 (puerto por defecto modificado)
- **HTTPS:** 8443
- **Management HTTP:** 9991
- **Management HTTPS:** 9993

**Ubicación en el archivo:** Buscar `<socket-binding-group>`

---

## 🔍 CÓMO ENCONTRAR LAS URLs

### 1. Context Root de la Aplicación

**Método 1: Verificar en logs de JBoss**
```bash
grep "WFLYUT0021.*iess-gestion-proyecto-base" EAP-7.2.0/standalone/log/server.log
```

**Método 2: Verificar en application.xml**
```bash
grep "context-root" iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/target/application.xml
```

**Método 3: Verificar en el EAR desplegado**
```bash
unzip -p EAP-7.2.0/standalone/deployments/iess-gestion-proyecto-base-ear-3.0.5.ear META-INF/application.xml | grep context-root
```

### 2. Puertos del Servidor

**Método 1: Verificar en standalone.xml**
```bash
grep -A 10 "socket-binding-group" EAP-7.2.0/standalone/configuration/standalone.xml | grep -E "port|http|https|management"
```

**Método 2: Verificar en logs de inicio**
```bash
grep "WFLYSRV0025\|listening" EAP-7.2.0/standalone/log/server.log | tail -5
```

**Método 3: Verificar procesos en ejecución**
```bash
lsof -i :8081 -i :9991 -i :8443
```

### 3. URLs de Repositorios Maven

**Archivo:** `~/.m2/settings.xml`

**O en los POMs:**
```bash
grep -r "192.168.29.6:8080" iess-gestion-proyecto-base-master/*/pom.xml
```

---

## 📝 RESUMEN RÁPIDO

| Tipo | URL | Puerto | Archivo de Configuración |
|------|-----|--------|---------------------------|
| **Aplicación Principal** | http://localhost:8081/iess-gestion-proyecto-base-web/ | 8081 | `application.xml` |
| **Consola Admin** | http://localhost:9991 | 9991 | `standalone.xml` |
| **Repositorio IESS** | http://192.168.29.6:8080/repository/internal/ | 8080 | `settings.xml` / `pom.xml` |

---

## 🔧 CAMBIAR LAS URLs

### Cambiar Context Root

1. Editar el módulo EAR (si existe `application.xml` en `src`)
2. O modificar el `pom.xml` del módulo EAR con el plugin `maven-ear-plugin`
3. Recompilar el proyecto

### Cambiar Puertos del Servidor

1. Editar `EAP-7.2.0/standalone/configuration/standalone.xml`
2. Buscar la sección `<socket-binding-group>`
3. Modificar los valores de `<socket-binding name="http">` y otros
4. Reiniciar JBoss

**Ejemplo:**
```xml
<socket-binding-group name="standard-sockets" default-interface="public" port-offset="${jboss.socket.binding.port-offset:0}">
    <socket-binding name="http" port="${jboss.http.port:8081}"/>
    <socket-binding name="https" port="${jboss.https.port:8443}"/>
    <socket-binding name="management-http" interface="management" port="${jboss.management.http.port:9991}"/>
    <socket-binding name="management-https" interface="management" port="${jboss.management.https.port:9993}"/>
</socket-binding-group>
```

---

## ✅ VERIFICACIÓN RÁPIDA

### Verificar que la aplicación está accesible
```bash
curl -I http://localhost:8081/iess-gestion-proyecto-base-web/
```

**Resultado esperado:** `HTTP/1.1 200 OK`

### Verificar que JBoss está corriendo
```bash
curl -I http://localhost:9991
```

**Resultado esperado:** `HTTP/1.1 200 OK` o `HTTP/1.1 401 Unauthorized` (normal, requiere autenticación)

### Verificar repositorio Maven
```bash
curl -I http://192.168.29.6:8080/repository/internal/
```

**Resultado esperado:** `HTTP/1.1 200 OK`

---

## 📚 DOCUMENTACIÓN RELACIONADA

- **[README.md](../../README.md)** - Documentación principal (sección "URLs de Acceso")
- **[COMANDOS_DESPLIEGUE.md](./comandos/COMANDOS_DESPLIEGUE.md)** - Comandos de despliegue
- **[DIAGNOSTICO_PROYECTO.md](./diagnosticos/DIAGNOSTICO_PROYECTO.md)** - Diagnóstico del proyecto

---

**💡 TIP:** Guarda este archivo en favoritos o agrega un bookmark para acceso rápido a las URLs del proyecto.

**Última actualización:** 29 de Diciembre de 2025


