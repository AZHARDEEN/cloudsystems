package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcPageAttachmentSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoExportFacade", mappedName = "CloudSystems-EjbPrj-AnotoExportFacade" )
@TransactionAttribute( TransactionAttributeType.NEVER )
public class AnotoExportFacadeBean extends AbstractSecurity implements AnotoExportFacade
{
    protected static final int SystemMessageTypeId = 8;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    private PgcFieldSessionLocal pgcFieldSession;

    @EJB
    private PgcPageSessionLocal pgcPageSession;

    @EJB
    private PgcPageAttachmentSessionLocal pgcAttachmentSession;


    public AnotoExportFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }

    @TransactionAttribute( TransactionAttributeType.NEVER )
    public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        PgcPage entity = pgcPageSession.get( new PgcPagePK( page ) );
        List<PgcField> fields = Collections.emptyList();
        if ( entity != null )
            fields = pgcFieldSession.getAll( entity );
        return AnotoUtils.toPgcFieldList( fields );
    }


    public List<PgcAttachmentDTO> getAttachments( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        PgcPage entity = pgcPageSession.get( new PgcPagePK( page ) );
        List<PgcPageAttachment> attachs = Collections.emptyList();
        if ( entity != null )
            attachs = pgcAttachmentSession.getAll( entity );
        return Collections.emptyList();
    }
}
