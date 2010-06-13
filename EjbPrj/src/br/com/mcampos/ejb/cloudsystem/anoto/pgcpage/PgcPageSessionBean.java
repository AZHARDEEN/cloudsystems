package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.page.session.AnotoPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.PGCSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session.RevisionStatusSessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateless( name = "PgcPageSession", mappedName = "CloudSystems-EjbPrj-PgcPageSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcPageSessionBean extends Crud<PgcPagePK, PgcPage> implements PgcPageSessionLocal
{
    @EJB
    PGCSessionLocal pgcSession;

    @EJB
    AnotoPageSessionLocal anotoPageSession;

    @EJB
    RevisionStatusSessionLocal revisionSession;

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

    @Override
    public PgcPage add( PgcPage entity ) throws ApplicationException
    {
        entity.setPgc( pgcSession.get( entity.getPgc().getId() ) );
        entity.setAnotoPage( anotoPageSession.get( new AnotoPagePK( entity.getAnotoPage() ) ) );
        return super.add( entity );
    }

    public void setRevisedStatus( PgcPage page, Integer status ) throws ApplicationException
    {
        getEntityManager().merge( page );
        if ( page.getRevisionStatus().getId().equals( status ) == false ) {
            RevisionStatus st = revisionSession.get( status );
            if ( st != null )
                page.setRevisionStatus( st );
        }
    }
}
