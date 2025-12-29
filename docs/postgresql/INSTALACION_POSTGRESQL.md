# 🐘 INSTALACIÓN DE POSTGRESQL 16 EN macOS

**Fecha:** 29 de Diciembre de 2025  
**Sistema:** macOS (Apple Silicon - ARM64)

---

## 📋 REQUISITOS PREVIOS

- macOS con permisos de administrador
- Terminal con acceso a internet
- Espacio en disco: ~200 MB

---

## 🚀 OPCIÓN 1: INSTALACIÓN CON HOMEBREW (RECOMENDADA)

### Paso 1: Instalar Homebrew (si no está instalado)

**Página oficial:** https://brew.sh/

**Comando de instalación:**
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

**Nota:** Este comando pedirá tu contraseña de administrador.

**Verificar instalación:**
```bash
brew --version
```

### Paso 2: Instalar PostgreSQL 16

```bash
brew install postgresql@16
```

**Tiempo estimado:** 5-10 minutos (depende de la conexión)

### Paso 3: Configurar Variables de Entorno

**Editar archivo de configuración:**
```bash
nano ~/.zshrc
```

**Agregar al final del archivo:**
```bash
# PostgreSQL 16
export PGHOME=$(brew --prefix postgresql@16)
export PGDATA=$PGHOME/var/postgres
export PATH=$PGHOME/bin:$PATH
export DYLD_LIBRARY_PATH=$PGHOME/lib:$DYLD_LIBRARY_PATH
```

**Guardar y recargar:**
```bash
# Guardar: Ctrl+O, Enter, Ctrl+X
# Recargar configuración
source ~/.zshrc
```

### Paso 4: Inicializar Base de Datos

```bash
# Inicializar cluster de PostgreSQL
initdb -D $PGDATA

# O si prefieres otra ubicación
initdb -D ~/postgresql-data
```

### Paso 5: Iniciar PostgreSQL

```bash
# Iniciar como servicio (recomendado)
brew services start postgresql@16

# O iniciar manualmente
pg_ctl -D $PGDATA start
```

### Paso 6: Verificar Instalación

```bash
# Verificar versión
psql --version

# Debe mostrar: psql (PostgreSQL) 16.x

# Conectar a PostgreSQL
psql -U postgres -d postgres
```

---

## 📥 OPCIÓN 2: INSTALACIÓN MANUAL (DESCARGA DIRECTA)

### Paso 1: Descargar PostgreSQL 16

**Página oficial de descarga:**
- **EnterpriseDB (Recomendado):** https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
- **PostgreSQL.org:** https://www.postgresql.org/download/macosx/

**Seleccionar:**
- **Versión:** PostgreSQL 16.x
- **Plataforma:** macOS
- **Arquitectura:** ARM 64-bit (para Apple Silicon)

**Archivo:** `postgresql-16.x-1-osx.dmg` (o similar)

### Paso 2: Instalar

1. Abrir el archivo `.dmg` descargado
2. Ejecutar el instalador `.pkg`
3. Seguir el asistente de instalación
4. **IMPORTANTE:** Anotar la contraseña del usuario `postgres`
5. Ubicación por defecto: `/Library/PostgreSQL/16`

### Paso 3: Configurar Variables de Entorno

**Editar archivo de configuración:**
```bash
nano ~/.zshrc
```

**Agregar al final:**
```bash
# PostgreSQL 16
export PGHOME=/Library/PostgreSQL/16
export PGDATA=$PGHOME/data
export PATH=$PGHOME/bin:$PATH
export DYLD_LIBRARY_PATH=$PGHOME/lib:$DYLD_LIBRARY_PATH
```

**Recargar:**
```bash
source ~/.zshrc
```

---

## 🗄️ CREAR BASE DE DATOS PARA BOLSA DE EMPLEO

### Paso 1: Conectar a PostgreSQL

```bash
# Conectar como usuario postgres
psql -U postgres
```

### Paso 2: Crear Base de Datos y Usuario

```sql
-- Crear base de datos
CREATE DATABASE bolsa_empleo;

-- Crear usuario
CREATE USER bolsa_empleo_user WITH PASSWORD 'tu_contraseña_segura';

-- Dar permisos
GRANT ALL PRIVILEGES ON DATABASE bolsa_empleo TO bolsa_empleo_user;

-- Conectar a la nueva base de datos
\c bolsa_empleo

-- Dar permisos en el esquema público
GRANT ALL ON SCHEMA public TO bolsa_empleo_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bolsa_empleo_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bolsa_empleo_user;

-- Salir
\q
```

