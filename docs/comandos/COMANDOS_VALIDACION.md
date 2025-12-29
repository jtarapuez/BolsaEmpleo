# 🔧 COMANDOS DE VALIDACIÓN - PROYECTO IESS

Este documento contiene todos los comandos necesarios para validar y verificar cada componente del proyecto paso a paso.

---

## 📋 PASO 1: VALIDAR JAVA

### 1.1 Verificar versión de Java instalada
```bash
java -version
```
**Resultado esperado:**
```
java version "1.8.0_202"
Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
```

### 1.2 Verificar compilador Java
```bash
javac -version
```
**Resultado esperado:**
```
javac 1.8.0_202
```

### 1.3 Verificar JAVA_HOME
```bash
echo $JAVA_HOME
```
**Resultado esperado:**
```
/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
```

### 1.4 Listar todas las instalaciones de Java en el sistema
```bash
/usr/libexec/java_home -V
```
**Resultado esperado:**
```
Matching Java Virtual Machines (3):
    17.0.12 (arm64) "Oracle Corporation" - "Java SE 17.0.12" /Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
    1.8.0_202 (x86_64) "Oracle Corporation" - "Java SE 8" /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
    1.8.202.08 (x86_64) "Oracle Corporation" - "Java" /Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home
```

### 1.5 Verificar que Java está en el PATH
```bash
which java
which javac
```
**Resultado esperado:**
```
/usr/bin/java
/usr/bin/javac
```
O si está configurado con JAVA_HOME:
```
/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/bin/java
```

---

## 📋 PASO 2: VALIDAR MAVEN

### 2.1 Verificar versión de Maven
```bash
mvn -version
```
**Resultado esperado:**
```
Apache Maven 3.8.8 (o 3.9.11)
Maven home: /ruta/a/maven
Java version: 1.8.0_202, vendor: Oracle Corporation
```

### 2.2 Verificar MAVEN_HOME
```bash
echo $MAVEN_HOME
```
**Resultado esperado:**
```
/opt/maven-3.8.8
```
O si usa SDKMAN:
```
/Users/desarrollo/.sdkman/candidates/maven/current
```

### 2.3 Verificar ubicación de Maven
```bash
which mvn
```
**Resultado esperado:**
```
/opt/maven-3.8.8/bin/mvn
```

### 2.4 Verificar configuración de Maven (settings.xml)
```bash
cat ~/.m2/settings.xml
```

**Ubicación del archivo:**
- **Ruta completa:** `/Users/desarrollo/.m2/settings.xml`
- **Ruta relativa:** `~/.m2/settings.xml`
- **Directorio:** `$HOME/.m2/` (directorio oculto en el home del usuario)

**Para abrir en el editor:**
```bash
# Con nano
nano ~/.m2/settings.xml

# Con vim
vim ~/.m2/settings.xml

# Con VS Code
code ~/.m2/settings.xml

# Con Finder (macOS)
open ~/.m2
```

**Verificar que existe:**
```bash
ls -la ~/.m2/settings.xml
```

**Debe contener:**
- Repositorios del IESS configurados
- Bloqueador HTTP desactivado para repositorios IESS

---

## 📋 PASO 3: VALIDAR CONECTIVIDAD DE RED

### 3.1 Verificar conectividad al servidor del repositorio Maven IESS
```bash
nc -zv -w 5 192.168.29.6 8080
```
**Resultado esperado:**
```
Connection to 192.168.29.6 port 8080 [tcp/http-alt] succeeded!
```

### 3.2 Verificar acceso HTTP al repositorio (curl)
```bash
curl -I http://192.168.29.6:8080/repository/internal/
```
**Resultado esperado:**
```
HTTP/1.1 200 OK
Date: ...
Server: Jetty(8.1.14.v20131031)
```

### 3.3 Verificar que se puede descargar un artefacto específico
```bash
curl -I "http://192.168.29.6:8080/repository/internal/iess/iess-componente-comun/2.3.1/iess-componente-comun-2.3.1.pom"
```
**Resultado esperado:**
```
HTTP/1.1 200 OK
Content-Type: application/xml
Content-Length: 398
```

### 3.4 Verificar acceso completo al repositorio
```bash
curl -v http://192.168.29.6:8080/repository/internal/ 2>&1 | head -20
```

---

## 📋 PASO 4: VALIDAR VARIABLES DE ENTORNO

### 4.1 Verificar todas las variables de entorno relacionadas
```bash
echo "JAVA_HOME: $JAVA_HOME"
echo "MAVEN_HOME: $MAVEN_HOME"
echo "M2_HOME: $M2_HOME"
echo "PATH: $PATH"
```

### 4.2 Verificar configuración en .zshrc
```bash
cat ~/.zshrc | grep -E "(JAVA_HOME|MAVEN_HOME|PATH|ORACLE_HOME)"
```
**Debe mostrar:**
- JAVA_HOME configurado
- MAVEN_HOME configurado
- PATH incluyendo Java y Maven

### 4.3 Verificar que las variables están activas en la sesión actual
```bash
env | grep -E "(JAVA|MAVEN|ORACLE)"
```

