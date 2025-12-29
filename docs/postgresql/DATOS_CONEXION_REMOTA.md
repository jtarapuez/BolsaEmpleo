# 🔌 DATOS DE CONEXIÓN - BASE DE DATOS REMOTA

**Última actualización:** 29 de Diciembre de 2025

---

## ✅ CONEXIÓN EXITOSA

```
Host: 192.168.29.5
Puerto: 5432
Base de datos: gesempleo_bd
Usuario: bolemp_owner
Contraseña: Empleo2025
```

**⚠️ IMPORTANTE:** La contraseña es `Empleo2025` (sin el punto final)

---

## 💻 CONECTAR DESDE LÍNEA DE COMANDOS

```bash
PGPASSWORD='Empleo2025' psql -h 192.168.29.5 -p 5432 -U bolemp_owner -d gesempleo_bd
```

---

## 🦫 CONECTAR DESDE DBEAVER

1. **Nueva conexión** → **PostgreSQL**
2. Configurar:
   ```
   Host: 192.168.29.5
   Puerto: 5432
   Base de datos: gesempleo_bd
   Usuario: bolemp_owner
   Contraseña: Empleo2025
   ```
3. Marcar **"Guardar contraseña"**
4. Click en **"Probar conexión"** → **"Finalizar"**

---

## 🔍 VERIFICAR CONEXIÓN

```bash
PGPASSWORD='Empleo2025' psql -h 192.168.29.5 -p 5432 -U bolemp_owner -d gesempleo_bd -c "\conninfo"
```

---

## 📋 COMANDOS ÚTILES

### Ver todas las tablas:
```sql
\dt
```

### Ver esquemas:
```sql
\dn
```

### Ver estructura de una tabla:
```sql
\d nombre_tabla
```

---

**Estado:** ✅ Conectado y funcionando

