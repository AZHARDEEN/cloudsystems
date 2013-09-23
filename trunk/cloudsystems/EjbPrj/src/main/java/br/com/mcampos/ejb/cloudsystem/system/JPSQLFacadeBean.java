package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "JPSQLFacade", mappedName = "JPSQLFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class JPSQLFacadeBean implements JPSQLFacade
{
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    public List<Object> executeSelect( String sql )
    {
        if ( SysUtils.isEmpty( sql ) )
            return Collections.emptyList();
        Query query = em.createQuery( sql );
        List<Object> list = query.getResultList();
        return list;
    }
}