---

## 📋 PASO 5: VALIDAR ESTRUCTURA DEL PROYECTO

### 5.1 Navegar al directorio del proyecto
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
```

### 5.2 Verificar que existe el pom.xml principal
```bash
ls -la pom.xml
```
**Resultado esperado:**
```
-rw-rw-r--@ 1 usuario staff 3124 fecha pom.xml
```

### 5.3 Verificar estructura de módulos
```bash
ls -la
```
**Debe mostrar:**
- iess-gestion-proyecto-base-ear/
- iess-gestion-proyecto-base-ejb/
- iess-gestion-proyecto-base-web/
- pom.xml

### 5.4 Verificar que cada módulo tiene su pom.xml
```bash
ls -la */pom.xml
```

---

## 📋 PASO 6: VALIDAR CONFIGURACIÓN MAVEN DEL PROYECTO

### 6.1 Validar estructura Maven (sin compilar)
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
mvn validate
```
**Resultado esperado:**
```
[INFO] BUILD SUCCESS
```

### 6.2 Verificar que Maven detecta los módulos
```bash
mvn validate 2>&1 | grep "Reactor Build Order"
```
**Resultado esperado:**
```
[INFO] Reactor Build Order:
[INFO] 
[INFO] iess-gestion-proyecto-base                                         [pom]
[INFO] iess-gestion-proyecto-base-ejb                                     [ejb]
[INFO] iess-gestion-proyecto-base-web                                     [war]
[INFO] iess-gestion-proyecto-base-ear                                     [ear]
```

### 6.3 Verificar repositorios configurados en el proyecto
```bash
mvn help:effective-settings 2>&1 | grep -A 10 "repositories"
```

---

## 📋 PASO 7: VALIDAR DEPENDENCIAS

### 7.1 Verificar dependencias del proyecto (árbol de dependencias)
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
mvn dependency:tree
```
**Nota:** Esto puede fallar si hay dependencias faltantes, pero muestra qué se está intentando descargar.

### 7.2 Verificar dependencias faltantes
```bash
mvn dependency:resolve 2>&1 | grep -E "(ERROR|Could not|FAILURE)"
```

### 7.3 Verificar dependencias IESS en repositorio local
```bash
ls -la ~/.m2/repository/iess/
ls -la ~/.m2/repository/ec/gob/iess/
```

### 7.4 Limpiar caché de dependencias IESS (si hay problemas)
```bash
rm -rf ~/.m2/repository/iess
rm -rf ~/.m2/repository/ec/gob/iess
```

---

## 📋 PASO 8: INTENTAR COMPILAR EL PROYECTO

### 8.1 Limpiar proyecto
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
mvn clean
```

### 8.2 Compilar proyecto
```bash
mvn compile
```
**Resultado esperado si todo está bien:**
```
[INFO] BUILD SUCCESS
```

**Si hay errores, buscar:**
```bash
mvn compile 2>&1 | grep -E "(ERROR|FAILURE|Could not)"
```

### 8.3 Compilar con actualización forzada de dependencias
```bash
mvn clean compile -U
```

### 8.4 Compilar con modo verbose (más información)
```bash
mvn clean compile -X 2>&1 | tail -50
```

---

## 📋 PASO 9: VALIDAR EMPAQUETADO

### 9.1 Empaquetar proyecto completo
```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
mvn clean package
```

### 9.2 Verificar que se generaron los archivos
```bash
ls -lh */target/*.jar */target/*.war */target/*.ear 2>/dev/null
```

### 9.3 Verificar tamaño de los archivos generados
```bash
find . -name "*.ear" -o -name "*.war" -o -name "*.jar" | grep target | xargs ls -lh
```

---

## 📋 PASO 10: VALIDAR CONFIGURACIÓN DE BASE DE DATOS

### 10.1 Verificar ORACLE_HOME
```bash
echo $ORACLE_HOME
```

### 10.2 Verificar drivers Oracle
```bash
ls -la $ORACLE_HOME/ojdbc*.jar 2>/dev/null || echo "Drivers Oracle no encontrados"
```

### 10.3 Verificar configuración de persistencia
```bash
cat iess-gestion-proyecto-base-ejb/src/main/resources/META-INF/persistence.xml
```
**Debe contener:**
- Datasource: `java:jboss/datasources/ap-ges-pbase-usr-DS`
- Dialecto: `org.hibernate.dialect.Oracle10gDialect`

---

## 📋 PASO 11: VALIDAR SERVIDOR DE APLICACIONES (JBoss/WildFly)

### 11.1 Verificar si JBoss/WildFly está instalado
```bash
which jboss-cli.sh 2>/dev/null || which standalone.sh 2>/dev/null || echo "JBoss/WildFly no encontrado en PATH"
```

### 11.2 Buscar instalaciones de JBoss/WildFly
```bash
find ~ -name "standalone.sh" -o -name "jboss-cli.sh" 2>/dev/null | head -5
```

### 11.3 Verificar versión de Java EE requerida
```bash
grep -r "javaee-api\|jakartaee" iess-gestion-proyecto-base-master/*/pom.xml
```
**Debe mostrar:** `javaee-api:8.0` (Java EE 8)

