package br.com.imstecnologia.ejb.sped.fiscal.cfop.session;


import br.com.imstecnologia.ejb.sped.fiscal.cfop.entity.Cfop;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "CfopSession", mappedName = "CloudSystems-EjbPrj-CfopSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class CfopSessionBean extends Crud<Integer, Cfop> implements CfopSessionLocal
{

    public void delete( Integer key ) throws ApplicationException
    {
        delete( Cfop.class, key );
    }

    public Cfop get( Integer key ) throws ApplicationException
    {
        return get( Cfop.class, key );
    }

    public List<Cfop> getAll( boolean bClosed ) throws ApplicationException
    {
        if ( bClosed )
            return ( List<Cfop> )getResultList( Cfop.getAll );
        else
            return ( List<Cfop> )getResultList( Cfop.getAllValid );
    }

    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( Cfop.nextId );
    }
}
