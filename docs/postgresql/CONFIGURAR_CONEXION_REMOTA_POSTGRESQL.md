# 🔌 CONFIGURAR CONEXIÓN REMOTA A POSTGRESQL

**Servidor:** 192.168.29.5  
**Base de datos:** gesempleo_bd  
**Usuario:** bolemp_owner

---

## ❌ PROBLEMA ACTUAL

**Error:** `no hay una línea en pg_hba.conf para «192.168.87.237»`

El servidor PostgreSQL está rechazando la conexión porque:
- El puerto 5432 está abierto ✅
- Pero falta configuración en `pg_hba.conf` del servidor ❌

---

## 🔧 SOLUCIÓN EN EL SERVIDOR (192.168.29.5)

**Necesitas acceso al servidor PostgreSQL para configurar:**

### 1. Editar `pg_hba.conf`

Ubicación típica: `/etc/postgresql/16/main/pg_hba.conf` o `/var/lib/pgsql/16/data/pg_hba.conf`

Agregar esta línea:

```conf
# Permitir conexión desde la IP del cliente
host    gesempleo_bd    bolemp_owner    192.168.87.237/32    md5

# O permitir desde toda la red local (menos seguro)
host    gesempleo_bd    bolemp_owner    192.168.0.0/16       md5
```

### 2. Verificar `postgresql.conf`

Asegurar que PostgreSQL escucha en todas las interfaces:

```conf
listen_addresses = '*'  # o '192.168.29.5,localhost'
```

### 3. Reiniciar PostgreSQL

```bash
sudo systemctl restart postgresql
# o
sudo service postgresql restart
```

---

## 💻 CONECTAR DESDE TU MAC

### Opción 1: Con psql (línea de comandos)

```bash
PGPASSWORD='Empleo2025.' psql -h 192.168.29.5 -p 5432 -U bolemp_owner -d gesempleo_bd
```

### Opción 2: Con DBeaver

1. **Nueva conexión** → **PostgreSQL**
2. Configurar:
   ```
   Host: 192.168.29.5
   Puerto: 5432
   Base de datos: gesempleo_bd
   Usuario: bolemp_owner
   Contraseña: Empleo2025.
   ```
3. **Opcional:** En pestaña **"SSL"**:
   - SSL Mode: `require` o `prefer`

### Opción 3: Con cadena de conexión

```
host=192.168.29.5 port=5432 dbname=gesempleo_bd user=bolemp_owner password=Empleo2025.
```

---

## 🔍 VERIFICAR CONECTIVIDAD

### Verificar puerto abierto:

```bash
nc -zv 192.168.29.5 5432
```

### Verificar conexión:

```bash
PGPASSWORD='Empleo2025.' psql -h 192.168.29.5 -p 5432 -U bolemp_owner -d gesempleo_bd -c "\conninfo"
```

---

## 📋 DATOS DE CONEXIÓN

```
Host: 192.168.29.5
Puerto: 5432
Base de datos: gesempleo_bd
Usuario: bolemp_owner
Contraseña: Empleo2025.
```

---

## ⚠️ NOTA IMPORTANTE

**Tu IP actual:** `192.168.87.237`

Si tu IP cambia (DHCP), necesitarás:
- Agregar tu nueva IP en `pg_hba.conf`, o
- Usar un rango de red (ej: `192.168.87.0/24`)

---

## 🆘 SI NO TIENES ACCESO AL SERVIDOR

Contacta al administrador del servidor PostgreSQL (192.168.29.5) y pide que:

1. Agregue tu IP `192.168.87.237` en `pg_hba.conf`
2. O configure un rango de red para tu oficina
3. Reinicie el servicio PostgreSQL

---

**Última actualización:** 29 de Diciembre de 2025