---

## 📋 PASO 12: COMANDOS DE DIAGNÓSTICO COMPLETO

### 12.1 Script completo de validación (copiar y pegar todo)
```bash
#!/bin/bash

echo "=========================================="
echo "VALIDACIÓN COMPLETA DEL PROYECTO IESS"
echo "=========================================="
echo ""

echo "--- 1. JAVA ---"
java -version 2>&1
echo ""
javac -version 2>&1
echo ""
echo "JAVA_HOME: $JAVA_HOME"
echo ""

echo "--- 2. MAVEN ---"
mvn -version 2>&1
echo ""
echo "MAVEN_HOME: $MAVEN_HOME"
echo ""

echo "--- 3. CONECTIVIDAD ---"
nc -zv -w 5 192.168.29.6 8080 2>&1
echo ""

echo "--- 4. PROYECTO ---"
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✅ Directorio del proyecto encontrado"
    ls -la pom.xml 2>&1
    echo ""
    echo "Módulos encontrados:"
    ls -d */ 2>&1
else
    echo "❌ No se pudo acceder al directorio del proyecto"
fi
echo ""

echo "--- 5. VALIDACIÓN MAVEN ---"
mvn validate 2>&1 | tail -10
echo ""

echo "--- 6. DEPENDENCIAS IESS EN CACHÉ ---"
echo "Dependencias iess encontradas:"
ls -la ~/.m2/repository/iess/ 2>/dev/null | head -5 || echo "Ninguna"
echo ""
echo "Dependencias ec.gob.iess encontradas:"
ls -la ~/.m2/repository/ec/gob/iess/ 2>/dev/null | head -5 || echo "Ninguna"
echo ""

echo "=========================================="
echo "VALIDACIÓN COMPLETADA"
echo "=========================================="
```

### 12.2 Guardar y ejecutar el script
```bash
# Guardar el script
cat > ~/validar_proyecto_iess.sh << 'SCRIPT'
[paste el contenido del script anterior aquí]
SCRIPT

# Dar permisos de ejecución
chmod +x ~/validar_proyecto_iess.sh

# Ejecutar
~/validar_proyecto_iess.sh
```

---

## 📋 PASO 13: COMANDOS DE SOLUCIÓN DE PROBLEMAS

### 13.1 Si Maven da error "Negative time"
```bash
# Limpiar caché problemática
rm -rf ~/.m2/repository/iess
rm -rf ~/.m2/repository/ec/gob/iess

# Verificar versión de Maven (debe ser 3.8.8 o anterior)
mvn -version

# Si es 3.9+, considerar instalar Maven 3.8.8
```

### 13.2 Si hay problemas de conectividad
```bash
# Verificar firewall
ping -c 3 192.168.29.6

# Verificar DNS
nslookup 192.168.29.6

# Verificar proxy (si aplica)
echo $http_proxy
echo $https_proxy
```

### 13.3 Si Java no se encuentra
```bash
# Buscar instalaciones de Java
/usr/libexec/java_home -V

# Configurar JAVA_HOME manualmente (reemplazar con tu versión)
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
```

### 13.4 Si Maven no se encuentra
```bash
# Buscar instalaciones de Maven
find /opt /usr/local ~ -name "mvn" 2>/dev/null

# Verificar SDKMAN (si lo usas)
sdk current maven
```

---

## 📋 PASO 14: COMANDOS DE INSTALACIÓN (SI FALTAN COMPONENTES)

### 14.1 Instalar Maven 3.8.8 (recomendado)
```bash
# Descargar
cd ~/Downloads
curl -O https://archive.apache.org/dist/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz

# Extraer
tar -xzf apache-maven-3.8.8-bin.tar.gz

# Mover a directorio permanente
sudo mv apache-maven-3.8.8 /opt/maven-3.8.8

# Agregar a .zshrc
echo 'export MAVEN_HOME=/opt/maven-3.8.8' >> ~/.zshrc
echo 'export PATH=$MAVEN_HOME/bin:$PATH' >> ~/.zshrc

# Recargar configuración
source ~/.zshrc

# Verificar
mvn -version
```

### 14.2 Verificar configuración de Maven settings.xml
```bash
# Ver contenido actual
cat ~/.m2/settings.xml

# Si no existe, crear directorio
mkdir -p ~/.m2
```

---

## 📝 NOTAS IMPORTANTES

1. **Ejecutar comandos en orden:** Los pasos están organizados de forma lógica, ejecuta en orden.

2. **Guardar salidas:** Si encuentras errores, guarda la salida:
   ```bash
   mvn compile > maven_output.log 2>&1
   ```

3. **Verificar cada paso:** No pases al siguiente paso hasta que el anterior esté correcto.

4. **Usar -U en Maven:** Si hay problemas con dependencias, usa `-U` para forzar actualización:
   ```bash
   mvn clean compile -U
   ```

5. **Modo verbose:** Para más información, usa `-X`:
   ```bash
   mvn compile -X
   ```

---

**Última actualización:** 23 de Diciembre de 2025

