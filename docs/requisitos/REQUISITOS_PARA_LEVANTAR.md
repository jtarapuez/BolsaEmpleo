# 📋 REQUISITOS PARA LEVANTAR EL PROYECTO IESS

**Fecha:** 23 de Diciembre de 2025  
**Proyecto:** iess-gestion-proyecto-base (Versión 3.0.5)

---

## ✅ LO QUE YA TENEMOS

1. **Java 8** ✅
   - Versión: 1.8.0_202
   - Configurado en `.zshrc`
   - **Nota:** Actualmente está usando Java 21, necesita forzar Java 8

2. **Maven 3.9.11** ✅
   - Instalado y configurado
   - Ubicación: `/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11`

3. **Proyecto compilado** ✅
   - BUILD SUCCESS
   - Todos los módulos compilados correctamente

4. **Dependencias IESS** ✅
   - Todas las dependencias descargadas manualmente
   - 38 módulos ec.gob.iess
   - 15 módulos iess

---

## ❌ LO QUE FALTA PARA LEVANTAR EL PROYECTO

### 1. ✅ COMPLETADO: Archivo EAR generado

**Estado:** ✅ GENERADO CORRECTAMENTE

**Ubicación:**
```
/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear
```

**Tamaño:** 60 MB

**Para regenerar si es necesario:**
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
mvn clean package -DskipTests
```

---

### 2. 🔴 CRÍTICO: Servidor de Aplicaciones JBoss/WildFly

**Estado:** ❌ NO INSTALADO

**Requisitos:**
- JBoss EAP 7.2+ o WildFly 14.0.1 Final o superior
- Compatible con Java EE 8
- Java 8 configurado

**Opciones de instalación:**

#### Opción A: WildFly (Gratuito)
```bash
# Descargar WildFly 14.0.1 Final
cd ~/Downloads
curl -O https://download.jboss.org/wildfly/14.0.1.Final/wildfly-14.0.1.Final.tar.gz

# Extraer
tar -xzf wildfly-14.0.1.Final.tar.gz

# Mover a ubicación permanente
sudo mv wildfly-14.0.1.Final /opt/wildfly-14.0.1

# Configurar variables de entorno en .zshrc
export WILDFLY_HOME=/opt/wildfly-14.0.1
export PATH=$WILDFLY_HOME/bin:$PATH
```

#### Opción B: JBoss EAP (Requiere suscripción Red Hat)
- Descargar desde el portal de Red Hat
- Requiere cuenta y suscripción

**Ubicación esperada:**
- WildFly: `/opt/wildfly-14.0.1/` o similar
- JBoss EAP: `/opt/jboss-eap-7.2/` o similar

---

### 3. 🔴 CRÍTICO: Drivers Oracle JDBC

**Estado:** ❌ NO ENCONTRADOS

**Requisitos:**
- Oracle JDBC Driver (ojdbc*.jar)
- Versión compatible con Oracle 10g o superior
- Debe estar en el classpath de JBoss/WildFly

**Ubicación actual de ORACLE_HOME:**
```
/Users/desarrollo/Downloads/instantclient_23_3
```

**Acción requerida:**
1. Descargar Oracle JDBC Driver desde Oracle
2. Copiar `ojdbc*.jar` a `$WILDFLY_HOME/modules/system/layers/base/com/oracle/main/`
3. Crear archivo `module.xml` para el módulo Oracle

**Descarga:**
- Oracle JDBC Driver: https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
- Versión recomendada: ojdbc8.jar (compatible con Java 8)

---

### 4. 🟡 IMPORTANTE: Base de Datos Oracle

**Estado:** ⚠️ NO VERIFICADO

**Requisitos:**
- Oracle Database 10g o superior
- Accesible desde la máquina de desarrollo
- Usuarios y esquemas creados

**Usuarios requeridos según README:**

#### Datasources Genéricos (OBLIGATORIOS):

1. **Usuario Seguridades:**
   - Usuario: `GEN_SEG_USR`
   - Clave: `pruebas`
   - JNDI: `java:jboss/datasources/gen-seg-usr-DS`

2. **Usuario Transversal:**
   - Usuario: `GEN_COMUN_USR`
   - Clave: `pruebas`
   - JNDI: `java:jboss/datasources/gen-comun-usr-DS`

3. **Usuario Auditoría:**
   - Usuario: `GES_AUD_USR`
   - Clave: `desarrollo`
   - JNDI: `java:jboss/datasources/ap-ges-aud-usr-DS`

#### Datasource de Aplicación:

4. **Usuario Aplicación:**
   - Usuario: `AP_ARQ_PBASE_USR`
   - Clave: `pruebas`
   - JNDI: `java:jboss/datasources/ap-ges-pbase-usr-DS`
   - Configurado en: `persistence.xml`

**Acción requerida:**
- Verificar conexión a Oracle Database
- Crear usuarios si no existen
- Verificar permisos y esquemas

---

### 5. 🟡 IMPORTANTE: Configuración de Datasources en JBoss/WildFly

**Estado:** ⚠️ PENDIENTE (requiere JBoss/WildFly instalado)

**Archivo de configuración:**
- WildFly: `$WILDFLY_HOME/standalone/configuration/standalone.xml`
- JBoss EAP: `$JBOSS_HOME/standalone/configuration/standalone.xml`

**Ejemplo de configuración de datasource:**

```xml
<datasource jndi-name="java:jboss/datasources/gen-seg-usr-DS" 
            pool-name="gen-seg-usr-DS">
    <connection-url>jdbc:oracle:thin:@localhost:1521:XE</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>GEN_SEG_USR</user-name>
        <password>pruebas</password>
    </security>
