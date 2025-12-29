# Comandos para Compilar, Generar EAR y Desplegar

## 🚀 Método Rápido (Script Automatizado)

```bash
# Ejecutar el script completo
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo
./build_and_deploy.sh
```

Este script hace todo automáticamente:
1. Valida el entorno (Java, Maven)
2. Limpia compilaciones anteriores
3. Compila el proyecto
4. Genera el EAR
5. Detiene JBoss si está corriendo
6. Copia el EAR a deployments
7. Inicia JBoss

---

## 📋 Método Manual (Paso a Paso)

### 1. Configurar Variables de Entorno

```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export MAVEN_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11
export PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH
export JBOSS_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0
```

### 2. Ir al Directorio del Proyecto

```bash
cd /Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master
```

### 3. Limpiar y Compilar

```bash
# Limpiar compilaciones anteriores
mvn clean

# Compilar el proyecto (sin tests)
mvn install -DskipTests

# O con tests (si los tienes configurados)
mvn install
```

### 4. Buscar el EAR Generado

```bash
# El EAR se genera en el módulo ear
find . -name "*.ear" -path "*/target/*"
```

Normalmente está en:
```
iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear
```

### 5. Detener JBoss (si está corriendo)

```bash
# Buscar el proceso
ps aux | grep "[j]ava.*standalone"

# Detener (reemplaza PID con el número del proceso)
kill <PID>

# O forzar detención
kill -9 <PID>

# O usar jboss-cli
$JBOSS_HOME/bin/jboss-cli.sh --connect command=:shutdown
```

### 6. Copiar EAR a JBoss Deployments

```bash
# Limpiar despliegues anteriores
rm -f $JBOSS_HOME/standalone/deployments/iess-gestion-proyecto-base-ear-*.ear*

# Copiar el nuevo EAR
cp iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear \
   $JBOSS_HOME/standalone/deployments/
```

### 7. Iniciar JBoss

```bash
cd $JBOSS_HOME
./bin/standalone.sh
```

O en background:
```bash
cd $JBOSS_HOME
nohup ./bin/standalone.sh > /tmp/jboss_startup.log 2>&1 &
```

### 8. Verificar el Despliegue

```bash
# Ver logs en tiempo real
tail -f $JBOSS_HOME/standalone/log/server.log

# Buscar mensaje de despliegue exitoso
grep "WFLYSRV0010.*iess-gestion-proyecto-base" $JBOSS_HOME/standalone/log/server.log

# Verificar que el archivo .deployed existe
ls -la $JBOSS_HOME/standalone/deployments/iess-gestion-proyecto-base-ear-*.deployed
```

### 9. Acceder a la Aplicación

- **URL Aplicación:** http://localhost:8081/iess-gestion-proyecto-base-web/
- **Consola Admin:** http://localhost:9991

---

## 🔍 Comandos de Verificación

### Verificar que Java y Maven están correctos

```bash
java -version  # Debe ser 1.8.x
mvn -version   # Debe mostrar Java 1.8
```

### Verificar que el EAR se generó

```bash
ls -lh iess-gestion-proyecto-base-ear/target/*.ear
```

### Verificar procesos de JBoss

```bash
ps aux | grep "[j]ava.*standalone"
```

### Ver logs de inicio

```bash
tail -f $JBOSS_HOME/standalone/log/server.log
```

### Verificar despliegue

```bash
# Ver archivos en deployments
ls -la $JBOSS_HOME/standalone/deployments/iess-gestion-proyecto-base-ear-*

# Verificar en el log
grep "iess-gestion-proyecto-base" $JBOSS_HOME/standalone/log/server.log | tail -10
```

---

## ⚠️ Solución de Problemas

### Si el EAR no se genera:

```bash
# Verificar errores de compilación
mvn clean install -X  # Modo verbose

# Verificar dependencias
mvn dependency:tree
```

### Si JBoss no inicia:

```bash
# Ver logs de error
tail -100 $JBOSS_HOME/standalone/log/server.log

# Verificar puertos
lsof -i :8081
lsof -i :9991
```

### Si el despliegue falla:

```bash
# Ver archivos de error en deployments
ls -la $JBOSS_HOME/standalone/deployments/*.failed

# Ver contenido del archivo .failed
cat $JBOSS_HOME/standalone/deployments/iess-gestion-proyecto-base-ear-*.failed
```

---

## 📝 Resumen de Rutas Importantes

- **Proyecto:** `/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master`
- **EAR generado:** `iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear`
- **JBoss deployments:** `/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0/standalone/deployments`
- **Log JBoss:** `/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0/standalone/log/server.log`
