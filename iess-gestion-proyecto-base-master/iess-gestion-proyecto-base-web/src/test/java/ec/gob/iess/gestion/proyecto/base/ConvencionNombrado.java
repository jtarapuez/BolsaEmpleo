/**
 * Copyright 2020 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */

package ec.gob.iess.gestion.proyecto.base;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 *
 * @author ppilcoa
 */
@AnalyzeClasses(packages = "ec.gob.iess.gestion.proyecto.base")
public class ConvencionNombrado {

	/**
	 * Convención de nombrado para Controlador
	 */
    @ArchTest
    static ArchRule controladoresDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..controlador..")
                    .should().haveSimpleNameEndingWith("Controlador");

    /**
	 * Convención de nombrado para Bean
	 */
    @ArchTest
    static ArchRule beansDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..bean..")
                    .should().haveSimpleNameEndingWith("Bean");

    /**
	 * Convención de nombrado para Servicio
	 */
    @ArchTest
    static ArchRule serviciosDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..servicio..")
                    .should().haveSimpleNameEndingWith("Servicio");

    /**
	 * Convención de nombrado para Entidad
	 */
    @ArchTest
    static ArchRule entidadesDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..entidad")
                    .should().haveSimpleNameEndingWith("Entidad");

    /**
	 * Convención de nombrado para Dao
	 */
    @ArchTest
    static ArchRule accesoDatosDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..dao..")
                    .should().haveSimpleNameEndingWith("Dao");
    /**
     * Convención de nombrado para Dto
     */
    @ArchTest
    static ArchRule transferenciaDatosDebeTenerSufijo
            = classes()
                    .that().resideInAPackage("..dto..")
                    .should().haveSimpleNameEndingWith("Dto");

}