</datasource>
```

**Repetir para los 4 datasources requeridos.**

---

### 6. 🟡 IMPORTANTE: Configuración de Java 8 permanente

**Estado:** ⚠️ Parcialmente configurado

**Problema actual:**
- `.zshrc` tiene Java 8 configurado
- Pero SDKMAN está sobrescribiendo con Java 21

**Solución:**
El `.zshrc` ya tiene la configuración correcta, pero SDKMAN se carga después y sobrescribe. La configuración está correcta, solo necesita recargar la terminal o asegurarse de que Java 8 tenga prioridad.

---

## 📝 CHECKLIST PARA LEVANTAR EL PROYECTO

### Compilación y Empaquetado
- [x] Proyecto compila correctamente
- [x] Archivo EAR generado y verificado (60 MB)
- [x] Tamaño del EAR verificado

### Servidor de Aplicaciones
- [ ] JBoss/WildFly instalado
- [ ] Variables de entorno configuradas (JBOSS_HOME o WILDFLY_HOME)
- [ ] Servidor inicia correctamente
- [ ] Consola de administración accesible

### Base de Datos
- [ ] Oracle Database disponible y accesible
- [ ] Drivers Oracle JDBC instalados en JBoss/WildFly
- [ ] Conexión de base de datos verificada
- [ ] Usuarios de base de datos creados (4 usuarios)

### Configuración de Datasources
- [ ] Datasource gen-seg-usr-DS configurado
- [ ] Datasource gen-comun-usr-DS configurado
- [ ] Datasource ap-ges-aud-usr-DS configurado
- [ ] Datasource ap-ges-pbase-usr-DS configurado
- [ ] Todos los datasources probados y funcionando

### Despliegue
- [ ] EAR copiado a directorio de despliegue de JBoss/WildFly
- [ ] Aplicación desplegada sin errores
- [ ] Logs verificados (sin errores críticos)
- [ ] Aplicación accesible vía navegador

---

## 🚀 PASOS PARA LEVANTAR EL PROYECTO (ORDEN DE EJECUCIÓN)

### Paso 1: Generar EAR
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
mvn clean package -DskipTests
```

### Paso 2: Instalar WildFly
```bash
# Descargar e instalar WildFly 14.0.1
# (Ver instrucciones arriba)
```

### Paso 3: Instalar Drivers Oracle
```bash
# Copiar ojdbc*.jar a WildFly
# Crear module.xml
```

### Paso 4: Configurar Datasources
```bash
# Editar standalone.xml
# Agregar los 4 datasources
```

### Paso 5: Iniciar WildFly
```bash
$WILDFLY_HOME/bin/standalone.sh
```

### Paso 6: Desplegar EAR
```bash
# Copiar EAR a $WILDFLY_HOME/standalone/deployments/
# O usar la consola de administración
```

### Paso 7: Verificar
```bash
# Acceder a: http://localhost:8080/nombre-aplicacion
# Verificar logs en: $WILDFLY_HOME/standalone/log/server.log
```

---

## 📞 CONTACTOS Y RECURSOS

- **Equipo de Arquitectura IESS:** arquitecturadnti@iess.gob.ec
- **Desarrollador del Proyecto:** patricio.pilco@iess.gob.ec
- **Documentación WildFly:** https://docs.wildfly.org/
- **Documentación JBoss EAP:** https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/

---

## ⚠️ NOTAS IMPORTANTES

1. **Java 8 es obligatorio:** El proyecto requiere Java 8, no funcionará con Java 9+
2. **Oracle Database es obligatorio:** No se puede usar PostgreSQL/MySQL
3. **JBoss/WildFly es obligatorio:** No se puede usar Tomcat
4. **Los 4 datasources son obligatorios:** La aplicación no iniciará sin ellos
5. **El proyecto usa Java EE 8:** No Jakarta EE

---

**Última actualización:** 23 de Diciembre de 2025