### Paso 3: Verificar Conexión

```bash
# Conectar con el nuevo usuario
psql -U bolsa_empleo_user -d bolsa_empleo

# Verificar
SELECT version();
\q
```

---

## 🔧 COMANDOS ÚTILES

### Gestión del Servicio (Homebrew)

```bash
# Iniciar PostgreSQL
brew services start postgresql@16

# Detener PostgreSQL
brew services stop postgresql@16

# Reiniciar PostgreSQL
brew services restart postgresql@16

# Ver estado
brew services list | grep postgresql

# Ver logs
tail -f ~/Library/Logs/Homebrew/postgresql@16.log
```

### Gestión Manual

```bash
# Iniciar
pg_ctl -D $PGDATA start

# Detener
pg_ctl -D $PGDATA stop

# Reiniciar
pg_ctl -D $PGDATA restart

# Ver estado
pg_ctl -D $PGDATA status
```

### Conexión y Consultas

```bash
# Conectar a base de datos específica
psql -U postgres -d bolsa_empleo

# Conectar con host y puerto específicos
psql -h localhost -p 5432 -U postgres -d bolsa_empleo

# Listar bases de datos
psql -U postgres -l

# Listar tablas
psql -U bolsa_empleo_user -d bolsa_empleo -c "\dt"
```

---

## ✅ VERIFICACIÓN COMPLETA

### Verificar Variables de Entorno

```bash
echo $PGHOME
echo $PGDATA
echo $PATH | grep postgresql
```

### Verificar Instalación

```bash
# Versión de PostgreSQL
psql --version

# Versión del servidor
psql -U postgres -c "SELECT version();"

# Estado del servicio
brew services list | grep postgresql
# O
pg_ctl -D $PGDATA status
```

### Verificar Conexión

```bash
# Conectar y ejecutar consulta
psql -U bolsa_empleo_user -d bolsa_empleo -c "SELECT current_database(), current_user;"
```

---

## 🔍 SOLUCIÓN DE PROBLEMAS

### Error: "psql: command not found"

**Solución:**
```bash
# Verificar que las variables están configuradas
source ~/.zshrc

# Verificar ubicación
which psql
```

### Error: "could not connect to server"

**Solución:**
```bash
# Verificar que PostgreSQL está corriendo
brew services list | grep postgresql

# Si no está corriendo, iniciarlo
brew services start postgresql@16
```

### Error: "password authentication failed"

**Solución:**
```bash
# Verificar usuario y contraseña
# Si olvidaste la contraseña, puedes resetearla editando pg_hba.conf
# Ubicación: $PGDATA/pg_hba.conf
```

### Error: "database does not exist"

**Solución:**
```bash
# Listar bases de datos existentes
psql -U postgres -l

# Crear la base de datos si no existe
psql -U postgres -c "CREATE DATABASE bolsa_empleo;"
```

---

## 📝 CONFIGURACIÓN ADICIONAL

### Cambiar Puerto (si es necesario)

**Editar:** `$PGDATA/postgresql.conf`

```conf
port = 5432  # Cambiar al puerto deseado
```

**Reiniciar PostgreSQL después del cambio**

### Configurar Acceso Remoto (si es necesario)

**Editar:** `$PGDATA/pg_hba.conf`

```conf
# Permitir conexiones locales
local   all             all                                     trust
host    all             all             127.0.0.1/32            trust
host    all             all             ::1/128                 trust
```

---

## 📚 RECURSOS Y DOCUMENTACIÓN

- **Documentación oficial PostgreSQL:** https://www.postgresql.org/docs/16/
- **Homebrew:** https://brew.sh/
- **EnterpriseDB:** https://www.enterprisedb.com/

---

## 🎯 PRÓXIMOS PASOS

Después de instalar PostgreSQL 16:

1. ✅ Verificar instalación
2. ✅ Crear base de datos `bolsa_empleo`
3. ✅ Configurar datasource en JBoss
4. ✅ Crear modelo de datos
5. ✅ Implementar registro de postulante

---

**Última actualización:** 29 de Diciembre de 2025

