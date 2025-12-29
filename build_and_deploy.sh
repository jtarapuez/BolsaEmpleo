#!/bin/bash

# Script para compilar, generar EAR y desplegar en JBoss
# Proyecto: BolsaEmpleo - IESS

set -e  # Salir si hay algún error

# Colores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== COMPILACIÓN Y DESPLIEGUE - BOLSA EMPLEO ===${NC}"
echo ""

# Variables
PROJECT_DIR="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/iess-gestion-proyecto-base-master"
JBOSS_DEPLOY_DIR="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0/standalone/deployments"
JBOSS_HOME="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/EAP-7.2.0"
JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home"
MAVEN_HOME="/Users/desarrollo/Documents/IESS/CURSOR/BolsaEmpleo/apache-maven-3.9.11"

# Configurar variables de entorno
export JAVA_HOME=$JAVA_HOME
export PATH=$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH

echo -e "${YELLOW}1. Validando entorno...${NC}"
echo "   JAVA_HOME: $JAVA_HOME"
echo "   MAVEN_HOME: $MAVEN_HOME"
echo "   Proyecto: $PROJECT_DIR"
echo ""

# Validar Java
if [ ! -d "$JAVA_HOME" ]; then
    echo -e "${RED}ERROR: Java 8 no encontrado en $JAVA_HOME${NC}"
    exit 1
fi

# Validar Maven
if [ ! -f "$MAVEN_HOME/bin/mvn" ]; then
    echo -e "${RED}ERROR: Maven no encontrado en $MAVEN_HOME${NC}"
    exit 1
fi

echo -e "${GREEN}✓ Entorno validado${NC}"
echo ""

# Ir al directorio del proyecto
cd "$PROJECT_DIR"

echo -e "${YELLOW}2. Limpiando compilaciones anteriores...${NC}"
mvn clean
echo ""

echo -e "${YELLOW}3. Compilando proyecto...${NC}"
mvn install -DskipTests
echo ""

# Buscar el EAR generado
EAR_FILE=$(find . -name "*.ear" -path "*/target/*" | head -1)

if [ -z "$EAR_FILE" ]; then
    echo -e "${RED}ERROR: No se encontró el archivo EAR generado${NC}"
    exit 1
fi

EAR_NAME=$(basename "$EAR_FILE")
EAR_FULL_PATH=$(realpath "$EAR_FILE")

echo -e "${GREEN}✓ EAR generado: $EAR_NAME${NC}"
echo "   Tamaño: $(ls -lh "$EAR_FULL_PATH" | awk '{print $5}')"
echo ""

echo -e "${YELLOW}4. Deteniendo JBoss si está corriendo...${NC}"
JBOSS_PID=$(ps aux | grep "[j]ava.*standalone" | awk '{print $2}' | head -1)
if [ ! -z "$JBOSS_PID" ]; then
    echo "   Deteniendo proceso JBoss (PID: $JBOSS_PID)..."
    kill $JBOSS_PID 2>/dev/null || true
    sleep 3
    # Verificar si se detuvo
    if ps -p $JBOSS_PID > /dev/null 2>&1; then
        echo "   Forzando detención..."
        kill -9 $JBOSS_PID 2>/dev/null || true
    fi
    echo -e "${GREEN}✓ JBoss detenido${NC}"
else
    echo "   JBoss no está corriendo"
fi
echo ""

echo -e "${YELLOW}5. Limpiando despliegues anteriores...${NC}"
rm -f "$JBOSS_DEPLOY_DIR"/iess-gestion-proyecto-base-ear-*.ear*
echo -e "${GREEN}✓ Despliegues anteriores eliminados${NC}"
echo ""

echo -e "${YELLOW}6. Copiando EAR a JBoss...${NC}"
cp "$EAR_FULL_PATH" "$JBOSS_DEPLOY_DIR/"
echo -e "${GREEN}✓ EAR copiado a: $JBOSS_DEPLOY_DIR/$EAR_NAME${NC}"
echo ""

echo -e "${YELLOW}7. Iniciando JBoss...${NC}"
cd "$JBOSS_HOME"
export JBOSS_HOME=$JBOSS_HOME
export JAVA_HOME=$JAVA_HOME
export PATH=$JAVA_HOME/bin:$PATH

# Iniciar JBoss en background
nohup ./bin/standalone.sh > /tmp/jboss_startup.log 2>&1 &
JBOSS_PID=$!

echo "   JBoss iniciando (PID: $JBOSS_PID)"
echo "   Esperando que inicie..."

# Esperar a que JBoss inicie (máximo 60 segundos)
TIMEOUT=60
ELAPSED=0
while [ $ELAPSED -lt $TIMEOUT ]; do
    if tail -20 /tmp/jboss_startup.log 2>/dev/null | grep -q "WFLYSRV0025"; then
        echo -e "${GREEN}✓ JBoss iniciado correctamente${NC}"
        break
    fi
    sleep 2
    ELAPSED=$((ELAPSED + 2))
    echo -n "."
done

if [ $ELAPSED -ge $TIMEOUT ]; then
    echo -e "${YELLOW}⚠ Tiempo de espera agotado. Verifica los logs manualmente.${NC}"
fi

echo ""
echo -e "${GREEN}=== DESPLIEGUE COMPLETADO ===${NC}"
echo ""
echo "📋 Información:"
echo "   EAR: $EAR_NAME"
echo "   Ubicación: $JBOSS_DEPLOY_DIR"
echo "   Log: $JBOSS_HOME/standalone/log/server.log"
echo ""
echo "🌐 URLs:"
echo "   Aplicación: http://localhost:8081/iess-gestion-proyecto-base-web/"
echo "   Consola admin: http://localhost:9991"
echo ""
echo "📝 Comandos útiles:"
echo "   Ver logs: tail -f $JBOSS_HOME/standalone/log/server.log"
echo "   Detener JBoss: kill $JBOSS_PID"
echo ""
