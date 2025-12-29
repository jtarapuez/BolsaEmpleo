# 📚 README - PROYECTO BOLSA EMPLEO IESS

**Proyecto:** iess-gestion-proyecto-base  
**Versión:** 3.0.5  
**Tipo:** Proyecto Java EE 8 Multi-módulo (EAR)  
**Organización:** Instituto Ecuatoriano de Seguridad Social (IESS)  
**Última actualización:** Diciembre 2025

---

## 📋 TABLA DE CONTENIDOS

1. [Descripción General](#descripción-general)
2. [Arquitectura del Proyecto](#arquitectura-del-proyecto)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Tecnologías y Dependencias](#tecnologías-y-dependencias)
5. [Requisitos del Sistema](#requisitos-del-sistema)
6. [Configuración del Entorno](#configuración-del-entorno)
7. [Compilación y Despliegue](#compilación-y-despliegue)
8. [Configuración de Base de Datos](#configuración-de-base-de-datos)
9. [Configuración del Servidor](#configuración-del-servidor)
10. [Estructura de Componentes](#estructura-de-componentes)
11. [Páginas y Vistas](#páginas-y-vistas)
12. [Convenciones y Estándares](#convenciones-y-estándares)
13. [Solución de Problemas](#solución-de-problemas)
14. [Contactos y Recursos](#contactos-y-recursos)

---

## 📖 DESCRIPCIÓN GENERAL

Este proyecto es una **plantilla base de desarrollo** para aplicaciones empresariales del IESS, construida sobre la arquitectura de referencia Java EE 8. Proporciona una estructura estándar y componentes reutilizables para el desarrollo de soluciones institucionales.

### Características Principales

- ✅ Arquitectura multi-módulo (EJB, WEB, EAR)
- ✅ Integración con componentes IESS (seguridad, auditoría, transversal)
- ✅ Framework JSF con PrimeFaces 8.0
- ✅ Persistencia JPA con Hibernate
- ✅ Base de datos Oracle
- ✅ Servidor de aplicaciones JBoss EAP 7.2.0

### Estado Actual del Proyecto

- ✅ **Compilación:** Funcionando correctamente
- ✅ **EAR Generado:** `iess-gestion-proyecto-base-ear-3.0.5.ear` (~60 MB)
- ✅ **Servidor:** JBoss EAP 7.2.0 instalado y configurado
- ✅ **Despliegue:** EAR desplegado en `/EAP-7.2.0/standalone/deployments/`

---

## 🏗️ ARQUITECTURA DEL PROYECTO

### Arquitectura Multi-módulo Maven

El proyecto sigue una arquitectura de **3 capas** organizadas en módulos Maven:

```
iess-gestion-proyecto-base (POM padre)
├── iess-gestion-proyecto-base-ejb    (Capa de Negocio)
├── iess-gestion-proyecto-base-web    (Capa de Presentación)
└── iess-gestion-proyecto-base-ear     (Empaquetado Enterprise)
```

#### 1. **Módulo EJB** (`iess-gestion-proyecto-base-ejb`)
- **Tipo:** EJB JAR
- **Responsabilidad:** Lógica de negocio, repositorios JPA, servicios
- **Ubicación:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ejb/`
- **Packaging:** `ejb`

#### 2. **Módulo WEB** (`iess-gestion-proyecto-base-web`)
- **Tipo:** WAR (Web Application Archive)
- **Responsabilidad:** Interfaz de usuario JSF, controladores, beans
- **Ubicación:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-web/`
- **Packaging:** `war`

#### 3. **Módulo EAR** (`iess-gestion-proyecto-base-ear`)
- **Tipo:** EAR (Enterprise Application Archive)
- **Responsabilidad:** Empaquetado final, configuración de despliegue
- **Ubicación:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/`
- **Packaging:** `ear`

---

## 📁 ESTRUCTURA DEL PROYECTO

### Estructura de Directorios

```
BolsaEmpleo/
├── iess-gestion-proyecto-base-master/          # Proyecto principal
│   ├── pom.xml                                 # POM padre
│   ├── iess-gestion-proyecto-base-ejb/        # Módulo EJB
│   │   ├── pom.xml
│   │   └── src/main/
│   │       ├── java/                          # Código Java
│   │       │   └── ec/gob/iess/nombre/proyecto/
│   │       │       └── repositorio/
│   │       │           └── RepositorioJPA.java
│   │       └── resources/
│   │           └── META-INF/
│   │               ├── persistence.xml        # Configuración JPA
│   │               ├── beans.xml              # CDI
│   │               └── jboss.xml
│   ├── iess-gestion-proyecto-base-web/        # Módulo WEB
│   │   ├── pom.xml
│   │   └── src/main/
│   │       ├── java/                          # Beans, Controladores
│   │       │   └── ec/gob/iess/proyecto/base/
│   │       │       └── alfresco/
│   │       │           ├── bean/
│   │       │           ├── controlador/
│   │       │           └── dto/
│   │       ├── resources/                     # Recursos
│   │       │   ├── messages_es_EC.properties
│   │       │   ├── label.properties
│   │       │   └── mensajes.properties
│   │       └── webapp/                        # Recursos web
│   │           ├── pages/                     # Páginas XHTML
│   │           │   ├── principal.xhtml
│   │           │   └── arquitectura/
│   │           └── WEB-INF/
│   │               ├── web.xml                # Configuración web
│   │               ├── faces-config.xml       # Configuración JSF
│   │               └── beans.xml
│   └── iess-gestion-proyecto-base-ear/        # Módulo EAR
│       ├── pom.xml
│       └── src/main/application/
│           └── META-INF/
│               └── application.xml
├── EAP-7.2.0/                                 # Servidor JBoss EAP
│   └── standalone/
│       ├── deployments/                       # Aplicaciones desplegadas
│       └── configuration/                     # Configuración del servidor
├── apache-maven-3.9.11/                       # Maven instalado
├── build_and_deploy.sh                        # Script de despliegue
├── COMANDOS_DESPLIEGUE.md                     # Guía de despliegue
├── COMANDOS_VALIDACION.md                     # Comandos de validación
└── REQUISITOS_PARA_LEVANTAR.md               # Requisitos del sistema
```

### Archivos Clave

#### Configuración Maven
- **POM Principal:** `iess-gestion-proyecto-base-master/pom.xml`
- **POM EJB:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ejb/pom.xml`
- **POM WEB:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-web/pom.xml`
- **POM EAR:** `iess-gestion-proyecto-base-master/iess-gestion-proyecto-base-ear/pom.xml`

#### Configuración Web
- **web.xml:** `iess-gestion-proyecto-base-web/src/main/webapp/WEB-INF/web.xml`
- **faces-config.xml:** `iess-gestion-proyecto-base-web/src/main/webapp/WEB-INF/faces-config.xml`

#### Configuración Persistencia
- **persistence.xml:** `iess-gestion-proyecto-base-ejb/src/main/resources/META-INF/persistence.xml`

---

## 🔧 TECNOLOGÍAS Y DEPENDENCIAS

### Stack Tecnológico Principal

| Componente | Tecnología | Versión |
|------------|-----------|---------|
| **Java** | JDK | 1.8 (Java 8) |
| **Java EE** | Java Enterprise Edition | 8.0 |
| **Servidor** | JBoss EAP | 7.2.0 |
| **Build Tool** | Apache Maven | 3.9.11 |
| **Framework Web** | JSF (JavaServer Faces) | 2.3.13 |
| **UI Framework** | PrimeFaces | 8.0 |
| **Tema** | PrimeFaces Serenity | Light Green |
| **ORM** | Hibernate | 5.4.19.Final |
| **Base de Datos** | Oracle Database | 10g+ |
| **Validación** | Hibernate Validator | 6.0.15.Final |
| **REST Client** | Jersey | 2.30 |
| **Testing** | ArchUnit | 0.12.0 |

### Dependencias IESS (Componentes Internos)

#### Componentes Transversales
- `iess-componente-transversal-ejb:1.10.0` - Componente transversal EJB
- `iess-componente-comun:2.3.1` - Componente común
- `iess-ws-modelo:1.9.0` - Modelo de servicios web

#### Componentes de Seguridad
- `iess-componente-seguridad-ejb:1.7.0` - Seguridad EJB
- `iess-componente-autorizador-jsf:1.8.0` - Autorizador JSF
- `iess-componente-otp:1.0.1` - Código OTP

#### Componentes de Utilidades
- `iess-componente-cifrado:2.0.0` - Cifrado
- `iess-componente-javamail:1.1.0` - JavaMail
- `iess-componente-auditoria:1.0.2` - Auditoría
- `iess-componente-cliente-restful:1.10.0` - Cliente RESTful

#### Utilidades
- `lombok:1.18.2` - Lombok (generación de código)

### Dependencias Públicas (Maven Central)

- `jakarta.faces:2.3.13` - JSF API
- `javaee-api:8.0` - Java EE 8 API
- `primefaces:8.0` - PrimeFaces UI Framework
- `omniutils:0.11` - OmniFaces Utils
- `jersey-client:2.30` - Jersey REST Client
- `commons-io:2.6` - Apache Commons IO
- `commons-fileupload:1.4` - Apache Commons FileUpload
- `hibernate-core:5.4.19.Final` - Hibernate Core
- `hibernate-validator:6.0.15.Final` - Hibernate Validator
- `audit4j-core:2.5.0` - Audit4j

### Repositorios Maven

El proyecto utiliza repositorios internos del IESS:

- **Repositorio Interno (Releases):** `http://192.168.29.6:8080/repository/internal/`
- **Repositorio Snapshots:** `http://192.168.29.6:8080/repository/snapshots/`
- **Maven Central:** Configurado automáticamente

**Configuración en:** `~/.m2/settings.xml`

---

## 💻 REQUISITOS DEL SISTEMA

### Requisitos Mínimos

#### Software
- ✅ **Java JDK 8** (1.8.0_202 o superior)
- ✅ **Apache Maven 3.8.8+** (3.9.11 instalado)
- ✅ **JBoss EAP 7.2.0** o **WildFly 14.0.1+**
- ✅ **Oracle Database 10g+** con drivers JDBC
- ✅ **Acceso a red** al repositorio Maven IESS (`192.168.29.6:8080`)

#### Hardware (Recomendado)
- **RAM:** Mínimo 4GB, recomendado 8GB+
- **Disco:** Mínimo 10GB libres
- **CPU:** Múltiples núcleos recomendados

### Variables de Entorno Requeridas

```bash
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
MAVEN_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11
JBOSS_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0
PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH
```

**Ubicación de configuración:** `~/.zshrc` (macOS) o `~/.bashrc` (Linux)

---

## ⚙️ CONFIGURACIÓN DEL ENTORNO

### 1. Verificar Java 8

```bash
java -version
# Debe mostrar: java version "1.8.0_202"
```

### 2. Verificar Maven

```bash
mvn -version
# Debe mostrar: Apache Maven 3.9.11
# Y usar Java 1.8
```

### 3. Verificar Conectividad al Repositorio IESS

```bash
nc -zv -w 5 192.168.29.6 8080
# Debe mostrar: Connection succeeded
```

### 4. Configurar Maven Settings

El archivo `~/.m2/settings.xml` debe contener:

```xml
<settings>
    <servers>
        <!-- Configuración de servidores si es necesario -->
    </servers>
    <profiles>
        <profile>
            <id>iess-repos</id>
            <repositories>
                <repository>
                    <id>internal</id>
                    <url>http://192.168.29.6:8080/repository/internal/</url>
                    <releases><enabled>true</enabled></releases>
                    <snapshots><enabled>false</enabled></snapshots>
                </repository>
                <repository>
                    <id>snapshots</id>
                    <url>http://192.168.29.6:8080/repository/snapshots/</url>
                    <releases><enabled>false</enabled></releases>
                    <snapshots><enabled>true</enabled></snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <activeProfiles>
        <activeProfile>iess-repos</activeProfile>
    </activeProfiles>
</settings>
```

---

## 🚀 COMPILACIÓN Y DESPLIEGUE

### Método Rápido (Script Automatizado)

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo
./build_and_deploy.sh
```

Este script realiza automáticamente:
1. Validación del entorno
2. Limpieza de compilaciones anteriores
3. Compilación del proyecto
4. Generación del EAR
5. Detención de JBoss (si está corriendo)
6. Copia del EAR a deployments
7. Inicio de JBoss

### Método Manual (Paso a Paso)

#### 1. Configurar Variables de Entorno

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export MAVEN_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11
export JBOSS_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0
export PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH
```

#### 2. Ir al Directorio del Proyecto

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
```

#### 3. Limpiar y Compilar

```bash
# Limpiar compilaciones anteriores
mvn clean

# Compilar el proyecto (sin tests)
mvn install -DskipTests

# O con tests
mvn install
```

#### 4. Verificar EAR Generado

```bash
ls -lh iess-gestion-proyecto-base-ear/target/*.ear
# Debe mostrar: iess-gestion-proyecto-base-ear-3.0.5.ear (~60 MB)
```

#### 5. Desplegar en JBoss

```bash
# Detener JBoss si está corriendo
ps aux | grep "[j]ava.*standalone" | awk '{print $2}' | xargs kill

# Copiar EAR a deployments
cp iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear \
   $JBOSS_HOME/standalone/deployments/

# Iniciar JBoss
cd $JBOSS_HOME
./bin/standalone.sh
```

#### 6. Verificar Despliegue

```bash
# Ver logs
tail -f $JBOSS_HOME/standalone/log/server.log

# Buscar mensaje de despliegue exitoso
grep "WFLYSRV0010.*iess-gestion-proyecto-base" $JBOSS_HOME/standalone/log/server.log
```

### URLs de Acceso

- **Aplicación:** http://localhost:8081/iess-gestion-proyecto-base-web/
- **Consola Admin:** http://localhost:9991

---

## 🗄️ CONFIGURACIÓN DE BASE DE DATOS

### Requisitos de Base de Datos

- **Motor:** Oracle Database 10g o superior
- **Dialecto Hibernate:** `org.hibernate.dialect.Oracle10gDialect`
- **Driver JDBC:** Oracle JDBC Driver (ojdbc8.jar o superior)

### Datasources Requeridos

El proyecto requiere **4 datasources** configurados en JBoss:

#### 1. Datasource de Seguridades (Genérico)

```xml
<datasource jndi-name="java:jboss/datasources/gen-seg-usr-DS" 
            pool-name="gen-seg-usr-DS">
    <connection-url>jdbc:oracle:thin:@HOST:PORT:SID</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>GEN_SEG_USR</user-name>
        <password>pruebas</password>
    </security>
</datasource>
```

#### 2. Datasource Transversal (Genérico)

```xml
<datasource jndi-name="java:jboss/datasources/gen-comun-usr-DS" 
            pool-name="gen-comun-usr-DS">
    <connection-url>jdbc:oracle:thin:@HOST:PORT:SID</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>GEN_COMUN_USR</user-name>
        <password>pruebas</password>
    </security>
</datasource>
```

#### 3. Datasource de Auditoría (Genérico)

```xml
<datasource jndi-name="java:jboss/datasources/ap-ges-aud-usr-DS" 
            pool-name="ap-ges-aud-usr-DS">
    <connection-url>jdbc:oracle:thin:@HOST:PORT:SID</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>GES_AUD_USR</user-name>
        <password>desarrollo</password>
    </security>
</datasource>
```

#### 4. Datasource de Aplicación

```xml
<datasource jndi-name="java:jboss/datasources/ap-ges-pbase-usr-DS" 
            pool-name="ap-ges-pbase-usr-DS">
    <connection-url>jdbc:oracle:thin:@HOST:PORT:SID</connection-url>
    <driver>oracle</driver>
    <security>
        <user-name>AP_ARQ_PBASE_USR</user-name>
        <password>pruebas</password>
    </security>
</datasource>
```

**Configurado en:** `persistence.xml` del módulo EJB

### Configuración de Persistencia

**Archivo:** `iess-gestion-proyecto-base-ejb/src/main/resources/META-INF/persistence.xml`

```xml
<persistence-unit name="ap-ges-pbase-PU" transaction-type="JTA">
    <jta-data-source>java:jboss/datasources/ap-ges-pbase-usr-DS</jta-data-source>
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    <properties>
        <property name="hibernate.dialect" value="org.hibernate.dialect.Oracle10gDialect" />
        <property name="hibernate.id.new_generator_mappings" value="true" />
    </properties>
</persistence-unit>
```

---

## 🖥️ CONFIGURACIÓN DEL SERVIDOR

### JBoss EAP 7.2.0

**Ubicación:** `/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0`

### Configuración de Puertos

- **HTTP:** 8081
- **HTTPS:** 8443
- **Management:** 9991
- **Management HTTPS:** 9993

### Archivos de Configuración

- **Standalone:** `EAP-7.2.0/standalone/configuration/standalone.xml`
- **Logs:** `EAP-7.2.0/standalone/log/server.log`
- **Deployments:** `EAP-7.2.0/standalone/deployments/`

### Comandos Útiles

```bash
# Iniciar JBoss
$JBOSS_HOME/bin/standalone.sh

# Iniciar en background
nohup $JBOSS_HOME/bin/standalone.sh > /tmp/jboss.log 2>&1 &

# Detener JBoss
$JBOSS_HOME/bin/jboss-cli.sh --connect command=:shutdown

# Ver logs
tail -f $JBOSS_HOME/standalone/log/server.log
```

---

## 🧩 ESTRUCTURA DE COMPONENTES

### Módulo EJB

#### Repositorio JPA

**Clase:** `ec.gob.iess.nombre.proyecto.repositorio.RepositorioJPA<T>`

```java
@PersistenceContext(unitName = "ap-ges-pbase-PU")
private EntityManager entityManager;

public GenericoRepositorioJPA<T> getDao() {
    // Retorna repositorio genérico con métodos CRUD
}
```

**Uso:**
- Extiende `GenericoRepositorioJPA` del componente transversal
- Proporciona métodos CRUD genéricos
- Maneja transacciones JTA automáticamente

### Módulo WEB

#### Beans

**Paquete:** `ec.gob.iess.proyecto.base.alfresco.bean`

- `GestorDocumentalBean` - Bean para gestión documental

#### Controladores

**Paquete:** `ec.gob.iess.proyecto.base.alfresco.controlador`

- `AlfrescogGestorControlador` - Controlador de Alfresco
- `CifradoControlador` - Controlador de cifrado
- `CodigoOTPControlador` - Controlador de código OTP

#### DTOs

**Paquete:** `ec.gob.iess.proyecto.base.alfresco.dto`

- `AlfrescoDto` - DTO para Alfresco

#### Enumeraciones

**Paquete:** `ec.gob.iess.proyecto.base.alfresco.enumeracion`

- `AlfrescoEnum` - Enumeraciones de Alfresco

---

## 📄 PÁGINAS Y VISTAS

### Estructura de Páginas

```
webapp/
├── pages/
│   ├── principal.xhtml                    # Página principal
│   └── arquitectura/
│       ├── alfresco/
│       │   ├── alfrescoUploadSingle.xhtml
│       │   ├── alfrescoUploadMultiple.xhtml
│       │   ├── alfrescoDownloadSingleFile.xhtml
│       │   └── alfrescoDownloadMultipleFile.xhtml
│       ├── cifrado.xhtml                  # Página de cifrado
│       ├── codigoOTP.xhtml                # Página de código OTP
│       └── transversal.xhtml              # Página transversal
└── public/
    └── pagina.xhtml                       # Página pública
```

### Página Principal

**Archivo:** `pages/principal.xhtml`

- Utiliza template: `/plantilla/template.xhtml`
- Incluye gestión de sesión
- Scripts de refresco y caducidad de sesión

### Configuración JSF

**Archivo:** `WEB-INF/faces-config.xml`

- **Locale por defecto:** `es_EC`
- **Message Bundle:** `ec.gob.iess.resources.messages`
- **Tema PrimeFaces:** `serenity-lightgreen`
- **Componentes personalizados:** SerenityMenu

### Configuración Web

**Archivo:** `WEB-INF/web.xml`

- **Código de Aplicación:** `IESS-ARQ` (configurable)
- **Timeout de Sesión:** 15 minutos
- **Filtros:**
  - `FiltroUrls` - Filtro de autorización de URLs
  - `PrimeFaces FileUpload Filter` - Filtro de carga de archivos
- **Páginas de Error:**
  - 403: `/errorWeb/403.jsf`
  - 404: `/errorWeb/404.jsf`
  - 500: `/errorWeb/500.jsf`
  - Excepciones: `/errorWeb/errorAplicacion.jsf`

---

## 📝 CONVENCIONES Y ESTÁNDARES

### Convenciones de Nombrado

#### Paquetes Java

```
ec.gob.iess.[nombre-proyecto].[capa].[tipo]
```

Ejemplos:
- `ec.gob.iess.proyecto.base.alfresco.bean`
- `ec.gob.iess.proyecto.base.alfresco.controlador`
- `ec.gob.iess.proyecto.base.alfresco.dto`
- `ec.gob.iess.nombre.proyecto.repositorio`

#### Clases

- **Beans:** `*Bean.java`
- **Controladores:** `*Controlador.java`
- **DTOs:** `*Dto.java`
- **Repositorios:** `*Repositorio.java`
- **Enumeraciones:** `*Enum.java`

### Estándares de Desarrollo

El proyecto sigue los estándares del IESS:

- **Checkstyle:** Configurado en `src/conf/checkstyle.xml`
- **JavaDoc:** Requerido para todas las clases públicas
- **Auditoría:** Integrada con `iess-componente-auditoria`
- **Seguridad:** Integrada con `iess-componente-seguridad-ejb`

### Recursos de Mensajes

**Archivos de propiedades:**

- `messages_es_EC.properties` - Mensajes en español Ecuador
- `label.properties` - Etiquetas
- `mensajes.properties` - Mensajes de la aplicación
- `botones.properties` - Textos de botones

**Ubicación:** `iess-gestion-proyecto-base-web/src/main/resources/`

---

## 🔍 SOLUCIÓN DE PROBLEMAS

### Problemas Comunes

#### 1. Error de Compilación: "Negative time"

**Síntoma:**
```
[ERROR] Could not transfer artifact ... Negative time
```

**Causa:** Maven 3.9+ tiene problemas con fechas inválidas del servidor Archiva

**Solución:**
- Usar Maven 3.8.8 o anterior
- O limpiar caché: `rm -rf ~/.m2/repository/iess ~/.m2/repository/ec/gob/iess`

#### 2. Error: "Could not find or load main class"

**Causa:** Java 8 no está configurado correctamente

**Solución:**
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
java -version  # Verificar que es 1.8
```

#### 3. Error de Despliegue: Datasource no encontrado

**Síntoma:**
```
WFLYCTL0412: Required services that are not installed: ["jboss.jdbc-driver.oracle"]
```

**Solución:**
- Instalar driver Oracle JDBC en JBoss
- Configurar datasources en `standalone.xml`

#### 4. Error: Dependencias IESS no encontradas

**Síntoma:**
```
[ERROR] Failed to execute goal ... Could not resolve dependencies
```

**Solución:**
- Verificar conectividad: `nc -zv 192.168.29.6 8080`
- Verificar `~/.m2/settings.xml`
- Limpiar caché y reintentar: `mvn clean install -U`

#### 5. Error: "ClassNotFoundException"

**Causa:** Dependencias no incluidas en el EAR

**Solución:**
- Verificar que todas las dependencias están en el POM
- Recompilar: `mvn clean package`

### Comandos de Diagnóstico

```bash
# Verificar Java
java -version
javac -version

# Verificar Maven
mvn -version

# Verificar conectividad
nc -zv 192.168.29.6 8080

# Verificar estructura del proyecto
mvn validate

# Ver árbol de dependencias
mvn dependency:tree

# Ver logs de JBoss
tail -f $JBOSS_HOME/standalone/log/server.log
```

### Documentos de Referencia

- **Comandos de Despliegue:** Ver `COMANDOS_DESPLIEGUE.md`
- **Comandos de Validación:** Ver `COMANDOS_VALIDACION.md`
- **Requisitos del Sistema:** Ver `REQUISITOS_PARA_LEVANTAR.md`

---

## 📞 CONTACTOS Y RECURSOS

### Contactos IESS

- **Equipo de Arquitectura:** arquitecturadnti@iess.gob.ec
- **Desarrollador del Proyecto:** patricio.pilco@iess.gob.ec

### Recursos Externos

- **Documentación WildFly:** https://docs.wildfly.org/
- **Documentación JBoss EAP:** https://access.redhat.com/documentation/en-us/red_hat_jboss_enterprise_application_platform/
- **PrimeFaces:** https://www.primefaces.org/documentation/
- **Java EE 8 Tutorial:** https://javaee.github.io/tutorial/

### Estándares IESS

- **Estándar de Desarrollo:** [SharePoint IESS](https://iesscnt.sharepoint.com/)
- **Estándar de Auditoría:** [SharePoint IESS](https://iesscnt.sharepoint.com/)
- **Estándar de Nombrado:** [SharePoint IESS](https://iesscnt.sharepoint.com/)
- **Estándar de Recaptcha:** [SharePoint IESS](https://iesscnt.sharepoint.com/)

---

## 📋 CHECKLIST PARA NUEVOS AGENTES

Cuando un nuevo agente se una al proyecto, debe:

- [ ] Leer este README completo
- [ ] Verificar que Java 8 está instalado y configurado
- [ ] Verificar que Maven está instalado y configurado
- [ ] Verificar conectividad al repositorio IESS
- [ ] Revisar la estructura del proyecto
- [ ] Compilar el proyecto exitosamente
- [ ] Revisar los archivos de configuración clave
- [ ] Entender la arquitectura multi-módulo
- [ ] Revisar las convenciones de nombrado
- [ ] Familiarizarse con los componentes IESS utilizados
- [ ] Revisar las páginas y vistas existentes
- [ ] Verificar que JBoss está configurado correctamente
- [ ] Verificar que los datasources están configurados
- [ ] Desplegar la aplicación exitosamente

---

## 📝 NOTAS IMPORTANTES

1. **Java 8 es obligatorio:** El proyecto requiere Java 8, no funcionará con Java 9+
2. **Oracle Database es obligatorio:** No se puede usar PostgreSQL/MySQL
3. **JBoss/WildFly es obligatorio:** No se puede usar Tomcat
4. **Los 4 datasources son obligatorios:** La aplicación no iniciará sin ellos
5. **El proyecto usa Java EE 8:** No Jakarta EE
6. **Las dependencias del IESS son privadas:** Solo están en el repositorio interno
7. **El código de aplicación debe configurarse:** En `web.xml` como `CODIGO_APLICACION`

---

## 🔄 HISTORIAL DE CAMBIOS

### Versión 3.0.5 (Actual)
- ✅ Proyecto compilando correctamente
- ✅ EAR generado y desplegado
- ✅ JBoss EAP 7.2.0 configurado
- ✅ Dependencias IESS resueltas

---

**Última actualización:** Diciembre 2025  
**Mantenido por:** Equipo de Arquitectura IESS  
**Licencia:** Copyright 2020 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR

---

<p align="center">
  <strong>© 2021 IESS, todos los derechos reservados.</strong><br>
  <a href="https://www.iess.gob.ec">https://www.iess.gob.ec</a>
</p>

