package br.com.mcampos.ejb.cloudsystem.anoto.facade;


import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.session.PageFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPagePK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPageSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcFieldUtil;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "ReviseFacade", mappedName = "CloudSystems-EjbPrj-ReviseFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
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
    private PageFieldSessionLocal anotoPageFieldSession;

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
            if ( pgcPage.getRevisionStatus().getId().equals( status ) )
                throwRuntimeException( 1 );
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

    public PgcPageDTO findPageByFieldKeys( AuthenticationDTO auth, PgcPageDTO page ) throws ApplicationException
    {
        authenticate( auth );
        PgcPage pgcPage = pgcPageSession.get( new PgcPagePK( page ) );
        if ( !pgcPage.getRevisionStatus().getId().equals( RevisionStatus.statusVerified ) )
            return null;
        List<AnotoPageField> pkFields = anotoPageFieldSession.getPKFields( pgcPage.getAnotoPage().getPad().getForm() );
        if ( SysUtils.isEmpty( pkFields ) )
            return null;
        StringBuffer filter = new StringBuffer();
        for ( AnotoPageField field : pkFields ) {
            PgcField pgcField = pgcFieldSession.get( new PgcFieldPK( pgcPage, field.getName() ) );
            if ( pgcField == null )
                return null;
            if ( filter.length() > 0 )
                filter.append( " AND " );
            filter.append( String.format( " ( pgc_field.pfl_name_ch = '%' \n" +
                        "and pgc_field.pfl_has_penstrokes_bt = true \n" +
                        "and coalesce ( pgc_field.pfl_icr_tx, pgc_field.pfl_revised_tx ) = '%s' ) \n", pgcField.getName(),
                        pgcField.getValue() ) );
        }
        return null;
    }


    private String getAnotherPageByPkFieldSQL( PgcPage pgcPage, StringBuffer filter )
    {
        String sql;

        sql = String.format( "select * from pgc_field \n" +
                    "where %s " + "and pgc_page.pgc_id_in <> %d", filter.toString(), pgcPage.getPgc().getId() );
        return sql;
    }
}
