# 🐘 DOCUMENTACIÓN DE POSTGRESQL

Documentación relacionada con la configuración, instalación y conexión a PostgreSQL.

---

## 📋 DOCUMENTOS DISPONIBLES

### 🔧 Instalación y Configuración

- **[INSTALACION_POSTGRESQL.md](./INSTALACION_POSTGRESQL.md)**
  - Instalación de PostgreSQL 16 en macOS
  - Configuración de variables de entorno
  - Creación de base de datos y usuario local

### 🔌 Conexión Remota

- **[CONFIGURAR_CONEXION_REMOTA_POSTGRESQL.md](./CONFIGURAR_CONEXION_REMOTA_POSTGRESQL.md)**
  - Configuración de conexión a servidor remoto
  - Solución de problemas de `pg_hba.conf`
  - Configuración de SSL

- **[DATOS_CONEXION_REMOTA.md](./DATOS_CONEXION_REMOTA.md)**
  - Datos de conexión a la base de datos remota
  - Comandos útiles para conectarse
  - Configuración en DBeaver

### 🛠️ Herramientas

- **[REFRESCAR_DBEAVER.md](./REFRESCAR_DBEAVER.md)**
  - Cómo refrescar conexión en DBeaver
  - Ver tablas en esquemas específicos
  - Solución de problemas de visualización

---

## 🚀 INICIO RÁPIDO

### Base de Datos Local

```bash
# Instalar PostgreSQL 16
./instalar_postgresql.sh

# Conectarse
psql -U bolsa_empleo_user -d bolsa_empleo
```

### Base de Datos Remota

```bash
# Conectarse a servidor remoto
PGPASSWORD='Empleo2025' psql -h 192.168.29.5 -p 5432 -U bolemp_owner -d gesempleo_bd
```

**Datos de conexión:**
- Host: `192.168.29.5`
- Puerto: `5432`
- Base de datos: `gesempleo_bd`
- Usuario: `bolemp_owner`
- Contraseña: `Empleo2025`

---

## 📊 ESQUEMAS Y TABLAS

### Base de Datos Local (`bolsa_empleo`)
- Esquema: `bolsa_empleo`
- 7 tablas para registro de postulante

### Base de Datos Remota (`gesempleo_bd`)
- Esquema: `bolsaempleo`
- 9 tablas del sistema de bolsa de empleo

---

**Última actualización:** 29 de Diciembre de 2025


