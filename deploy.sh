#!/bin/bash

# Script simplificado de despliegue - BolsaEmpleo IESS
# Solo 3 pasos: Compilar, Copiar, Levantar JBoss

# Variables
PROJECT_DIR="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master"
JBOSS_DEPLOY_DIR="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0/standalone/deployments"
JBOSS_HOME="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0"
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home"
MAVEN_HOME="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11"

# Configurar entorno
export JAVA_HOME=$JAVA_HOME
export PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH

echo "🚀 DESPLIEGUE SIMPLIFICADO - BOLSA EMPLEO"
echo ""

# PASO 1: COMPILAR
echo "1️⃣ Compilando proyecto..."
cd "$PROJECT_DIR"
mvn clean install -DskipTests -q
EAR_FILE="$PROJECT_DIR/iess-gestion-proyecto-base-ear/target/iess-gestion-proyecto-base-ear-3.0.5.ear"
echo "   ✓ Compilación completada"
echo ""

# PASO 2: COPIAR EAR
echo "2️⃣ Copiando EAR a JBoss..."
# Detener JBoss si está corriendo
JBOSS_PID=$(ps aux | grep "[j]ava.*standalone" | awk '{print $2}' | head -1)
if [ ! -z "$JBOSS_PID" ]; then
    kill $JBOSS_PID 2>/dev/null || kill -9 $JBOSS_PID 2>/dev/null || true
    sleep 2
fi
# Limpiar y copiar
rm -f "$JBOSS_DEPLOY_DIR"/iess-gestion-proyecto-base-ear-*.ear* 2>/dev/null
cp "$EAR_FILE" "$JBOSS_DEPLOY_DIR/"
echo "   ✓ EAR copiado"
echo ""

# PASO 3: LEVANTAR JBOSS
echo "3️⃣ Levantando JBoss..."
cd "$JBOSS_HOME"
nohup ./bin/standalone.sh > /tmp/jboss_startup.log 2>&1 &
echo "   ✓ JBoss iniciando en background"
echo ""

echo "✅ DESPLIEGUE COMPLETADO"
echo ""
echo "📋 URL: http://localhost:8081/iess-gestion-proyecto-base-web/pages/bolsaEmpleo/registroPostulante.jsf"
echo "📝 Logs: tail -f $JBOSS_HOME/standalone/log/server.log"
echo ""

