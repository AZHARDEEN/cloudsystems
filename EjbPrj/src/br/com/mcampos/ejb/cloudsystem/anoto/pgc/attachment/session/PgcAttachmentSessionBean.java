package br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.session;


import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity.PgcAttachmentPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PgcAttachmentSession", mappedName = "CloudSystems-EjbPrj-PgcAttachmentSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class PgcAttachmentSessionBean extends Crud<PgcAttachmentPK, PgcAttachment> implements PgcAttachmentSessionLocal
{
    public List<PgcAttachment> get( Integer pgcId ) throws ApplicationException
    {
        return ( List<PgcAttachment> )getResultList( PgcAttachment.findAllPgc, pgcId );
    }
}
