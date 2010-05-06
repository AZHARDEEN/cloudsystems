package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment;


import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PgcAttachmentSession", mappedName = "CloudSystems-EjbPrj-PgcAttachmentSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcAttachmentSessionBean extends Crud<PgcAttachmentPK, PgcAttachment> implements PgcAttachmentSessionLocal
{
    public PgcAttachmentSessionBean()
    {
    }

    public void delete( PgcAttachmentPK key ) throws ApplicationException
    {
        delete( PgcAttachment.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PgcAttachment get( PgcAttachmentPK key ) throws ApplicationException
    {
        return get( PgcAttachment.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcAttachment> getAll( PgcPage page ) throws ApplicationException
    {
        return ( List<PgcAttachment> )getResultList( PgcAttachment.findByPage, page );
    }
}
