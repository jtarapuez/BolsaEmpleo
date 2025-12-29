#!/bin/bash

# Script para descargar todas las dependencias del IESS manualmente
# Esto evita el problema "Negative time" de Maven 3.9.11

REPO_URL="http://192.168.29.6:8080/repository/internal"
MAVEN_REPO="$HOME/.m2/repository"

echo "=========================================="
echo "DESCARGANDO DEPENDENCIAS IESS"
echo "=========================================="
echo ""

# Función para descargar una dependencia
download_dependency() {
    local group_id=$1
    local artifact_id=$2
    local version=$3
    local type=${4:-jar}
    
    # Convertir groupId a ruta
    local group_path=$(echo "$group_id" | tr '.' '/')
    local artifact_dir="$MAVEN_REPO/$group_path/$artifact_id/$version"
    
    echo "📦 Descargando: $group_id:$artifact_id:$version"
    
    # Crear directorio
    mkdir -p "$artifact_dir"
    
    # Descargar POM
    local pom_url="$REPO_URL/$group_path/$artifact_id/$version/$artifact_id-$version.pom"
    echo "  📄 POM: $pom_url"
    if curl -s -f -o "$artifact_dir/$artifact_id-$version.pom" "$pom_url"; then
        echo "    ✅ POM descargado"
    else
        echo "    ❌ Error al descargar POM"
        return 1
    fi
    
    # Descargar JAR/EJB según tipo
    if [ "$type" = "ejb" ]; then
        local jar_url="$REPO_URL/$group_path/$artifact_id/$version/$artifact_id-$version.ejb"
        echo "  📦 EJB: $jar_url"
        if curl -s -f -o "$artifact_dir/$artifact_id-$version.ejb" "$jar_url"; then
            echo "    ✅ EJB descargado"
        else
            # Intentar como .jar si .ejb falla
            jar_url="$REPO_URL/$group_path/$artifact_id/$version/$artifact_id-$version.jar"
            if curl -s -f -o "$artifact_dir/$artifact_id-$version.jar" "$jar_url"; then
                echo "    ✅ JAR descargado (como alternativa a EJB)"
            else
                echo "    ❌ Error al descargar EJB/JAR"
                return 1
            fi
        fi
    else
        local jar_url="$REPO_URL/$group_path/$artifact_id/$version/$artifact_id-$version.jar"
        echo "  📦 JAR: $jar_url"
        if curl -s -f -o "$artifact_dir/$artifact_id-$version.jar" "$jar_url"; then
            echo "    ✅ JAR descargado"
        else
            echo "    ❌ Error al descargar JAR"
            return 1
        fi
    fi
    
    # Crear archivo _maven.repositories
    cat > "$artifact_dir/_maven.repositories" << EOF
#NOTE: This is a Maven Resolver internal file, do not edit manually.
$artifact_id-$version.pom>internal=
$artifact_id-$version.$type>internal=
EOF
    
    echo "  ✅ Dependencia completa: $artifact_id-$version"
    echo ""
    return 0
}

# Lista de dependencias requeridas
echo "Iniciando descarga de dependencias..."
echo ""

# Dependencias del grupo ec.gob.iess
download_dependency "ec.gob.iess" "iess-componente-cifrado" "2.0.0"
download_dependency "ec.gob.iess" "iess-componente-javamail" "1.1.0"
download_dependency "ec.gob.iess" "iess-componente-auditoria" "1.0.2"
download_dependency "ec.gob.iess" "iess-componente-transversal-ejb" "1.10.0" "ejb"
download_dependency "ec.gob.iess" "iess-componente-autorizador-jsf" "1.8.0"
download_dependency "ec.gob.iess" "iess-componente-seguridad-ejb" "1.7.0" "ejb"
download_dependency "ec.gob.iess" "iess-componente-cliente-restful" "1.10.0"
download_dependency "ec.gob.iess" "iess-componente-otp" "1.0.1"
download_dependency "ec.gob.iess" "iess-alfresco" "1.1.0"

# Dependencias del grupo iess
download_dependency "iess" "iess-componente-comun" "2.3.1"
download_dependency "iess" "lombok" "1.18.2"
download_dependency "iess" "iess-ws-modelo" "1.9.0"

# Dependencia commons-codec (puede estar en Maven Central, pero la descargamos del IESS si existe)
download_dependency "iess" "commons-codec" "1.10"

echo "=========================================="
echo "DESCARGA COMPLETADA"
echo "=========================================="
echo ""
echo "Limpiando caché de errores de Maven..."
find "$MAVEN_REPO" -name "*.lastUpdated" -delete 2>/dev/null
find "$MAVEN_REPO" -name "_remote.repositories" -delete 2>/dev/null
echo "✅ Caché limpiada"
echo ""
echo "Ahora puedes ejecutar: mvn clean compile -U"





