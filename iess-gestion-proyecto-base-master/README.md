## Plantilla Base de Desarrollo

La plantilla esta generada con base a la arquitectura de referencia JEE8,  se trata de un componente vacio y permite unicamente incorporar el desarrollo de las soluciones del IESS.

**Componentes:**

- Componente EJB
- Componente WEB
    - Componente WEB Funcionario
    - Componente WEB Afiliado
    - Componente WEB Empleador (*)

(*) El componente WEB Empleador debe ser certificado su funcionalidad de ingreso a los sistemas por parte de la unidade de negocio.

## Código de Aplicación
Los componentes de tipo WEB debe tener un código de aplicación el mismo que debe ser especificado en archivo web.xml

    <context-param>
		<param-name>CODIGO_APLICACION</param-name>
		<param-value>IESS_GES_XXXXXXX</param-value>
	</context-param>

## Estándares Principales

Desarrollo | Auditoria | Nombrado | Recaptcha
-------------- | ----------------- | -------------------- | ---------------------
👉 [Descargar](https://iesscnt.sharepoint.com/:b:/s/GrupodeSoluciones/RepositorioDocumental/EQ_3tHud1btAiWrhdGQHOgIBG1a7iTXdOhEKNYOO89ODzw?e=pJPTAm) | 👉 [Descargar](https://iesscnt.sharepoint.com/:b:/s/GrupodeSoluciones/RepositorioDocumental/EfXh7sxmewxCrGEFP-vVBq4B4psXhGeni_F1ftLse1Q_Vw?e=zFB7eg) | 👉 [Descargar](https://iesscnt.sharepoint.com/:b:/s/GrupodeSoluciones/RepositorioDocumental/Ed1kpQY-n0tAgsg55w8UjQ8ByYOd-AqMhvbuiUYnAhbAlw?e=9knT8o) |👉 [Descargar](https://iesscnt.sharepoint.com/:b:/s/GrupodeSoluciones/RepositorioDocumental/EWVu1GiGnwBHnu5F0Muw4roBka9Y2lEwXrleB2zR9ghkTQ?e=bOHq7R) |



# Datasources Genéricos
Para utilizar la plantilla base de desarrollo se necesita que se encuentre configurado los siguientes datasources de manera obligatoria:


- Usuario Seguridades:

    - jndi-name= java:jboss/datasources/gen-seg-usr-DS
    - pool-name= gen-seg-usr-DS
    - usuario = GEN_SEG_USR
    - clave = pruebas

- Usuario Transversal:

    - jndi-name= java:jboss/datasources/gen-comun-usr-DS 
    - pool-name= gen-comun-usr-DS
    - usuario = GEN_COMUN_USR
    - clave = pruebas

- Usuario Auditoría:

    - jndi-name= java:jboss/datasources/ap-ges-aud-usr-DS 
    - pool-name= ap-ges-aud-usr-DS
    - usuario = GES_AUD_USR
    - clave = desarrollo

# Datasources de Aplicación
El datasource de aplicación es único por cada componente empresarial, este datasource se configura en el componente EJB del proyecto.

- Usuario Aplicación (Varia de acuerdo a la aplicación):

    - jndi-name= java:jboss/datasources/ap-ges-pbase-usr-DS 
    - pool-name= ap-ges-pbase-usr-DS
    - usuario = AP_ARQ_PBASE_USR
    - clave = pruebas

## Contactenos

Correo Electrónico: arquitecturadnti@iess.gob.ec


<p align="center"> © 2021 IESS, todos los derechos reservados.</p>
<p align="center">
https://www.iess.gob.ec
</p>