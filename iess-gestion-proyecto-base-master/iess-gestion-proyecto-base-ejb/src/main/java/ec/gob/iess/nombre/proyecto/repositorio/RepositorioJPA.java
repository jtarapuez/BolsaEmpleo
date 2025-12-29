/**
 * Copyright 2020 INSTITUTO ECUATORIANO DE SEGURIDAD SOCIAL - ECUADOR.
 * Todos los derechos reservados.
 */

package ec.gob.iess.nombre.proyecto.repositorio;

import ec.gob.iess.componente.repositorio.auditoria.jpa.GenericoRepositorioJPA;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <b>
 * Incluir aqui la descripcion de la clase.
 * </b>
 * @author PatricioPilco
 * @version $Revision: 1.0 $ <p>[$Author: PatricioPilco $, $Date: 29 jul. 2020 $]</p>
 */
public class RepositorioJPA<T> implements Serializable {

    private static final long serialVersionUID = 7049343590221178886L;
    private static final String AP_PBASE_PU = "ap-ges-pbase-PU";
    @PersistenceContext(unitName = AP_PBASE_PU)
    private transient EntityManager entityManager;
    private final GenericoRepositorioJPA<T> genericoRepositorioJPA;

    /**
     * Constructor para inicializar entitymanager
     */
    public RepositorioJPA() {
        genericoRepositorioJPA = new GenericoRepositorioJPA(entityManager);
    }

    /**
     * Constructor para inicializar entitymanager y entidad
     */
    public RepositorioJPA(Class<T> entidad) {
        genericoRepositorioJPA = new GenericoRepositorioJPA(entityManager, entidad);
    }

    /**
     * Método que permite extender los métodos del repositorio genérico
     */
    public GenericoRepositorioJPA<T> getDao() {
        genericoRepositorioJPA.setEntityManager(entityManager);
        return genericoRepositorioJPA;
    }
}