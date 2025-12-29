# 🔄 REFRESCAR CONEXIÓN EN DBEAVER

## Pasos para ver las tablas:

1. **En DBeaver, en el panel izquierdo:**
   - Click derecho en la conexión **"bolsa_empleo localhost:5432"**
   - Selecciona **"Editar conexión"** o **"Refresh"**

2. **O expande manualmente:**
   - Expande: **bolsa_empleo** → **Schemas** → **bolsa_empleo** (no "public")
   - Click en **"Tables"** para expandirlo
   - Deberías ver las 7 tablas

3. **Si no aparecen:**
   - Click derecho en **"bolsa_empleo"** (el esquema, no la base de datos)
   - Selecciona **"Refresh"**
   - O **"SQL Editor"** → **"Nuevo script SQL"** y ejecuta:
     ```sql
     SELECT * FROM bolsa_empleo.postulante;
     ```

## Las tablas están en el esquema "bolsa_empleo", NO en "public"

- ✅ **bolsa_empleo.postulante**
- ✅ **bolsa_empleo.formacion_academica**
- ✅ **bolsa_empleo.ocupacion**
- ✅ **bolsa_empleo.experiencia_laboral**
- ✅ **bolsa_empleo.curso_certificacion**
- ✅ **bolsa_empleo.discapacidad**
- ✅ **bolsa_empleo.postulante_discapacidad**

