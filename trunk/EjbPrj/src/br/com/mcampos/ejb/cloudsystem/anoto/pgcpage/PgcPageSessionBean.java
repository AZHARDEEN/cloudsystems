package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "PgcPageSession", mappedName = "CloudSystems-EjbPrj-PgcPageSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcPageSessionBean extends Crud<PgcPagePK, PgcPage> implements PgcPageSessionLocal
{
    public PgcPageSessionBean()
    {
    }

    public void delete( PgcPagePK key ) throws ApplicationException
    {
        delete( PgcPage.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PgcPage get( PgcPagePK key ) throws ApplicationException
    {
        return get( PgcPage.class, key );
    }
}
