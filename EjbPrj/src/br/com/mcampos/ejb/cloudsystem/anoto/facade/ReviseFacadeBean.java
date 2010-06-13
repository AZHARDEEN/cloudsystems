package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ReviseFacade", mappedName = "CloudSystems-EjbPrj-ReviseFacade" )
@Remote
public class ReviseFacadeBean extends AbstractSecurity implements ReviseFacade
{
    protected static final int SystemMessageTypeId = 27;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private PgcFieldSessionLocal pgcFieldSession;

    @EJB
    private PgcPageSessionLocal pgcPageSession;

    @EJB
    private MediaSessionLocal mediaSession;


    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return SystemMessageTypeId;
    }

    public List<PgcFieldDTO> getFields( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        PgcPage pgcPage = pgcPageSession.get( new PgcPagePK( page ) );
        if ( pgcPage != null ) {
            if ( pgcPage.getRevisionStatus().getId().equals( 1 ) )
                pgcPageSession.setRevisedStatus( pgcPage, 2 );
            List<PgcField> fields = pgcFieldSession.getAll( pgcPage );
            return AnotoUtils.toPgcFieldList( fields );
        }
        else
            return Collections.emptyList();
    }

    public void setStatus( AuthenticationDTO auth, PgcPageDTO page, Integer status ) throws ApplicationException
    {
        authenticate( auth );
        PgcPage pgcPage = pgcPageSession.get( new PgcPagePK( page ) );
        if ( pgcPage != null ) {
            pgcPageSession.setRevisedStatus( pgcPage, status );
        }
    }


    public byte[] getObject( MediaDTO key ) throws ApplicationException
    {
        return mediaSession.getObject( key.getId() );
    }

    public void update( AuthenticationDTO auth, PgcFieldDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        PgcField field = pgcFieldSession.get( new PgcFieldPK( dto ) );
        if ( field != null ) {
            PgcFieldUtil.update( field, dto );
            pgcFieldSession.update( field );
        }
    }

}
