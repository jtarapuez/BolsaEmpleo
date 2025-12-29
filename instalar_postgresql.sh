#!/bin/bash
# Script para instalar PostgreSQL 16 con Homebrew y configurar base de datos

set -e  # Salir si hay algún error

PASSWORD="Desa2023"
DB_NAME="bolsa_empleo"
DB_USER="bolsa_empleo_user"

echo "=========================================="
echo "INSTALACIÓN DE POSTGRESQL 16"
echo "=========================================="
echo ""

# Verificar si Homebrew está instalado
if ! command -v brew &> /dev/null; then
    echo "❌ Homebrew no está instalado"
    echo ""
    echo "Por favor ejecuta primero:"
    echo "/bin/bash -c \"\$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)\""
    echo ""
    echo "Luego ejecuta este script nuevamente:"
    echo "./instalar_postgresql.sh"
    exit 1
fi

echo "✅ Homebrew encontrado"
echo ""

# Instalar PostgreSQL 16
echo "Instalando PostgreSQL 16..."
if brew list postgresql@16 &>/dev/null; then
    echo "⚠️  PostgreSQL 16 ya está instalado"
else
    brew install postgresql@16
    echo "✅ PostgreSQL 16 instalado"
fi

# Configurar variables de entorno
echo ""
echo "Configurando variables de entorno..."
echo ""

# Agregar a .zshrc si no existe
if ! grep -q "PostgreSQL 16" ~/.zshrc 2>/dev/null; then
    cat >> ~/.zshrc << 'ZSHRC_EOF'

# PostgreSQL 16
export PGHOME=$(brew --prefix postgresql@16)
export PGDATA=$PGHOME/var/postgres
export PATH=$PGHOME/bin:$PATH
export DYLD_LIBRARY_PATH=$PGHOME/lib:$DYLD_LIBRARY_PATH
ZSHRC_EOF
    echo "✅ Variables agregadas a ~/.zshrc"
else
    echo "⚠️  Variables ya existen en ~/.zshrc"
fi

# Recargar configuración
export PGHOME=$(brew --prefix postgresql@16)
export PGDATA=$PGHOME/var/postgres
export PATH=$PGHOME/bin:$PATH
export DYLD_LIBRARY_PATH=$PGHOME/lib:$DYLD_LIBRARY_PATH

echo ""
echo "Ubicación PostgreSQL: $PGHOME"
echo ""

# Inicializar base de datos si no existe
if [ ! -d "$PGDATA" ]; then
    echo "Inicializando base de datos..."
    initdb -D "$PGDATA" --locale=en_US.UTF-8 -E UTF-8
    echo "✅ Base de datos inicializada"
else
    echo "⚠️  Base de datos ya existe en $PGDATA"
fi

# Iniciar PostgreSQL
echo ""
echo "Iniciando PostgreSQL..."
brew services start postgresql@16 || pg_ctl -D "$PGDATA" start

# Esperar a que PostgreSQL esté listo
echo "Esperando que PostgreSQL inicie..."
sleep 5

# Verificar que está corriendo
if pg_isready -q; then
    echo "✅ PostgreSQL está corriendo"
else
    echo "⚠️  Esperando un poco más..."
    sleep 5
    if ! pg_isready -q; then
        echo "❌ Error: PostgreSQL no está respondiendo"
        exit 1
    fi
fi

# Crear base de datos y usuario
echo ""
echo "Creando base de datos y usuario..."
echo ""

# Crear base de datos
psql -U "$USER" -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = '$DB_NAME'" | grep -q 1 || \
psql -U "$USER" -d postgres -c "CREATE DATABASE $DB_NAME;"

echo "✅ Base de datos '$DB_NAME' creada o ya existe"

# Crear usuario si no existe
psql -U "$USER" -d postgres -tc "SELECT 1 FROM pg_user WHERE usename = '$DB_USER'" | grep -q 1 || \
psql -U "$USER" -d postgres -c "CREATE USER $DB_USER WITH PASSWORD '$PASSWORD';"

echo "✅ Usuario '$DB_USER' creado o ya existe"

# Dar permisos
psql -U "$USER" -d "$DB_NAME" << EOF
GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $DB_USER;
\c $DB_NAME
GRANT ALL ON SCHEMA public TO $DB_USER;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO $DB_USER;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO $DB_USER;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO $DB_USER;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO $DB_USER;
EOF

echo "✅ Permisos otorgados"

# Verificar conexión
echo ""
echo "Verificando conexión..."
if psql -U "$DB_USER" -d "$DB_NAME" -c "SELECT version();" > /dev/null 2>&1; then
    echo "✅ Conexión exitosa"
else
    echo "⚠️  Verificando con usuario actual..."
    psql -U "$USER" -d "$DB_NAME" -c "SELECT version();" > /dev/null 2>&1 && echo "✅ Conexión exitosa"
fi

echo ""
echo "=========================================="
echo "INSTALACIÓN COMPLETADA"
echo "=========================================="
echo ""
echo "📋 Información de conexión:"
echo "   Base de datos: $DB_NAME"
echo "   Usuario: $DB_USER"
echo "   Contraseña: $PASSWORD"
echo "   Host: localhost"
echo "   Puerto: 5432"
echo ""
echo "🔧 Comandos útiles:"
echo "   Iniciar: brew services start postgresql@16"
echo "   Detener: brew services stop postgresql@16"
echo "   Estado:  brew services list | grep postgresql"
echo "   Conectar: psql -U $DB_USER -d $DB_NAME"
echo ""
echo "📝 Próximos pasos:"
echo "   1. Configurar datasource en JBoss"
echo "   2. Crear modelo de datos"
echo "   3. Implementar registro de postulante"
echo ""

