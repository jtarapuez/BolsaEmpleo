#!/bin/bash

# Script para iniciar JBoss EAP 7.2.0 con el proyecto IESS

# Configurar Java 8
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# Configurar JBoss
export JBOSS_HOME=/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0
export PATH=$JBOSS_HOME/bin:$PATH

# Verificar configuración
echo "=========================================="
echo "INICIANDO JBOSS EAP 7.2.0"
echo "=========================================="
echo ""
echo "Java version:"
java -version 2>&1 | head -1
echo ""
echo "JBOSS_HOME: $JBOSS_HOME"
echo ""
echo "EAR desplegado:"
ls -lh $JBOSS_HOME/standalone/deployments/iess-gestion-proyecto-base-ear-3.0.5.ear 2>/dev/null || echo "  ⚠️ EAR no encontrado"
echo ""
echo "Iniciando JBoss..."
echo "=========================================="
echo ""

# Configurar memoria adicional si es necesario (sobrescribe standalone.conf si hay problemas)
# export JAVA_OPTS="-Xms1303m -Xmx1303m -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=512m -Djava.net.preferIPv4Stack=true"

# Iniciar JBoss en modo standalone
cd $JBOSS_HOME
./bin/standalone.sh



